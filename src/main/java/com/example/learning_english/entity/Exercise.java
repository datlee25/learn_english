package com.example.learning_english.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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
    private String question;

    private int course_id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id", updatable = false, insertable = false)
    @JsonBackReference
    private Course course;
    @JsonManagedReference
    @OneToMany(mappedBy = "exercise")
    private Set<Answer> answer;
}
