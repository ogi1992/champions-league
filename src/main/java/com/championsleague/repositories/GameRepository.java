package com.championsleague.repositories;

import com.championsleague.entities.Game;
import com.championsleague.entities.pk.GamePK;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, GamePK> {
}
