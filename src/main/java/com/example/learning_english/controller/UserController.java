package com.example.learning_english.controller;

import com.example.learning_english.entity.User;
import com.example.learning_english.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloUser () {
        return "hello account with role user";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<User>> getAll(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int limit){
        return ResponseEntity.ok(userService.getAll(page,limit));
    }
}
