package com.example.learning_english.ultils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDateTime {

    public static String formatDateTime(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
    }
}
