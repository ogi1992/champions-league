package com.championsleague.entities;

import com.championsleague.entities.pk.GamePK;
import com.championsleague.to.GameTO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "game")
public class Game {

    @EmbeddedId
    private GamePK id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @NotEmpty(message = "Score must not be null or empty")
    @Pattern(regexp = "[0-9]+:[0-9]+", message = "Please insert correct score")
    private String score;

    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Team awayTeam;

    private Date kickoffAt;

    private int matchday;

    private String leagueTitle;

    public Game() {

    }

    public Game(GamePK id, Group group, GameTO gameTO) {
        this.id = id;
        this.group = group;
        this.score = gameTO.getScore();
        this.kickoffAt = gameTO.getKickoffAt();
        this.matchday = gameTO.getMatchday();
        this.leagueTitle = gameTO.getLeagueTitle();
    }

    public GamePK getId() {
        return id;
    }

    public void setId(GamePK id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getKickoffAt() {
        return kickoffAt;
    }

    public void setKickoffAt(Date kickoffAt) {
        this.kickoffAt = kickoffAt;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

    public String getLeagueTitle() {
        return leagueTitle;
    }

    public void setLeagueTitle(String leagueTitle) {
        this.leagueTitle = leagueTitle;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
}
