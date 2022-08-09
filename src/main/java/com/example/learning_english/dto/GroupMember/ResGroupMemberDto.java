package com.example.learning_english.dto.GroupMember;

import com.example.learning_english.dto.User.ResUserDto;
import lombok.Data;

@Data
public class ResGroupMemberDto{
    private ResUserDto user;
    private String createAt;
    private String updateAt;
}
