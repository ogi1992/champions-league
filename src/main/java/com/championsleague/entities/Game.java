package com.championsleague.entities;

import com.championsleague.entities.pk.GamePK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @EmbeddedId
    private GamePK id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private String score;

    private Date kickoffAt;

    private int matchday;

    private String leagueTitle;
}
