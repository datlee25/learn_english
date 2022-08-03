package com.example.learning_english.controller;

import com.example.learning_english.dto.Answer.AnswerDto;
import com.example.learning_english.dto.Answer.ResAnswerDto;
import com.example.learning_english.dto.Course.ResCourseDto;
import com.example.learning_english.entity.Answer;
import com.example.learning_english.entity.Exercise;
import com.example.learning_english.service.AnswerService;
import com.example.learning_english.service.ExerciseService;
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
@RequestMapping(path = "api/v1/answers")
public class AnswerController {
    @Autowired
    public AnswerService answerService;
    @Autowired
    public ExerciseService exerciseService;
    @Autowired
    public ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ResAnswerDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int limit){
        Page<ResAnswerDto> resAnswerDtos = answerService.findAll(page,limit).map(answer -> modelMapper.map(answer, ResAnswerDto.class));
        return ResponseEntity.ok(resAnswerDtos);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody AnswerDto answerDto){
        Optional<Exercise> exerciseOptional = exerciseService.findByQuestion(answerDto.getExercise_question());

        if (!exerciseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise Not Found");
        }
        Exercise exercise = exerciseOptional.get();

        Answer answer = modelMapper.map(answerDto,Answer.class);
        answer.setExercise(exercise);
        answerService.save(answer);

        return ResponseEntity.ok(answerDto);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Answer> answerOptional = answerService.findById(id);
        if (!answerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        ResAnswerDto resAnswerDto = modelMapper.map(answerOptional.get(),ResAnswerDto.class);
        return ResponseEntity.ok(resAnswerDto);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody AnswerDto answerDto){

        Optional<Answer> answerOptional = answerService.findById(id);
        if (!answerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        Optional<Exercise> exerciseOptional = exerciseService.findByQuestion(answerDto.getExercise_question());

        if (!exerciseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise Not Found");
        }

        Exercise exercise = exerciseOptional.get();
        Answer exitAnswer = answerOptional.get();
        exitAnswer.setAnswer_key(answerDto.getAnswer_key());
        exitAnswer.setAnswer_value(answerDto.getAnswer_value());
        exitAnswer.setExercise(exercise);

        return ResponseEntity.ok(answerService.save(exitAnswer));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Optional<Answer> answerOptional = answerService.findById(id);

        if (!answerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        answerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ACTION_SUCCESS);
    }
}