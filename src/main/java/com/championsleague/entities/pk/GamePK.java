package com.championsleague.entities.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GamePK implements Serializable {

    private Integer homeTeamId;

    private Integer awayTeamId;
}
