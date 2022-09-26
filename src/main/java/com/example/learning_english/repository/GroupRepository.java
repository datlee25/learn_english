package com.example.learning_english.repository;

import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.Exercise;
import com.example.learning_english.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer>, JpaSpecificationExecutor<Course> {
    Page<Group> findAll(Pageable pageable);
    List<Group> findAll();
}