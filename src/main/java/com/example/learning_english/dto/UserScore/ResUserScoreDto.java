package com.example.learning_english.dto.UserScore;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ResUserScoreDto {
    private String name;
    private BigDecimal score;
    private int rank;

    public ResUserScoreDto(String name, BigDecimal score) {
        this.name = name;
        this.score = score;
    }
}