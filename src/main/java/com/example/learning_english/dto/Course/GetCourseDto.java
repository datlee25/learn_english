package com.example.learning_english.dto.Course;

import lombok.Data;

import java.util.List;

@Data
public class GetCourseDto {
    private int userId;
    private List<Object> groupId;
}
