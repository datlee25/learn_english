package com.example.learning_english.repository;

import com.example.learning_english.entity.Exercise;
import com.example.learning_english.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    Page<Group> findAll(Pageable pageable);

}
