package com.example.learning_english.repository;

import com.example.learning_english.entity.RefreshToken;
import com.example.learning_english.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findById(Integer id);
    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);
}
