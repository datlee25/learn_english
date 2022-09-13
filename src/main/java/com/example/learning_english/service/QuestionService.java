package com.example.learning_english.service;

import com.example.learning_english.entity.Exercise;
import com.example.learning_english.entity.Question;
import com.example.learning_english.repository.ExerciseRepository;
import com.example.learning_english.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    public QuestionRepository questionRepository;

    public List<Question> findAll(){
        return questionRepository.findAll();
    }

    public Page<Question> findAll(int page, int limit){
        PageRequest pageRequest = PageRequest.of(page, limit);
        return questionRepository.findAll(pageRequest);
    }
    public Page<Question> findQuestionByExerciseId(int page, int limit,int id){
        PageRequest pageRequest = PageRequest.of(page, limit);

        return questionRepository.findAllByExercise_idOrderByAnswersAsc(pageRequest,id);
    }

    public Optional<Question> findById(int id){
        return questionRepository.findById(id);
    }

    public Question save(Question question){
        return questionRepository.save(question);
    }

    public List<Question> saveAll(List<Question> questions){
        return questionRepository.saveAll(questions);
    }

    public void delete(int id){
        questionRepository.deleteById(id);
    }

    public void deleteAll(){
        questionRepository.deleteAll();
    }
}
