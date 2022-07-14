package com.example.learning_english.service;

import com.example.learning_english.entity.Exercise;
import com.example.learning_english.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    public ExerciseRepository exerciseRepository;

    public List<Exercise> findAll(){
        return exerciseRepository.findAll();
    }

    public Page<Exercise> findAll(int page, int limit){
        PageRequest pageRequest = PageRequest.of(page, limit);
        return exerciseRepository.findAll(pageRequest);
    }

    public Optional<Exercise> findById(int id){
        return exerciseRepository.findById(id);
    }
    public Optional<Exercise> findByQuestion(String question){
        return exerciseRepository.findByQuestion(question);
    }

    public Exercise save(Exercise exercise){
        return exerciseRepository.save(exercise);
    }

    public List<Exercise> saveAll(List<Exercise> exerciseList){
        return exerciseRepository.saveAll(exerciseList);
    }

    public void delete(int id){
        exerciseRepository.deleteById(id);
    }

    public void deleteAll(){
        exerciseRepository.deleteAll();
    }
}
