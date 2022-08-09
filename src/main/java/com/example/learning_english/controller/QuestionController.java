package com.example.learning_english.controller;

import com.example.learning_english.entity.Question;
import com.example.learning_english.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
