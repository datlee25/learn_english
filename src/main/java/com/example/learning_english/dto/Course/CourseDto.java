package com.example.learning_english.dto.Course;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class CourseDto {
    @NotBlank(message = "Course title is mandatory")
    public String title;
    public String detail;
    public String image;
    public int participantAge;
    public double qualification;
    public int numberParticipants;

    public Set<String> exercises;

}