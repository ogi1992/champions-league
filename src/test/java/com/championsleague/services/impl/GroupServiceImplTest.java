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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
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
    private GameTO psgArsenalUpdated;
    private GameTO psgArsenalGameTO;
    private Team teamPSG;
    private Team teamArsenal;
    private Group group;
    private Game psgArsenal;
    private GamePK psgArsenalGamePK;

    @Before
    public void setUp() {
        gameListTO = new GameListTO();
        group = new Group();
        group.setId(1);
        group.setName("A");

        teamArsenal = new Team();
        teamArsenal.setId(1);
        teamArsenal.setName("Arsenal");
        teamArsenal.setGroup(group);
        teamArsenal.setWin(1);
        teamArsenal.setDraw(1);
        teamArsenal.setLose(0);
        teamArsenal.setGoals(3);
        teamArsenal.setGoalsAgainst(1);
        teamArsenal.setGoalDifference(2);

        teamPSG = new Team();
        teamPSG.setId(2);
        teamPSG.setName("PSG");
        teamPSG.setGroup(group);
        teamPSG.setWin(0);
        teamPSG.setDraw(1);
        teamPSG.setLose(1);
        teamPSG.setGoals(1);
        teamPSG.setGoalsAgainst(3);
        teamPSG.setGoalDifference(-2);

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

        psgArsenalGamePK = new GamePK();
        psgArsenalGamePK.setHomeTeamId(teamPSG.getId());
        psgArsenalGamePK.setAwayTeamId(teamArsenal.getId());

        psgArsenal = new Game();
        psgArsenal.setId(psgArsenalGamePK);
        psgArsenal.setGroup(group);
        psgArsenal.setHomeTeam(teamPSG);
        psgArsenal.setAwayTeam(teamArsenal);
        psgArsenal.setScore("1:1");

        arsenalPSGGameTO = new GameTO();
        arsenalPSGGameTO.setHomeTeam(teamArsenal.getName());
        arsenalPSGGameTO.setAwayTeam(teamPSG.getName());
        arsenalPSGGameTO.setGroup(group.getName());
        arsenalPSGGameTO.setScore("2:0");

        psgArsenalUpdated = new GameTO();
        psgArsenalUpdated.setHomeTeam(teamPSG.getName());
        psgArsenalUpdated.setAwayTeam(teamArsenal.getName());
        psgArsenalUpdated.setGroup(group.getName());
        psgArsenalUpdated.setScore("0:1");

        psgArsenalGameTO = new GameTO();
        psgArsenalGameTO.setHomeTeam(teamPSG.getName());
        psgArsenalGameTO.setAwayTeam(teamArsenal.getName());
        psgArsenalGameTO.setGroup(group.getName());
        psgArsenalGameTO.setScore("1:1");

        when(groupRepository.findByName(arsenalPSGGameTO.getGroup())).thenReturn(group);
        when(groupRepository.findByName(psgArsenalGameTO.getGroup())).thenReturn(null);
        when(groupRepository.save(group)).thenReturn(group);
        when(groupRepository.findAll()).thenReturn(Collections.singletonList(group));
        when(gameRepository.findById(arsenalPSG.getId())).thenReturn(Optional.of(arsenalPSG));
        when(gameRepository.findById(psgArsenal.getId())).thenReturn(Optional.of(psgArsenal));
        when(teamRepository.findByName(teamArsenal.getName())).thenReturn(Optional.of(teamArsenal));
        when(teamRepository.findByName(teamPSG.getName())).thenReturn(Optional.of(teamPSG));
        when(teamRepository.save(teamArsenal)).thenReturn(teamArsenal);
        when(teamRepository.save(teamPSG)).thenReturn(teamPSG);
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
    public void updateResults_noGroupException() {
        gameListTO.setGames(Collections.singletonList(psgArsenalUpdated));
        groupService.updateResults(gameListTO.getGames());
    }

    @Test
    public void updateResults_Success() {
        when(groupRepository.findByName(psgArsenalUpdated.getGroup())).thenReturn(group);
        when((gameRepository.findById(any(GamePK.class)))).thenReturn(Optional.of(psgArsenal));
        psgArsenal.setId(psgArsenalGamePK);
        when((gameRepository.findById(psgArsenalGamePK))).thenReturn(Optional.of(psgArsenal));
        gameListTO.setGames(Collections.singletonList(psgArsenalUpdated));
        List<GroupTO> groups = groupService.updateResults(gameListTO.getGames());
        assertEquals(1, groups.size());
        assertEquals(2, groups.get(0).getStanding().size());
        assertEquals(2, groups.get(0).getStanding().get(1).getLose());
    }
}