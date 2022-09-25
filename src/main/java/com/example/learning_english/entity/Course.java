package com.example.learning_english.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
    @JsonIgnore
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Course title is mandatory")
    private String title;

    private String detail;

    @Column(columnDefinition = "TEXT")
    private String image;

    private int participantAge;

    private double qualification;

    @Column(nullable = true)
    private int numberParticipants;

    private double stars_rated;
    private int groupsId;

    @JsonManagedReference
    @OneToMany(mappedBy = "course",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Exercise> exercises;

}

