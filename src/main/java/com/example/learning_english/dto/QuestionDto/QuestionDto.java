package com.example.learning_english.dto.QuestionDto;

import com.example.learning_english.entity.Answer;
import lombok.Data;

import java.util.Set;

@Data
public class QuestionDto {
    private int id;
    private String question;
    private Set<Answer> answers;
    private int exercise_id;

}
