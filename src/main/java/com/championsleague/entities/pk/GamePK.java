package com.championsleague.entities.pk;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GamePK implements Serializable {

    private Integer homeTeamId;

    private Integer awayTeamId;

    public GamePK() {

    }

    public GamePK(Integer homeTeamId, Integer awayTeamId) {
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Integer getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Integer awayTeamId) {
        this.awayTeamId = awayTeamId;
    }
}
