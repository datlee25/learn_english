package com.example.learning_english.controller;

import com.example.learning_english.dto.Answer.ResAnswerDto;
import com.example.learning_english.dto.Exercise.ResExerciseDto;
import com.example.learning_english.dto.QuestionDto.QuestionDto;
import com.example.learning_english.entity.Exercise;
import com.example.learning_english.entity.Question;
import com.example.learning_english.entity.User;
import com.example.learning_english.repository.QuestionRepository;
import com.example.learning_english.service.AnswerService;
import com.example.learning_english.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.learning_english.ultils.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.ultils.ExceptionMessage.NOT_FOUND;

@RestController
@RequestMapping(path = "/api/v1/question")
public class QuestionController {

    @Autowired
    public QuestionService questionService;
    @Autowired
    public AnswerService answerService;
    @Autowired
    public ModelMapper modelMapper;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Question>> findAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int limit){
        return ResponseEntity.ok(questionService.findAll(page,limit));
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Question exercise = questionService.findById(id).orElseThrow(()->new RuntimeException("Question Not Found!"));

        QuestionDto resExerciseDto = modelMapper.map(exercise,QuestionDto.class);
        return ResponseEntity.ok(resExerciseDto);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    @CrossOrigin(value = "*")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody QuestionDto questionDto){
        Question question = questionService.findById(id).orElseThrow(()-> new RuntimeException("Question not found!"));
        question.setQuestion(questionDto.getQuestion());
        return ResponseEntity.ok(questionService.save(question));
    }
    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @CrossOrigin(value = "*")
    public ResponseEntity<?> delete(@PathVariable int id){
        Optional<Question> questionOptional = questionService.findById(id);

        if (!questionOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        questionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ACTION_SUCCESS);

    }
    @RequestMapping(method = RequestMethod.GET,path = "/{question_id}/answer")
    public ResponseEntity<?> findExercisesOfCourse(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int limit,
                                                   @PathVariable int question_id) {
        questionService.findById(question_id).orElseThrow(() -> new RuntimeException("Question not found!"));
        Page<ResAnswerDto> resAnswerDtos = answerService.findAnswerByQuestionId(page, limit, question_id).map(exercise -> modelMapper.map(exercise, ResAnswerDto.class));
        return ResponseEntity.ok(resAnswerDtos);
    }
}
