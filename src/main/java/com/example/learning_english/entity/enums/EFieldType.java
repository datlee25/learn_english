package com.example.learning_english.entity.enums;

import java.math.BigDecimal;

public enum EFieldType {
    DOUBLE{
        public Object parse(String value){return Double.valueOf(value);}
    },
    INTEGER{
        public Object parse(String value){return Integer.valueOf(value);}
    },
    DECIMAL{
        public Object parse(String value){return BigDecimal.valueOf(Long.parseLong(value));}
    },
    STRING{
        public Object parse(String value){return value;}
    };

    public abstract Object parse(String value);
}