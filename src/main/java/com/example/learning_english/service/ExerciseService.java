package com.example.learning_english.service;

import com.example.learning_english.dto.Exercise.ExerciseDto;
import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.Exercise;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.repository.ExerciseRepository;
import com.example.learning_english.specifications.SearchSpecification;
import org.modelmapper.ModelMapper;
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
    @Autowired
    public ModelMapper modelMapper;
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
    public List<Exercise> findExercisesByCourseId(int id){
        return exerciseRepository.findExercisesByCourse_id(id);
    }

    public Exercise save(Course course, ExerciseDto exerciseDto){
        Exercise exercise = modelMapper.map(exerciseDto,Exercise.class);
        exercise.setCourse(course);
        return exerciseRepository.save(exercise);
    }

    public Exercise update(Exercise exercise){
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
    public Page<Exercise> search(SearchRequest searchRequest){
        SearchSpecification<Exercise> specification = new SearchSpecification<>(searchRequest);
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage(), searchRequest.getLimit());
        return exerciseRepository.findAll(specification,pageRequest);
    }
}
