package to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTO implements Serializable {

    private String leagueTitle;

    private int matchday;

    private String group;

    private List<TeamTO> standing;
}
