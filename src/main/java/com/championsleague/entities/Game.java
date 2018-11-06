package com.championsleague.entities;

import com.championsleague.entities.pk.GamePK;
import com.championsleague.to.GameTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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

    @NotEmpty(message = "Score must not be null or empty")
    @Pattern(regexp = "(0:|[1-9][0-9]*:)(0$|[1-9][0-9]*$)", message = "Please insert correct score")
    private String score;

    private Date kickoffAt;

    private int matchday;

    private String leagueTitle;

    public Game(GamePK id, Group group, GameTO gameTO) {
        this.id = id;
        this.group = group;
        this.score = gameTO.getScore();
        this.kickoffAt = gameTO.getKickoffAt();
        this.matchday = gameTO.getMatchday();
        this.leagueTitle = gameTO.getLeagueTitle();
    }

}
