package com.example.learning_english.dto.Exercise;

import com.example.learning_english.entity.Course;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ExerciseDto {
    @NotBlank(message = "Exercise question title is mandatory")
    private String question;
    @NotBlank(message = "Course title is mandatory")
    private String course_title;

}
