package com.championsleague.services;

import to.GamesTO;
import to.GroupTO;

import java.util.List;

public interface GroupService {

    List<GroupTO> getGroupInfo();

    List<GroupTO> addResults(GamesTO results);
}
