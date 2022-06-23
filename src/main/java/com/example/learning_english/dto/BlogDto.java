package com.example.learning_english.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BlogDto {
    @NotBlank(message = "Blog title is mandatory")
    public String title;
    public String synopsis;
    @NotBlank(message = "Blog detail is mandatory")
    public String detail;
}
