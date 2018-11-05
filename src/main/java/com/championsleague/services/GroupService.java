package com.championsleague.services;

import com.championsleague.to.GameTO;
import com.championsleague.to.GroupTO;

import java.util.List;

public interface GroupService {

    List<GroupTO> getGroupInfo();

    List<GroupTO> addResults(List<GameTO> results);
}
