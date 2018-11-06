package com.championsleague.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`group`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "group")
    private Set<Team> teams;

    @OneToMany(mappedBy = "group")
    private Set<Game> games;

    @NotEmpty(message = "Name of group must not be null or empty")
    private String name;

    private String leagueTitle;

    private int matchday;

}
