package com.example.learning_english.dto.UserScore;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResScoreBoard {
    private List<ResUserScoreDto> top10User;
    private ResUserScoreDto currentUserScore;
}
