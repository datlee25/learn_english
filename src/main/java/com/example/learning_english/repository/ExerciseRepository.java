package com.example.learning_english.repository;

import com.example.learning_english.entity.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Page<Exercise> findAll(Pageable pageable);
    Optional<Exercise> findByQuestion(String question);
}
