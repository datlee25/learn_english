package com.example.learning_english.controller;

import com.example.learning_english.SearchCriteria.Operator;
import com.example.learning_english.dto.Course.CourseDto;
import com.example.learning_english.dto.Course.GetCourseDto;
import com.example.learning_english.dto.Course.ResCourseDto;
import com.example.learning_english.dto.Exercise.ExerciseDto;
import com.example.learning_english.dto.Exercise.ResExerciseDto;
import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.User;
import com.example.learning_english.entity.UserScore;
import com.example.learning_english.entity.enums.EFieldType;
import com.example.learning_english.payload.request.search.FilterRequest;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.service.CourseService;
import com.example.learning_english.service.ExerciseService;
import com.example.learning_english.service.UserScoreService;
import com.example.learning_english.service.UserService;
import org.apache.commons.codec.binary.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private UserService userService;

    @Autowired
    private UserScoreService userScoreService;

    private String errorMessage;

    @RequestMapping(method = RequestMethod.POST,path = "/all")
    public ResponseEntity<Page<ResCourseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestBody GetCourseDto getCourseDto){
        List<Object> listGroupId = getCourseDto.getGroupId();
        listGroupId.add(0);
        List<FilterRequest> filterRequestList = new ArrayList<>();
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setKey("groupsId");
        filterRequest.setOperator(Operator.IN);
        filterRequest.setFieldType(EFieldType.INTEGER);
        filterRequest.setValues(listGroupId);

        filterRequestList.add(filterRequest);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setFilters(filterRequestList);
        searchRequest.setPage(page);
        searchRequest.setLimit(limit);
        searchRequest.setSorts(new ArrayList<>());

        List<UserScore> userScores = userScoreService.findByUserId(getCourseDto.getUserId());
        Page<ResCourseDto> resCourseDto = courseService.search(searchRequest).map(course ->{
            Map<Integer,Integer> map = new TreeMap<>();

            for (UserScore userScore: userScores){
                countOccurrences(map,userScore.getExercise().getCourse_id());
            }

            int percentageComplete = countPercent(map,course.getId(),course.getExercises().size());
            ResCourseDto result = modelMapper.map(course,ResCourseDto.class);
            result.setPercentageComplete(percentageComplete);
            result.setTitle(StringUtils.newStringUtf8(StringUtils.getBytesUtf8(result.getTitle())));
            result.setDetail(StringUtils.newStringUtf8(StringUtils.getBytesUtf8(result.getDetail())));

            return result;
        });

        return  ResponseEntity.ok(resCourseDto);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/all/pagination")
    public ResponseEntity<Page<ResCourseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
            ){
        Page<ResCourseDto> resCourseDtos = courseService.findAll(page,limit).map(course ->{
            course.setTitle(StringUtils.newStringUtf8(StringUtils.getBytesUtf8(course.getTitle())));
            course.setDetail(StringUtils.newStringUtf8(StringUtils.getBytesUtf8(course.getDetail())));
            return modelMapper.map(course,ResCourseDto.class);
        } );

        return ResponseEntity.ok(resCourseDtos);
    }
    @RequestMapping(method = RequestMethod.GET,path = "/all")
    public ResponseEntity<List<ResCourseDto>> findAllNoPage(){
        List<ResCourseDto> resCourseDtos = courseService.findAll().stream().map(course -> modelMapper.map(course,ResCourseDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok(resCourseDtos);
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
    @CrossOrigin(value = "*")
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
        exitCourse.setGroupsId(courseDto.getGroupsId());
        exitCourse.setImage(courseDto.getImage());
        return ResponseEntity.ok(courseService.save(exitCourse));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @CrossOrigin(value = "*")
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

    @RequestMapping(method = RequestMethod.GET, path = "/{course_id}/exercise")
    public ResponseEntity<?> findExercisesOfCourse(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int limit,
                                                   @PathVariable int course_id){
        courseService.findById(course_id).orElseThrow(()->new RuntimeException("Course not found!"));
        Page<ResExerciseDto> resExerciseDtos = exerciseService.findExercisesByCourseId(page,limit,course_id).map(exercise->modelMapper.map(exercise, ResExerciseDto.class));
        return ResponseEntity.ok(resExerciseDtos);
    }

    public static void countOccurrences(Map<Integer, Integer> map, int course_id){
        if (map.containsKey(course_id)){
            int count = map.get(course_id)+1;
            map.put(course_id,count);
        }else{
            map.put(course_id,1);
        }
    }

    public static int countPercent(Map<Integer, Integer> map, int course_id, int exercise_length){
        if (map.containsKey(course_id)){
            return map.get(course_id)*100/exercise_length;
        }
        return 0;
    }
}