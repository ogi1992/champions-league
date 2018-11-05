package com.championsleague.controllers;

import com.championsleague.exceptions.GenericException;
import com.championsleague.services.GroupService;
import com.championsleague.to.GameTO;
import com.championsleague.to.GroupTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<GroupTO> addResults(@RequestBody List<GameTO> results) throws GenericException {
        return groupService.addResults(results);
    }

    @PutMapping
    public List<GroupTO> updateResults(@RequestBody List<GameTO> results) throws GenericException {
        return groupService.updateResults(results);
    }
}
