package com.example.learning_english.specifications;

import com.example.learning_english.entity.search.FilterRequest;
import com.example.learning_english.entity.search.SearchRequest;
import com.example.learning_english.entity.search.SortRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    public SearchRequest searchRequest;


    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.equal(cb.literal(Boolean.TRUE),Boolean.TRUE);
        for (FilterRequest filter : this.searchRequest.getFilters()){
            predicate = filter.getOperator().build(root,cb,filter,predicate);
        }

        List<Order> orders =new ArrayList<>();
        for (SortRequest sort : this.searchRequest.getSorts()){
            orders.add(sort.getDirection().build(root, cb, sort));
        }

        query.orderBy(orders);
        return predicate;
    }
}
