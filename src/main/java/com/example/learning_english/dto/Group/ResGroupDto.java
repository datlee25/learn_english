package com.example.learning_english.dto.Group;

import com.example.learning_english.dto.GroupMember.ResGroupMemberDto;
import com.example.learning_english.dto.User.ResUserDto;
import com.example.learning_english.entity.GroupMember;
import com.example.learning_english.entity.User;
import com.example.learning_english.entity.enums.EGroupLevel;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Data
public class ResGroupDto {
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private EGroupLevel groupLevel;
    private Set<ResGroupMemberDto> groupMembers = new HashSet<>();
    private BigDecimal price;

}