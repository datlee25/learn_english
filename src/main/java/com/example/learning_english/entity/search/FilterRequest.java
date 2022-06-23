package com.example.learning_english.entity.search;

import com.example.learning_english.SearchCriteria.Operator;
import com.example.learning_english.entity.enums.EFieldType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class FilterRequest implements Serializable {
    public String key;
    public Operator operator;
    public EFieldType fieldType;
    private transient Object value;
    private transient Object valueTo;
    private transient List<Object> values;
}
