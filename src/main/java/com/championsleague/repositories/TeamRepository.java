package com.championsleague.repositories;

import com.championsleague.entities.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    Optional<Team> findByName(String homeTeam);

    @Query("SELECT t.id FROM Team t WHERE t.name = :name")
    Integer findIdByName(@Param("name") String name);

    List<Team> findByGroup_Id(Integer id);

    @Query("SELECT t.name FROM Team t WHERE t.id = :id")
    String findNameById(Integer id);
}
