package com.championsleague.to;

import java.io.Serializable;
import java.util.Date;

public class FilterTO implements Serializable {

    private Date from;

    private Date to;

    private String group;

    private String team;

    public FilterTO() {

    }

    public FilterTO(Date from, Date to, String group, String team) {
        this.from = from;
        this.to = to;
        this.group = group;
        this.team = team;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public String getGroup() {
        return group;
    }

    public String getTeam() {
        return team;
    }
}
