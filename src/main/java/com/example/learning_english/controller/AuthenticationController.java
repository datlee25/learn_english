package com.example.learning_english.controller;

import com.example.learning_english.dto.AccountDto;
import com.example.learning_english.dto.RegisterDto;
import com.example.learning_english.security.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserDetailsServiceImpl authenticationService ;

    @RequestMapping(path ="register",method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto){
        AccountDto accountDto = authenticationService.saveAccount(registerDto);
        return ResponseEntity.ok().body(accountDto);
    }

}
