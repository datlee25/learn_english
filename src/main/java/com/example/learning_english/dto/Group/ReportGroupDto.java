package com.example.learning_english.dto.Group;

import com.example.learning_english.entity.enums.EGroupLevel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReportGroupDto {
    private String name;
    private EGroupLevel groupLevel;
    private BigDecimal price;

}
