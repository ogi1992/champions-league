package com.championsleague.to;

import com.championsleague.entities.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameTO implements Serializable {

    private String leagueTitle;

    private int matchday;

    private String group;

    private String homeTeam;

    private String awayTeam;

    private Date kickoffAt;

    private String score;

    public GameTO(Game game, String homeTeam, String awayTeam) {
        this.leagueTitle = game.getLeagueTitle();
        this.matchday = game.getMatchday();
        this.group = game.getGroup().getName();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.kickoffAt = game.getKickoffAt();
        this.score = game.getScore();
    }
}
