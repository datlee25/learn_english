package com.example.learning_english.dto.Answer;

import com.example.learning_english.entity.Exercise;
import com.example.learning_english.entity.enums.EAnswerKey;

public class GetAnswerDto {
    private EAnswerKey answer_key;
    private String answer_value;
    private String exercise_question;
    private boolean correct_answer;
    private Exercise exercise;

}
