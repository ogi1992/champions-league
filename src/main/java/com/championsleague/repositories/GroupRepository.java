package com.championsleague.repositories;

import com.championsleague.entities.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {

    List<Group> findAll();

    Group findByName(String group);
}
