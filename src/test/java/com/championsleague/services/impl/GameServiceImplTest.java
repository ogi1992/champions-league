package com.championsleague.services.impl;

import com.championsleague.entities.Game;
import com.championsleague.entities.Group;
import com.championsleague.entities.Team;
import com.championsleague.entities.pk.GamePK;
import com.championsleague.entities.specification.GameSpecification;
import com.championsleague.exceptions.GenericException;
import com.championsleague.repositories.GameRepository;
import com.championsleague.repositories.GroupRepository;
import com.championsleague.repositories.TeamRepository;
import com.championsleague.to.FilterTO;
import com.championsleague.to.GameTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GameServiceImplTest {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private GameRepository gameRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private TeamRepository teamRepository;

    private FilterTO filters;

    @Before
    public void setUp() {
        filters = new FilterTO();
        List<Game> games = new ArrayList<>();

        Group group = new Group();
        group.setName("A");

        Team teamArsenal = new Team();
        teamArsenal.setId(1);
        teamArsenal.setName("Arsenal");
        teamArsenal.setGroup(group);

        Team teamPSG = new Team();
        teamPSG.setId(2);
        teamPSG.setName("PSG");
        teamPSG.setGroup(group);

        Game homeArsenal = new Game();
        GamePK homeArsenalPK = new GamePK(teamArsenal.getId(), teamPSG.getId());
        homeArsenal.setId(homeArsenalPK);
        homeArsenal.setHomeTeam(teamArsenal);
        homeArsenal.setAwayTeam(teamPSG);
        homeArsenal.setGroup(group);

        Game homePSG = new Game();
        GamePK homePSGPK = new GamePK(teamPSG.getId(), teamArsenal.getId());
        homePSG.setId(homePSGPK);
        homePSG.setHomeTeam(teamPSG);
        homePSG.setAwayTeam(teamArsenal);
        homePSG.setGroup(group);

        games.add(homeArsenal);
        games.add(homePSG);

        when(groupRepository.findByName(anyString())).thenReturn(group);
        when(gameRepository.findAll(any(Specification.class))).thenReturn(games);
        when(teamRepository.findNameById(teamArsenal.getId())).thenReturn(teamArsenal.getName());
        when(teamRepository.findNameById(teamPSG.getId())).thenReturn(teamPSG.getName());
        when(teamRepository.findByName(anyString())).thenReturn(Optional.of(teamArsenal));
    }

    @Test
    public void filterResults_filterByDate() throws GenericException {
        filters.setFrom(new Date());
        filters.setTo(new Date());
        List<GameTO> gameTOS = gameService.filterResults(filters);
        assertEquals(2, gameTOS.size());
    }

    @Test
    public void filterResults_filterByGroup() throws GenericException {
        filters.setGroup("A");
        List<GameTO> gameTOS = gameService.filterResults(filters);
        assertEquals(2, gameTOS.size());
    }

    @Test
    public void filterResults_filterByTeam() throws GenericException {
        filters.setTeam("Arsenal");
        List<GameTO> gameTOS = gameService.filterResults(filters);
        assertEquals(2, gameTOS.size());
    }

    @Test
    public void filterResults_filterByDateTeamAndGroup() throws GenericException {
        filters.setFrom(new Date());
        filters.setTo(new Date());
        filters.setGroup("A");
        filters.setTeam("Arsenal");
        List<GameTO> gameTOS = gameService.filterResults(filters);
        assertEquals(2, gameTOS.size());
    }

}