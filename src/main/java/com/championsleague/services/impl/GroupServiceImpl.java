package com.championsleague.services.impl;

import com.championsleague.entities.Game;
import com.championsleague.entities.Group;
import com.championsleague.entities.Team;
import com.championsleague.entities.pk.GamePK;
import com.championsleague.enums.GameOutcome;
import com.championsleague.enums.Points;
import com.championsleague.exceptions.GenericException;
import com.championsleague.repositories.GameRepository;
import com.championsleague.repositories.GroupRepository;
import com.championsleague.repositories.TeamRepository;
import com.championsleague.services.GroupService;
import com.championsleague.to.GameTO;
import com.championsleague.to.GroupTO;
import com.championsleague.to.TeamTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<GroupTO> getGroupInfo() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> new GroupTO(group, convertToTeamTO(teamRepository.findByGroup_Id(group.getId()))))
                .collect(Collectors.toList());
    }

    private List<TeamTO> convertToTeamTO(List<Team> teams) {
        List<TeamTO> teamTOS = teams.stream()
                .map(TeamTO::new)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < teamTOS.size(); i++) {
            teamTOS.get(i).setRank(i + 1);
        }

        return teamTOS;
    }

    @Override
    public List<GroupTO> addResults(List<GameTO> results) throws GenericException {
        for (GameTO gameTO : results) {
            Group group = checkGroup(gameTO);
            saveTeamStats(gameTO, group);
            saveGame(gameTO, group);
        }

        return getGroupInfo();
    }

    @Override
    public List<GroupTO> updateResults(List<GameTO> results) throws GenericException {
        for (GameTO gameTO : results) {
            Group group = groupRepository.findByName(gameTO.getGroup());
            if (group == null) {
                throw new GenericException("Group doesn't exist. Can't update game: " + gameTO.getHomeTeam() + ":" + gameTO.getAwayTeam());
            } else {
                updateGameInfo(gameTO);
            }
        }
        return getGroupInfo();
    }

    private void updateGameInfo(GameTO gameTO) throws GenericException {
        GamePK gamePK = new GamePK(findTeamId(gameTO.getHomeTeam()), findTeamId(gameTO.getAwayTeam()));
        Optional<Game> gameOptional = gameRepository.findById(gamePK);
        if (!gameOptional.isPresent()) {
            throw new GenericException("Game doesn't exist. Can't update game: " + gameTO.getHomeTeam() + ":" + gameTO.getAwayTeam());
        } else {
            Game game = gameOptional.get();
            if (!game.getScore().equals(gameTO.getScore())) {
                updateTeamStats(gameTO, game.getScore());
                game.setScore(gameTO.getScore());
                gameRepository.save(game);
            }
        }
    }

    private void updateTeamStats(GameTO gameTO, String oldScore) throws GenericException {
        int homeGoals = splitScore(gameTO.getScore(), 0);
        int awayGoals = splitScore(gameTO.getScore(), 1);

        int oldHomeGoals = splitScore(oldScore, 0);
        int oldAwayGoals = splitScore(oldScore, 1);

        Team homeTeam = findTeamByName(gameTO.getHomeTeam());
        Team awayTeam = findTeamByName(gameTO.getAwayTeam());

        subtractGoals(homeTeam, oldHomeGoals, oldAwayGoals);
        subtractGoals(awayTeam, oldAwayGoals, oldHomeGoals);

        addGoals(homeTeam, homeGoals, awayGoals);
        addGoals(awayTeam, awayGoals, homeGoals);

        if (oldHomeGoals > oldAwayGoals) {
            if (homeGoals < awayGoals) {
                changePointsAndOutcomes(homeTeam, GameOutcome.WIN, GameOutcome.LOSE);
                changePointsAndOutcomes(awayTeam, GameOutcome.LOSE, GameOutcome.WIN);
            } else if (homeGoals == awayGoals) {
                changePointsAndOutcomes(homeTeam, GameOutcome.WIN, GameOutcome.DRAW);
                changePointsAndOutcomes(awayTeam, GameOutcome.LOSE, GameOutcome.DRAW);
            }
        } else if (oldHomeGoals == oldAwayGoals) {
            if (homeGoals < awayGoals) {
                changePointsAndOutcomes(homeTeam, GameOutcome.DRAW, GameOutcome.LOSE);
                changePointsAndOutcomes(awayTeam, GameOutcome.DRAW, GameOutcome.WIN);
            } else if (homeGoals > awayGoals) {
                changePointsAndOutcomes(homeTeam, GameOutcome.DRAW, GameOutcome.WIN);
                changePointsAndOutcomes(awayTeam, GameOutcome.DRAW, GameOutcome.LOSE);
            }
        } else {
            if (homeGoals > awayGoals) {
                changePointsAndOutcomes(homeTeam, GameOutcome.LOSE, GameOutcome.WIN);
                changePointsAndOutcomes(awayTeam, GameOutcome.WIN, GameOutcome.LOSE);
            } else if (homeGoals == awayGoals) {
                changePointsAndOutcomes(homeTeam, GameOutcome.LOSE, GameOutcome.DRAW);
                changePointsAndOutcomes(awayTeam, GameOutcome.WIN, GameOutcome.DRAW);
            }
        }

        teamRepository.save(homeTeam);
        teamRepository.save(awayTeam);
    }

    private void changePointsAndOutcomes(Team team, GameOutcome oldOutcome, GameOutcome newOutcome) {
        switch (oldOutcome) {
            case WIN:
                team.setWin(team.getWin() - 1);
                team.setPoints(team.getPoints() - Points.WIN.getPoints());
                break;
            case DRAW:
                team.setDraw(team.getDraw() - 1);
                team.setPoints(team.getPoints() - Points.DRAW.getPoints());
                break;
            case LOSE:
                team.setLose(team.getLose() - 1);
                team.setPoints(team.getPoints() - Points.LOSE.getPoints());
                break;
        }
        switch (newOutcome) {
            case WIN:
                team.setWin(team.getWin() + 1);
                team.setPoints(team.getPoints() + Points.WIN.getPoints());
                break;
            case DRAW:
                team.setDraw(team.getDraw() + 1);
                team.setPoints(team.getPoints() + Points.DRAW.getPoints());
                break;
            case LOSE:
                team.setLose(team.getLose() + 1);
                team.setPoints(team.getPoints() + Points.LOSE.getPoints());
                break;
        }
    }

    private Group checkGroup(GameTO gameTO) {
        Group group = groupRepository.findByName(gameTO.getGroup());
        if (group == null) {
            group = new Group();
            group.setName(gameTO.getGroup());
            group.setLeagueTitle(gameTO.getLeagueTitle());
            group.setMatchday(1);

            return groupRepository.save(group);
        } else {
            if (gameTO.getMatchday() > group.getMatchday()) {
                group.setMatchday(gameTO.getMatchday());
                return groupRepository.save(group);
            }
        }
        return group;
    }

    private void saveTeamStats(GameTO gameTO, Group group) throws GenericException {
        GamePK gamePK = new GamePK(findTeamId(gameTO.getHomeTeam()), findTeamId(gameTO.getAwayTeam()));
        Optional<Game> gameOptional = gameRepository.findById(gamePK);
        if (gameOptional.isPresent()) {
            throw new GenericException("Game between: " + gameTO.getHomeTeam() + " and " + gameTO.getAwayTeam() + " already exists. Can't add new one");
        }
        int homeGoals = splitScore(gameTO.getScore(), 0);
        int awayGoals = splitScore(gameTO.getScore(), 1);

        Team homeTeam = findTeamByName(gameTO.getHomeTeam());
        Team awayTeam = findTeamByName(gameTO.getAwayTeam());

        if (homeTeam == null) {
            homeTeam = new Team(group, gameTO.getHomeTeam());
        }

        if (awayTeam == null) {
            awayTeam = new Team(group, gameTO.getAwayTeam());
        }

        computeResult(homeTeam, homeGoals, awayGoals);
        computeResult(awayTeam, awayGoals, homeGoals);

        teamRepository.save(homeTeam);
        teamRepository.save(awayTeam);
    }

    private void computeResult(Team team, int goals, int goalsAgainst) {
        team.setPlayedGames(team.getPlayedGames() + 1);

        addGoals(team, goals, goalsAgainst);

        if (goals > goalsAgainst) {
            team.setWin(team.getWin() + 1);
            team.setPoints(team.getPoints() + Points.WIN.getPoints());
        } else if (goals < goalsAgainst) {
            team.setLose(team.getLose() + 1);
            team.setPoints(team.getPoints() + Points.LOSE.getPoints());
        } else {
            team.setDraw(team.getDraw() + 1);
            team.setPoints(team.getPoints() + Points.DRAW.getPoints());
        }
    }

    private void addGoals(Team team, int goals, int goalsAgainst) {
        team.setGoals(team.getGoals() + goals);
        team.setGoalsAgainst(team.getGoalsAgainst() + goalsAgainst);
        team.setGoalDifference(team.getGoalDifference() + (goals - goalsAgainst));
    }

    private void subtractGoals(Team team, int goals, int goalsAgainst) {
        team.setGoals(team.getGoals() - goals);
        team.setGoalsAgainst(team.getGoalsAgainst() - goalsAgainst);
        team.setGoalDifference(team.getGoalDifference() - (goals - goalsAgainst));
    }

    private Team findTeamByName(String teamName) {
        return teamRepository.findByName(teamName);
    }

    private void saveGame(GameTO gameTO, Group group) throws GenericException {
        GamePK gamePK = new GamePK(findTeamId(gameTO.getHomeTeam()), findTeamId(gameTO.getAwayTeam()));
        Optional<Game> gameOptional = gameRepository.findById(gamePK);
        if (gameOptional.isPresent()) {
            throw new GenericException("There is already a game featuring: " + gameTO.getHomeTeam() + " and " + gameTO.getAwayTeam());
        }
        Game game = new Game(gamePK, group, gameTO);

        gameRepository.save(game);
    }

    private Integer findTeamId(String teamName) {
        return teamRepository.findIdByName(teamName);
    }

    private int splitScore(String score, int index) throws GenericException {
        if (StringUtils.isEmpty(score)) {
            throw new GenericException("Score field must not be empty or null");
        }
        try {
            return Integer.parseInt(score.split(":")[index]);
        } catch (NumberFormatException e) {
            throw new GenericException(e.getMessage());
        }
    }
}
