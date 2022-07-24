package com.example.learning_english.service;

import com.example.learning_english.entity.Role;
import com.example.learning_english.entity.User;
import com.example.learning_english.entity.enums.ERole;
import com.example.learning_english.payload.request.SignupRequest;
import com.example.learning_english.repository.RoleRepository;
import com.example.learning_english.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;

    public Page<User> getAll(int page, int limit){
        Pageable pageable = PageRequest.of(page,limit);
        return userRepository.findAll(pageable);
    }
    public User saveUser(SignupRequest signupRequest){
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return null;
        }

        String password = "";
        // Create new user's account
        if (signupRequest.getPassword() != null){
            password = signupRequest.getPassword();

        }
        User user = new User(signupRequest.getUsername(),
                passwordEncoder.encode(password),
                signupRequest.getEmail());
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());



        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        //check user role
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
}
