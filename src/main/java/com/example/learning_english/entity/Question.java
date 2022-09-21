package com.example.learning_english.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String question;
    private int exercise_id;

    @JsonManagedReference
    @OneToMany(mappedBy = "question")
    @ToString.Exclude
    private Set<Answer> answers;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "exercise_id", updatable = false, insertable = false)
    private Exercise exercise;

    public Question(String question, int exercise_id, Exercise exercise) {
        this.question = question;
        this.exercise_id = exercise_id;
        this.exercise = exercise;
    }
}
