package com.example.learning_english.controller;

import com.example.learning_english.dto.Course.CourseDto;
import com.example.learning_english.dto.Course.ResCourseDto;
import com.example.learning_english.dto.Exercise.ExerciseDto;
import com.example.learning_english.dto.Exercise.ResExerciseDto;
import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.Exercise;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.service.CourseService;
import com.example.learning_english.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static com.example.learning_english.ultils.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.ultils.ExceptionMessage.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {

    @Autowired
    public ModelMapper modelMapper;
    @Autowired
    public CourseService courseService;

    @Autowired
    private ExerciseService exerciseService;

    private String errorMessage;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ResCourseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit){
        Page<ResCourseDto> resCourseDto =  courseService.findAll(page, limit).map(course -> modelMapper.map(course,ResCourseDto.class));
        return  ResponseEntity.ok(resCourseDto);
    }

    @RequestMapping(method = RequestMethod.GET, path="/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Course> courseOptional = courseService.findById(id);
        if (!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        ResCourseDto resCourseDto = modelMapper.map(courseOptional.get(),ResCourseDto.class);
        return ResponseEntity.ok(resCourseDto);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResCourseDto> save(@Valid @RequestBody CourseDto courseDto){

        Course course = modelMapper.map(courseDto,Course.class);
        Course res = courseService.save(course);
        ResCourseDto courseResponse = modelMapper.map(res, ResCourseDto.class);
        return ResponseEntity.ok(courseResponse);
    }

    @RequestMapping(method = RequestMethod.PUT, path="/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody CourseDto courseDto){
        Optional<Course> courseOptional = courseService.findById(id);

        if (!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        Course exitCourse = courseOptional.get();
        exitCourse.setTitle(courseDto.getTitle());
        exitCourse.setDetail(courseDto.getDetail());
        exitCourse.setQualification(courseDto.getQualification());
        exitCourse.setParticipantAge(courseDto.getParticipantAge());
        exitCourse.setNumber_of_participants(courseDto.getNumber_of_participants());

        return ResponseEntity.ok(courseDto);
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Optional<Course> courseOptional = courseService.findById(id);
        if (!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        courseService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ACTION_SUCCESS);

    }

    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public Page<Course> search (@RequestBody SearchRequest searchRequest){
        return courseService.search(searchRequest);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/new/exercise")
    public ResponseEntity<?> addExerciseToCourse( @RequestBody ExerciseDto exerciseDto){
        Course course = courseService.findById(exerciseDto.getCourse_id()).orElseThrow(()-> new RuntimeException("Course Not Found!"));
        return ResponseEntity.ok(exerciseService.save(course,exerciseDto));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{course_id}/exercise/{exercise_id}")
    public ResponseEntity<?> findExerciseOfCourse(@PathVariable int course_id, @PathVariable int exercise_id){
        courseService.findById(course_id).orElseThrow(()->new RuntimeException("Course not found!"));
        Exercise exercise = exerciseService.findById(exercise_id).orElseThrow(()->new RuntimeException("Exercise not found!"));

        return ResponseEntity.ok(modelMapper.map(exercise, ResExerciseDto.class));
    }
}