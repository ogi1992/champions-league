package com.championsleague.entities.specification;

import com.championsleague.entities.Game;
import com.championsleague.entities.Group;
import com.championsleague.entities.Team;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Map;

public class GameSpecification implements Specification<Game> {

    private SearchCriteria criteria;

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public GameSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase("equal")) {
            if (root.get(criteria.getKey()).getJavaType() == Group.class) {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            } else if (root.get(criteria.getKey()).getJavaType() == Team.class) {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("between")) {
            if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                Map<String, Date> dates = (Map<String, Date>) criteria.getValue();
                return builder.between(root.get(criteria.getKey()), dates.get("from"), dates.get("to"));
            }
        }
        return null;
    }
}
