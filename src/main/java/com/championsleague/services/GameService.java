package com.championsleague.services;

import com.championsleague.exceptions.GenericException;
import com.championsleague.to.FilterTO;
import com.championsleague.to.GameTO;

import java.util.List;

public interface GameService {

    List<GameTO> filterResults(FilterTO filters) throws GenericException;
}
