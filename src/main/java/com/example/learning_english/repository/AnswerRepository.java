package com.example.learning_english.repository;

import com.example.learning_english.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Page<Answer> findAll(Pageable pageable);
    Page<Answer> findAllByQuestion_id(Pageable pageable,int id);
}
