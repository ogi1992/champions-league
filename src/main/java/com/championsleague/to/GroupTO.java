package com.championsleague.to;

import com.championsleague.entities.Group;

import java.io.Serializable;
import java.util.List;

public class GroupTO implements Serializable {

    private String leagueTitle;

    private int matchday;

    private String group;

    private List<TeamTO> standing;

    public GroupTO() {

    }

    public GroupTO(Group group, List<TeamTO> teams) {
        this.leagueTitle = group.getLeagueTitle();
        this.matchday = group.getMatchday();
        this.group = group.getName();
        this.standing = teams;
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

    public List<TeamTO> getStanding() {
        return standing;
    }

    public void setStanding(List<TeamTO> standing) {
        this.standing = standing;
    }
}
