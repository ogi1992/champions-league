package com.championsleague.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterTO {

    Date from;

    Date to;

    String group;

    String team;
}
