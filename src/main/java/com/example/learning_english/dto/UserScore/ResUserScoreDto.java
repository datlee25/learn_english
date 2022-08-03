package com.example.learning_english.dto.UserScore;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResUserScoreDto {
    private String name;
    private BigDecimal score;
}
