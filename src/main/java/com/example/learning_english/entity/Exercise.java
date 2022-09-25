package com.example.learning_english.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    private int course_id;

    @ManyToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "course_id", updatable = false, insertable = false)
    @JsonBackReference
    private Course course;

    @JsonManagedReference
    @OneToMany(mappedBy = "exercise",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Question> questions;

}
