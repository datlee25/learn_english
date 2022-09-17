package com.example.learning_english.entity.enums;

public enum EGroupLevel {
        LOW(5), MID(7), HARD(10);
    private final int qualification;

    public int getQualification(){
        return qualification;
    }
    EGroupLevel(int qualification) {
        this.qualification = qualification;
    }
}