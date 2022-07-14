package com.example.learning_english.dto.Answer;

import com.example.learning_english.entity.enums.EAnswerKey;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AnswerDto {
    private EAnswerKey answer_key;
    @NotBlank(message = "Answer value is mandatory")
    private String answer_value;
    @NotBlank(message = "Exercise question is mandatory")
    private String exercise_question;
    private boolean correct_answer;
}
