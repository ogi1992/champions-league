package com.championsleague.services;

import com.championsleague.exceptions.GenericException;
import com.championsleague.to.GameTO;
import com.championsleague.to.GroupTO;

import java.util.List;

public interface GroupService {

    List<GroupTO> getGroupInfo();

    List<GroupTO> addResults(List<GameTO> results) throws GenericException;

    List<GroupTO> updateResults(List<GameTO> results) throws GenericException;
}
