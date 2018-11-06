package com.championsleague.services.impl;

import com.championsleague.entities.Game;
import com.championsleague.entities.specification.GameSpecificationBuilder;
import com.championsleague.exceptions.GenericException;
import com.championsleague.repositories.GameRepository;
import com.championsleague.repositories.GroupRepository;
import com.championsleague.repositories.TeamRepository;
import com.championsleague.services.GameService;
import com.championsleague.to.FilterTO;
import com.championsleague.to.GameTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameServiceImpl implements GameService {

    private final @NonNull
    GameRepository gameRepository;

    private final @NonNull
    TeamRepository teamRepository;

    private final @NonNull
    GroupRepository groupRepository;

    @Override
    public List<GameTO> filterResults(FilterTO filters) throws GenericException {
        if (filters == null) {
            throw new GenericException("Filters cannot be null");
        }
        GameSpecificationBuilder builder = new GameSpecificationBuilder();

        if (!StringUtils.isEmpty(filters.getGroup())) {
            builder.with("group", "equal", groupRepository.findByName(filters.getGroup()));
        }
        if (filters.getFrom() != null && filters.getTo() != null) {
            Map<String, Date> dates = new HashMap<>();
            dates.put("from", filters.getFrom());
            dates.put("to", filters.getTo());
            builder.with("kickoffAt", "between", dates);
        }
        Specification<Game> specification = builder.build();

        List<Game> games = gameRepository.findAll(specification);

        return games.stream()
                .map(game -> new GameTO(game, findTeamNameById(game.getId().getHomeTeamId()),
                        findTeamNameById(game.getId().getAwayTeamId())))
                .collect(Collectors.toList());
    }

    private String findTeamNameById(Integer teamId) {
        return teamRepository.findNameById(teamId);
    }
}
