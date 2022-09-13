package com.example.learning_english.repository;

import com.example.learning_english.entity.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer>, JpaSpecificationExecutor<Exercise> {
    Page<Exercise> findAll(Pageable pageable);
    Page<Exercise> findExercisesByCourse_id(Pageable pageable,int id);
}
