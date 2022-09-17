package com.example.learning_english.SearchCriteria;

import com.example.learning_english.payload.request.search.FilterRequest;

import javax.persistence.criteria.*;
import java.util.List;

public enum Operator {

    LIKE {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Expression<String> key = root.get(request.getKey());
            return cb.and(cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate);
        }
    },
    EQUAL {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Expression<?> key = root.get(request.getKey());
            return cb.and(cb.equal(key, request.getValue()), predicate);
        }
    },
    LESS_THAN_OR_EQUAL{
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            String typeOfKey = root.get(request.getKey()).getJavaType().getTypeName();
            if (typeOfKey.equals("int")){
                Expression<Integer> key = root.get(request.getKey());
                return cb.and(cb.lessThanOrEqualTo(key,(Integer) request.getValue()),predicate);
            }else{
                Expression<Double> key = root.get(request.getKey());
                return cb.and(cb.lessThanOrEqualTo(key,(Double) request.getValue()),predicate);
            }
        }
    },
    IN{
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate){
            List<Object> values = request.getValues();
            CriteriaBuilder.In<Object> inClause = cb.in(root.get(request.getKey()));
            for (Object value:values){
                inClause.value(request.getFieldType().parse(value.toString()));
            }
            return cb.and(inClause,predicate);
        }
    },
    BETWEEN {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            String typeOfKey = root.get(request.getKey()).getJavaType().getTypeName();
            if (typeOfKey.equals("int")){
                Expression<Integer> key = root.get(request.getKey());
                return cb.and(cb.between(key,
                        (Integer) request.getValue(),
                        (Integer) request.getValueTo()),predicate);
            }else {
                Expression<Double> key = root.get(request.getKey());
                return cb.and(cb.between(key,
                        (Double) request.getValue(),
                        (Double) request.getValueTo()), predicate);
            }
        }
    };

    public abstract <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate);

}
