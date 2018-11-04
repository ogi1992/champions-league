package com.championsleague.controllers;

import com.championsleague.services.GroupService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import to.GameTO;
import to.GroupTO;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {

    private final @NonNull
    GroupService groupService;

    @GetMapping
    public List<GroupTO> getGroups() {
        return groupService.getGroupInfo();
    }

    @PostMapping
    public List<GroupTO> addResults(@RequestBody List<GameTO> results) {
        return groupService.addResults(results);
    }
}
