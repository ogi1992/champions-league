package com.championsleague.to;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class GameListTO {

    @Valid
    @NotEmpty
    private List<GameTO> games;

    public GameListTO() {

    }

    public List<GameTO> getGames() {
        return games;
    }

    public void setGames(List<GameTO> games) {
        this.games = games;
    }
}
