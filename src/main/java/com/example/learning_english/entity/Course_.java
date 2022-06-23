package com.example.learning_english.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Course.class)
public class Course_ {
    public static volatile SingularAttribute<Course, Integer> id;
    public static volatile SingularAttribute<Course, String> title;
    public static volatile SingularAttribute<Course, String> detail;
    public static volatile SingularAttribute<Course, Integer> participantAge;
    public static volatile SingularAttribute<Course, Double> qualification;
    public static volatile SingularAttribute<Course, Integer> number_of_participants;

}
