package com.example.learning_english.entity;

import com.example.learning_english.entity.enums.EAnswerKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private EAnswerKey answer_key;

    @Column(nullable = false)
    @NotBlank
    private String answer_value;

    private boolean correct_answer;

    private int question_id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    @JoinColumn(name = "question_id", updatable = false, insertable = false)
    private Question question;
}
