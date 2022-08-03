package com.example.learning_english.dto.Group;

import com.example.learning_english.entity.User;
import com.example.learning_english.entity.enums.EGroupLevel;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
public class ResGroupDto {
    private String name;
    private EGroupLevel groupLevel;
    private Set<User> users = new HashSet<>();
}
