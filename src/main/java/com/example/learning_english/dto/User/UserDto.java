package com.example.learning_english.dto.User;

import com.example.learning_english.entity.enums.EGroupLevel;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    public String password;
    private String email;
    public EGroupLevel level;
}
