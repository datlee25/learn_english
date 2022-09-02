package com.example.learning_english.dto.UserScore;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResScoreBoard {
    Map<String, BigDecimal> top10User;
    Map<String, BigDecimal> currentUserScore;
}
