package com.example.learning_english.dto.Exercise;

import com.example.learning_english.entity.Answer;
import lombok.Data;

import java.util.Set;

@Data
public class ResExerciseDto {
    private String question;
    private Set<Answer> answer;
}