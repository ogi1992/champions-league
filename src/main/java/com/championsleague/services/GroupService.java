package com.championsleague.services;

import to.GameTO;
import to.GroupTO;

import java.util.List;

public interface GroupService {

    List<GroupTO> getGroupInfo();

    List<GroupTO> addResults(List<GameTO> results);
}
