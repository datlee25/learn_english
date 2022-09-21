package com.example.learning_english.controller;

import com.example.learning_english.dto.QuestionDto.QuestionDto;
import com.example.learning_english.entity.Question;
import com.example.learning_english.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/question")
public class QuestionController {

    @Autowired
    public QuestionService questionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Question>> findAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int limit){
        return ResponseEntity.ok(questionService.findAll(page,limit));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    @CrossOrigin(value = "*")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody QuestionDto questionDto){
        Question question = questionService.findById(id).orElseThrow(()-> new RuntimeException("Question not found!"));
        question.setQuestion(questionDto.getQuestion());
        return ResponseEntity.ok(questionService.save(question));
    }
}
