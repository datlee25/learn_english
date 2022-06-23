package com.example.learning_english.controller;

import com.example.learning_english.dto.CourseDto;
import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.search.SearchRequest;
import com.example.learning_english.exception.CourseNotFoundException;
import com.example.learning_english.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static com.example.learning_english.util.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.util.ExceptionMessage.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {

    @Autowired
    public ModelMapper modelMapper;
    @Autowired
    public CourseService courseService;
    private String errorMessage;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<CourseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit){
        Page<CourseDto> courseDto =  courseService.findAll(page, limit).map(course -> modelMapper.map(course,CourseDto.class));
        return  ResponseEntity.ok(courseDto);
    }

    @RequestMapping(method = RequestMethod.GET, path="/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Course> courseOptional = courseService.findById(id);
        if (!courseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        CourseDto courseDto = modelMapper.map(courseOptional.get(),CourseDto.class);
        return ResponseEntity.ok(courseDto);
//        return ResponseEntity.ok(courseService.findById(id).orElseThrow(CourseNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CourseDto> save(@Valid @RequestBody CourseDto courseDto){
        Course course = new Course(courseDto);
        Course res = courseService.save(course);
        CourseDto courseResponse = modelMapper.map(res, CourseDto.class);
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

        return ResponseEntity.ok(courseService.save(exitCourse));
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

}
