package com.example.learning_english.dto.UserScore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class UserScoreDto {
    private String name;
    private BigDecimal score;
    private int userId;
    private int exerciseId;
}
