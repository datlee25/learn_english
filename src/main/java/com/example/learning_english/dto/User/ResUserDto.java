package com.example.learning_english.dto.User;

import com.example.learning_english.entity.GroupMember;
import com.example.learning_english.entity.enums.EGroupLevel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ResUserDto {
    private int id;
    private String username;
    private String email;
    private String age;
    public EGroupLevel level;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean enabled;
    private List<Integer> groupId;

}
