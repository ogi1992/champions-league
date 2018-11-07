package com.championsleague.controllers;

import com.championsleague.exceptions.GenericException;
import com.championsleague.services.GroupService;
import com.championsleague.to.GameListTO;
import com.championsleague.to.GameTO;
import com.championsleague.to.GroupTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupTO> getGroups() {
        return groupService.getGroupInfo();
    }

    @PostMapping
    public ResponseEntity<?> addResults(@Valid @RequestBody GameListTO results) throws GenericException {
        return new ResponseEntity<>(groupService.addResults(results.getGames()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateResults(@Valid @RequestBody GameListTO results) throws GenericException {
        return new ResponseEntity<>(groupService.updateResults(results.getGames()), HttpStatus.OK);
    }
}
