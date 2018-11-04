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
}
