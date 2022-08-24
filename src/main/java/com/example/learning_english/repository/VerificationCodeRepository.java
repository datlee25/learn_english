package com.example.learning_english.repository;

import com.example.learning_english.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Integer> {
    VerificationCode findByCode(String code);
}
