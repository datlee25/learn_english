package com.example.learning_english.controller;

import com.example.learning_english.entity.RefreshToken;
import com.example.learning_english.entity.User;
import com.example.learning_english.exception.TokenRefreshException;
import com.example.learning_english.payload.request.LoginRequest;
import com.example.learning_english.payload.request.SignupRequest;
import com.example.learning_english.payload.request.TokenRefreshRequest;
import com.example.learning_english.payload.response.JwtResponse;
import com.example.learning_english.payload.response.TokenRefreshResponse;
import com.example.learning_english.security.jwt.JwtUtils;
import com.example.learning_english.security.services.RefreshTokenService;
import com.example.learning_english.security.services.UserDetailsImpl;
import com.example.learning_english.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails.getEmail(),userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                refreshToken.getToken(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @RequestMapping(path ="register",method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest) {
        User user = userService.saveUser(signupRequest);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "/token/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request){

        String refresh_token = request.getRefreshToken();
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(refresh_token);
        if (refreshToken.isPresent()){
            System.out.println("in ra refresh_token: " + refreshToken.get());

        }else{
            System.out.println("khong co refreshToken: ");
        }
        return refreshTokenService.findByToken(refresh_token)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtils.generateJwtToken(user.getEmail(),user.getUsername());
                    return ResponseEntity.ok(
                        new TokenRefreshResponse(accessToken, refresh_token)
                    );
                }).orElseThrow(() -> new TokenRefreshException(refresh_token,
                        "Refresh token is not in database!"));
    }
}
