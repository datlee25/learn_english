package com.example.learning_english.security.services;

import com.example.learning_english.entity.RefreshToken;
import com.example.learning_english.entity.User;
import com.example.learning_english.exception.TokenRefreshException;
import com.example.learning_english.repository.RefreshTokenRepository;
import com.example.learning_english.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${english.app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plusMillis(jwtRefreshExpirationMs);
        RefreshToken refreshToken = new RefreshToken(user, token, expiryDate);
        System.out.println("refreshToken la: " + refreshToken);
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){

        if (token.getExpiryDate().compareTo(Instant.now())<0){

            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()){
            return 0;
        }
        User user = userOptional.get();
        return refreshTokenRepository.deleteByUser(user);
    }
}
