package com.example.learning_english.controller;

import com.example.learning_english.dto.Course.ResCourseDto;
import com.example.learning_english.dto.Exercise.ResExerciseDto;
import com.example.learning_english.dto.Exercise.ExerciseDto;
import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.Exercise;
import com.example.learning_english.service.ExerciseService;
import com.example.learning_english.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.example.learning_english.util.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.util.ExceptionMessage.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/exercise")
public class ExerciseController {
    @Autowired
    public CourseService courseService;
    @Autowired
    public ExerciseService exerciseService;
    @Autowired
    public ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ResExerciseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int limit){
        Page<ResExerciseDto> resExerciseDtos = exerciseService.findAll(page,limit).map(exercise -> modelMapper.map(exercise,ResExerciseDto.class));
        return ResponseEntity.ok(resExerciseDtos);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody ExerciseDto exerciseDto){
        Optional<Course> courseOptional = courseService.finByTitle(exerciseDto.getCourse_title());

        if (!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise Not Found");
        }

        Course course = courseOptional.get();
        Exercise exercise = modelMapper.map(exerciseDto,Exercise.class);
        exercise.setCourse(course);
        exercise.setCourse_id(course.getId());
        exerciseService.save(exercise);

        return ResponseEntity.ok(exerciseDto);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Exercise> exerciseOptional = exerciseService.findById(id);
        if (!exerciseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        ResCourseDto resCourseDto = modelMapper.map(exerciseOptional.get(),ResCourseDto.class);
        return ResponseEntity.ok(resCourseDto);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody ExerciseDto exerciseDto){

        Optional<Exercise> exerciseOptional = exerciseService.findById(id);
        if (!exerciseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        Optional<Course> courseOptional = courseService.finByTitle(exerciseDto.getCourse_title());

        if (!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise Not Found");
        }

        Course course = courseOptional.get();
        Exercise exitExercise = exerciseOptional.get();
        exitExercise.setCourse(course);
        exitExercise.setQuestion(exitExercise.getQuestion());

        return ResponseEntity.ok(exerciseService.save(exitExercise));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Optional<Exercise> exerciseOptional = exerciseService.findById(id);

        if (!exerciseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        exerciseService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ACTION_SUCCESS);
    }
}
