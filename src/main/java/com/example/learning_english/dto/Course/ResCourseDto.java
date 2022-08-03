package com.example.learning_english.dto.Course;

import lombok.Data;

import java.util.Set;

@Data
public class ResCourseDto {
    public String title;
    public String detail;
    public String image;
    public int participantAge;
    public double qualification;
    public int number_of_participants;
}
