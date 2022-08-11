package com.example.learning_english.dto.Exercise;

import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ExerciseDto {
    @NotBlank(message = "Exercise name is mandatory")
    private String name;
    @NotBlank(message = "Exercise description is mandatory")
    private String description;
    private int course_id;
    private List<Question> questions;

}
