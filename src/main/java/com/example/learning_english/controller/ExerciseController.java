package com.example.learning_english.controller;

import com.example.learning_english.dto.Exercise.ExerciseDto;
import com.example.learning_english.dto.Exercise.ResExerciseDto;
import com.example.learning_english.dto.QuestionDto.QuestionDto;
import com.example.learning_english.entity.Answer;
import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.Exercise;
import com.example.learning_english.entity.Question;
import com.example.learning_english.service.AnswerService;
import com.example.learning_english.service.ExerciseService;
import com.example.learning_english.service.CourseService;
import com.example.learning_english.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.learning_english.ultils.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.ultils.ExceptionMessage.NOT_FOUND;

@RestController
@RequestMapping(path = "api/v1/exercise")
public class ExerciseController {
    @Autowired
    public CourseService courseService;
    @Autowired
    public ExerciseService exerciseService;

    @Autowired
    public QuestionService questionService;

    @Autowired
    public AnswerService answerService;
    @Autowired
    public ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ResExerciseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int limit){
        Page<ResExerciseDto> resExerciseDtos = exerciseService.findAll(page,limit).map(exercise -> modelMapper.map(exercise,ResExerciseDto.class));
        return ResponseEntity.ok(resExerciseDtos);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Exercise exercise = exerciseService.findById(id).orElseThrow(()->new RuntimeException("Exercise Not Found!"));

        ResExerciseDto resExerciseDto = modelMapper.map(exercise,ResExerciseDto.class);
        return ResponseEntity.ok(resExerciseDto);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody ExerciseDto exerciseDto){

        Optional<Exercise> exerciseOptional = exerciseService.findById(id);
        if (!exerciseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        Course course = courseService.findById(exerciseDto.getCourse_id()).orElseThrow(()->new RuntimeException("Exercise Not Found"));

        Exercise exercise = exerciseOptional.get();

        exercise.setCourse_id(exerciseDto.getCourse_id());
        exercise.setName(exerciseDto.getName());
        exercise.setCourse(course);
        exercise.setDescription(exerciseDto.getDescription());

         return ResponseEntity.ok(exerciseService.update(exercise));
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

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/question")
    public ResponseEntity<?> addQuestionToExercise(@PathVariable int id, @RequestBody QuestionDto questionDto){

        Exercise exercise = exerciseService.findById(id).orElseThrow(()->new RuntimeException("Exercise not found!"));
        Question question = new Question(questionDto.getQuestion(), exercise.getId(),exercise);
        Question res = questionService.save(question);

        Set<Answer> answerSet = new HashSet<>();
        for (Answer answer :
                questionDto.getAnswers()) {
            answer.setQuestion(res);
            answer.setQuestion_id(res.getId());
            answerSet.add(answer);
        }

        answerService.saveAll(answerSet);
        return ResponseEntity.ok(answerSet);
    }
}