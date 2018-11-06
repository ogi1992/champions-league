package com.championsleague.controllers;

import com.championsleague.exceptions.GenericException;
import com.championsleague.services.GameService;
import com.championsleague.to.FilterTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameController {

    private final @NonNull
    GameService gameService;

    @PostMapping(value = "/filter")
    public ResponseEntity<?> filterResults(@RequestBody FilterTO filters) throws GenericException {
        return new ResponseEntity<>(gameService.filterResults(filters), HttpStatus.OK);
    }
}
