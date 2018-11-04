package com.championsleague.entities;

import com.championsleague.entities.pk.GamePK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @EmbeddedId
    private GamePK id;

    @ManyToOne
    private Group group;

    private String score;

    private Date kickoffAt;

    private int matchday;

    private String leagueTitle;
}
