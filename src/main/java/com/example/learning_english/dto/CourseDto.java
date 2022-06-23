package com.example.learning_english.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CourseDto {
    @NotBlank(message = "Course title is mandatory")
    public String title;
    public String detail;
    public String image;
    public int participantAge;
    public double qualification;
    public int number_of_participants;
}
