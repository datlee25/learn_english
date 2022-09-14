package com.example.learning_english.dto.User;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    public String password;
    private String email;
    private String qualification;
}
