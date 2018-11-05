package com.championsleague.services.impl;

import com.championsleague.entities.Game;
import com.championsleague.entities.Group;
import com.championsleague.entities.Team;
import com.championsleague.entities.pk.GamePK;
import com.championsleague.enums.Points;
import com.championsleague.repositories.GameRepository;
import com.championsleague.repositories.GroupRepository;
import com.championsleague.repositories.TeamRepository;
import com.championsleague.services.GroupService;
import com.championsleague.to.GameTO;
import com.championsleague.to.GroupTO;
import com.championsleague.to.TeamTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<GroupTO> addResults(List<GameTO> results) {
        for (GameTO gameTO : results) {
            Group group = groupRepository.findByName(gameTO.getGroup());
            if (group == null) {
                group = new Group();
                group.setName(gameTO.getGroup());
                group.setLeagueTitle(gameTO.getLeagueTitle());
                group.setMatchday(1);

                group = groupRepository.save(group);
            } else {
                if (gameTO.getMatchday() > group.getMatchday()) {
                    group.setMatchday(gameTO.getMatchday());
                    group = groupRepository.save(group);
                }
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
        Game game = new Game(gamePK, group, gameTO);

        gameRepository.save(game);
    }

    private Integer findTeamId(String teamName) {
        return teamRepository.findIdByName(teamName);
    }
}
