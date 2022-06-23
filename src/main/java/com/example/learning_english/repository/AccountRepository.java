package com.example.learning_english.repository;

import com.example.learning_english.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByEmail(String email);
    Boolean existsByEmail(String email);
}
