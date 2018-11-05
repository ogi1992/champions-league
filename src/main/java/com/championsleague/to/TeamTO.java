package com.championsleague.to;

import com.championsleague.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamTO implements Serializable, Comparable<TeamTO> {

    private int rank;

    private String team;

    private int playedGames;

    private int points;

    private int goals;

    private int goalsAgainst;

    private int goalDifference;

    private int win;

    private int lose;

    private int draw;

    public TeamTO(Team team) {
        this.team = team.getName();
        this.playedGames = team.getPlayedGames();
        this.points = team.getPoints();
        this.goals = team.getGoals();
        this.goalsAgainst = team.getGoalsAgainst();
        this.goalDifference = team.getGoalDifference();
        this.win = team.getWin();
        this.lose = team.getLose();
        this.draw = team.getDraw();
    }

    @Override
    public int compareTo(TeamTO o) {
        int compare = Integer.compare(o.getPoints(), getPoints());
        if (compare == 0) {
            compare = Integer.compare(o.getGoals(), getGoals());
            if (compare == 0) {
                compare = Integer.compare(o.getGoalDifference(), getGoalDifference());
            }
        }
        return compare;
    }
}
