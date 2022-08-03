package com.example.learning_english.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Course title is mandatory")
    private String title;

    private String detail;

    private String image;

    private int participantAge;

    private double qualification;

    private int number_of_participants;

    private double stars_rated;

}

