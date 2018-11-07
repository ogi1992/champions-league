package com.championsleague;

import com.championsleague.entities.Group;
import com.championsleague.entities.Team;
import com.championsleague.repositories.GroupRepository;
import com.championsleague.repositories.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChampionsLeagueTest {

    @Test
    public void contextLoads() {
    }

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void test() {

        Group group = new Group();
        group.setName("Grupa 9");
        group.setMatchday(2);
        groupRepository.save(group);
        Team team = new Team(group, "Mrtva ekipa");
        teamRepository.save(team);
        List<Group> c = groupRepository.findAll();
        List<Team> teams = teamRepository.findByGroup_Id(group.getId());
        System.out.println("c" + c);

    }
}
