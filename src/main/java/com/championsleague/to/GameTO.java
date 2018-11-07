package com.championsleague.to;

import com.championsleague.entities.Game;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class GameTO implements Serializable {

    private String leagueTitle;

    private int matchday;

    @NotEmpty(message = "Group must not be null or empty")
    @Size(min = 1, max = 1)
    private String group;

    @NotEmpty(message = "Home team must not be null or empty")
    private String homeTeam;

    @NotEmpty(message = "Away team must not be null or empty")
    private String awayTeam;

    private Date kickoffAt;

    @NotEmpty(message = "Score must not be null or empty")
    @Pattern(regexp = "[0-9]+:[0-9]+", message = "Please insert correct score")
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

    public GameTO(String leagueTitle, int matchday, String group, String homeTeam, String awayTeam, Date kickoffAt, String score) {
        this.leagueTitle = leagueTitle;
        this.matchday = matchday;
        this.group = group;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.kickoffAt = kickoffAt;
        this.score = score;
    }

    public GameTO() {

    }

    public String getLeagueTitle() {
        return leagueTitle;
    }

    public void setLeagueTitle(String leagueTitle) {
        this.leagueTitle = leagueTitle;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Date getKickoffAt() {
        return kickoffAt;
    }

    public void setKickoffAt(Date kickoffAt) {
        this.kickoffAt = kickoffAt;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
