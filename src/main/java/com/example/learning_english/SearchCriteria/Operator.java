package com.example.learning_english.SearchCriteria;

import com.example.learning_english.payload.request.search.FilterRequest;

import javax.persistence.criteria.*;

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
