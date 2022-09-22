package com.example.learning_english.dto.Group;

import com.example.learning_english.entity.User;
import com.example.learning_english.entity.enums.EGroupLevel;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class GroupDto {
    private String name;
    private EGroupLevel groupLevel;
    private BigDecimal price;
}