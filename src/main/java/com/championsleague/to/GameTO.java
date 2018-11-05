package com.championsleague.to;

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
}
