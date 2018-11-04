package com.championsleague.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "group")
    private List<Team> teams;

    @OneToMany(mappedBy = "group")
    private List<Game> games;

    private String name;

    private String leagueTitle;

    private int matchday;

}
