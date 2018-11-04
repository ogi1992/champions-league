package to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamTO implements Serializable {

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

    public TeamTO(String team, int playedGames, int points, int goals, int goalsAgainst, int goalDifference, int win, int lose, int draw) {
        this.team = team;
        this.playedGames = playedGames;
        this.points = points;
        this.goals = goals;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }
}
