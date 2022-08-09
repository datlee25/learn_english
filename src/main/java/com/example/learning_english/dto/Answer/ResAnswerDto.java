package com.example.learning_english.dto.Answer;

import com.example.learning_english.entity.Exercise;
import com.example.learning_english.entity.enums.EAnswerKey;
import lombok.Data;

@Data
public class ResAnswerDto {
    private EAnswerKey answer_key;
    private String answer_value;
    private boolean correct_answer;

}