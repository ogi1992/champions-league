package com.championsleague.to;

import com.championsleague.entities.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTO implements Serializable {

    private String leagueTitle;

    private int matchday;

    private String group;

    private List<TeamTO> standing;

    public GroupTO(Group group, List<TeamTO> teams) {
        this.leagueTitle = group.getLeagueTitle();
        this.matchday = group.getMatchday();
        this.group = group.getName();
        this.standing = teams;
    }
}
