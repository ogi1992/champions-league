package com.championsleague.services.impl;

import com.championsleague.entities.Game;
import com.championsleague.entities.Group;
import com.championsleague.entities.Team;
import com.championsleague.entities.pk.GamePK;
import com.championsleague.repositories.GameRepository;
import com.championsleague.repositories.GroupRepository;
import com.championsleague.repositories.TeamRepository;
import com.championsleague.services.GroupService;
import enums.Points;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import to.GameTO;
import to.GamesTO;
import to.GroupTO;
import to.TeamTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupServiceImpl implements GroupService {

    private final @NonNull
    GroupRepository groupRepository;

    private final @NonNull
    GameRepository gameRepository;

    private final @NonNull
    TeamRepository teamRepository;

    @Override
    public List<GroupTO> getGroupInfo() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> new GroupTO(group.getLeagueTitle(), group.getMatchday(), group.getName(), convertToTeamTO(group.getTeams())))
                .collect(Collectors.toList());
    }

    private List<TeamTO> convertToTeamTO(List<Team> teams) {
        Comparator<TeamTO> comparator = Comparator.comparing(TeamTO::getPoints)
                .thenComparing(TeamTO::getGoals)
                .thenComparing(TeamTO::getGoalDifference);

        return teams.stream()
                .map(team -> new TeamTO(team.getRank(), team.getName(), team.getPlayedGames(), team.getPoints(), team.getGoals(),
                        team.getGoalsAgainst(), team.getGoalDifference(), team.getWin(), team.getLose(), team.getDraw()))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupTO> addResults(GamesTO results) {
        List<GameTO> gameTOs = results.getGames();

        for (GameTO gameTO : gameTOs) {
            Group group = groupRepository.findByName(gameTO.getGroup());
            if (group == null) {
                group = new Group();
                group.setName(gameTO.getGroup());
                group.setLeagueTitle(gameTO.getLeagueTitle());
                group.setMatchday(1);

                groupRepository.save(group);
            } else {
                group.setMatchday(gameTO.getMatchDay());
                groupRepository.save(group);
            }
            updateTeamStats(gameTO, group);
            saveGame(gameTO, group);
        }

        return getGroupInfo();
    }

    private void updateTeamStats(GameTO gameTO, Group group) {
        int homeGoals = Integer.parseInt(gameTO.getScore().split(":")[0]);
        int awayGoals = Integer.parseInt(gameTO.getScore().split(":")[1]);

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
        team.setGoals(team.getGoals() + goals);
        team.setGoalsAgainst(team.getGoalsAgainst() + goalsAgainst);
        team.setGoalDifference(team.getGoalDifference() + (goals - goalsAgainst));

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

    private Team findTeamByName(String teamName) {
        return teamRepository.findByName(teamName);
    }

    private void saveGame(GameTO gameTO, Group group) {
        GamePK gamePK = new GamePK(findTeamId(gameTO.getHomeTeam()), findTeamId(gameTO.getAwayTeam()));
        Game game = new Game(gamePK, group, gameTO.getScore(), gameTO.getKickoffAt(),
                gameTO.getMatchDay(), gameTO.getLeagueTitle());

        gameRepository.save(game);
    }

    private Integer findTeamId(String teamName) {
        return teamRepository.findIdByName(teamName);
    }
}
