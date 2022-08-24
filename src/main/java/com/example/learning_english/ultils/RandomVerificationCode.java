package com.example.learning_english.ultils;

import java.util.Random;

public class RandomVerificationCode {
    public static String randomVerificationCode(){
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d",number);
    }
}
