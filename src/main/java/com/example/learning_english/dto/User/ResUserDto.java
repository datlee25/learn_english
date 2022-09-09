package com.example.learning_english.dto.User;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResUserDto {
    private int id;
    private String username;
    private String email;
    private String age;
    private String qualification;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean enabled;

}
