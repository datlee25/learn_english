package com.example.learning_english.dto.Exercise;

import com.example.learning_english.entity.Answer;
import com.example.learning_english.entity.Question;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ResExerciseDto {
    private int id;
    private String name;
    private String description;
    private String course_id;
}