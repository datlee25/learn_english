package com.example.learning_english.entity;

import com.example.learning_english.dto.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(nullable = false)
    @NotBlank(message = "Course title is mandatory")
    public String title;
    public String detail;
    public String image;
    public int participantAge;
    public double qualification;
    public int number_of_participants;

    public Course(CourseDto courseDto) {
        this.title = courseDto.title;
        this.detail = courseDto.detail;
        this.image = courseDto.image;
        this.participantAge = courseDto.participantAge;
        this.qualification = courseDto.qualification;
        this.number_of_participants = courseDto.number_of_participants;
    }
};

