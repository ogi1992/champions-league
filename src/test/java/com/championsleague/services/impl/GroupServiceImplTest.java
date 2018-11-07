package com.championsleague.services.impl;

import com.championsleague.entities.Game;
import com.championsleague.entities.Group;
import com.championsleague.entities.Team;
import com.championsleague.entities.pk.GamePK;
import com.championsleague.exceptions.GenericException;
import com.championsleague.repositories.GameRepository;
import com.championsleague.repositories.GroupRepository;
import com.championsleague.repositories.TeamRepository;
import com.championsleague.to.GameListTO;
import com.championsleague.to.GameTO;
import com.championsleague.to.GroupTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceImplTest {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GameRepository gameRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private TeamRepository teamRepository;

    private GameListTO gameListTO;
    private GameTO arsenalPSGGameTO;
    private GameTO psgArsenalGameTO;
    private Team teamPSG;

    @Before
    public void setUp() {
        gameListTO = new GameListTO();
        Group group = new Group();
        group.setId(1);
        group.setName("A");

        Team teamArsenal = new Team();
        teamArsenal.setId(1);
        teamArsenal.setName("Arsenal");
        teamArsenal.setGroup(group);

        teamPSG = new Team();
        teamPSG.setId(2);
        teamPSG.setName("PSG");
        teamPSG.setGroup(group);

        group.setTeams(new HashSet<Team>() {{
            add(teamArsenal);
            add(teamPSG);
        }});

        Game arsenalPSG = new Game();
        GamePK arsenalPSGGamePK = new GamePK();
        arsenalPSGGamePK.setHomeTeamId(teamArsenal.getId());
        arsenalPSGGamePK.setAwayTeamId(teamPSG.getId());
        arsenalPSG.setId(arsenalPSGGamePK);
        arsenalPSG.setGroup(group);
        arsenalPSG.setHomeTeam(teamArsenal);
        arsenalPSG.setAwayTeam(teamPSG);

        Game psgArsenal = new Game();
        GamePK psgArsenalGamePK = new GamePK();
        psgArsenalGamePK.setHomeTeamId(teamPSG.getId());
        psgArsenalGamePK.setAwayTeamId(teamArsenal.getId());
        psgArsenal.setId(psgArsenalGamePK);
        psgArsenal.setGroup(group);
        psgArsenal.setHomeTeam(teamPSG);
        psgArsenal.setAwayTeam(teamArsenal);

        arsenalPSGGameTO = new GameTO();
        arsenalPSGGameTO.setHomeTeam(teamArsenal.getName());
        arsenalPSGGameTO.setAwayTeam(teamPSG.getName());
        arsenalPSGGameTO.setGroup(group.getName());
        arsenalPSGGameTO.setScore("2:0");

        psgArsenalGameTO = new GameTO();
        psgArsenalGameTO.setHomeTeam(teamPSG.getName());
        psgArsenalGameTO.setAwayTeam(teamArsenal.getName());
        psgArsenalGameTO.setGroup(group.getName());
        psgArsenalGameTO.setScore("1:1");

        when(groupRepository.findByName(arsenalPSGGameTO.getGroup())).thenReturn(group);
        when(groupRepository.findByName(psgArsenalGameTO.getGroup())).thenReturn(null);
        when(groupRepository.save(group)).thenReturn(group);
        when(gameRepository.findById(arsenalPSG.getId())).thenReturn(Optional.of(arsenalPSG));
        when(gameRepository.findById(psgArsenal.getId())).thenReturn(Optional.of(psgArsenal));
        when(teamRepository.findByName(teamArsenal.getName())).thenReturn(Optional.of(teamArsenal));
        when(teamRepository.findByName(teamPSG.getName())).thenReturn(Optional.of(teamPSG));
        when(teamRepository.save(teamArsenal)).thenReturn(teamArsenal);
        when(teamRepository.save(teamPSG)).thenReturn(teamPSG);
        when(groupRepository.findAll()).thenReturn(Collections.singletonList(group));
        when(teamRepository.findByGroup_Id(group.getId())).thenReturn(Arrays.asList(teamArsenal, teamPSG));
    }

    @Test
    public void addResults_Success() {
        gameListTO.setGames(Arrays.asList(arsenalPSGGameTO, psgArsenalGameTO));
        List<GroupTO> groups = groupService.addResults(gameListTO.getGames());
        assertEquals(1, groups.size());
        assertEquals(2, groups.get(0).getStanding().size());
    }

    @Test(expected = GenericException.class)
    public void addResults_GenericException() {
        when(teamRepository.findByName(teamPSG.getName())).thenReturn(Optional.empty());
        gameListTO.setGames(Arrays.asList(arsenalPSGGameTO, psgArsenalGameTO));
        groupService.addResults(gameListTO.getGames());
    }

    @Test
    public void updateResults() {
    }
}