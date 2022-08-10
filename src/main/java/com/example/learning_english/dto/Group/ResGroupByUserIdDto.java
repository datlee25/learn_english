package com.example.learning_english.dto.Group;

import com.example.learning_english.dto.GroupMember.ResGroupMemberDto;
import com.example.learning_english.entity.enums.EGroupLevel;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class ResGroupByUserIdDto {
    private String name;
    @Enumerated(EnumType.STRING)
    private EGroupLevel groupLevel;
    private BigDecimal price;
    private String createAt;
    private String updateAt;
}
