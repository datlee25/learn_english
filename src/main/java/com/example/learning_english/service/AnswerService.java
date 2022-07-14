package com.example.learning_english.service;

import com.example.learning_english.entity.Answer;
import com.example.learning_english.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    public AnswerRepository answerRepository;

    public Page<Answer> findAll(int page, int limit){
        PageRequest pageRequest = PageRequest.of(page, limit);
        return answerRepository.findAll(pageRequest);
    }

    public Optional<Answer> findById(int id){
        return answerRepository.findById(id);
    }

    public Answer save(Answer answer){
        return answerRepository.save(answer);
    }
    public List<Answer> saveAll(List<Answer> answerList){
        return answerRepository.saveAll(answerList);
    }

    public void delete(int id){
        answerRepository.deleteById(id);
    }
    public void deleteAll(){
        answerRepository.deleteAll();
    }
}
