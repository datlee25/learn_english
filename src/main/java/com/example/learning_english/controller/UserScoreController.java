package com.example.learning_english.controller;

import com.example.learning_english.dto.UserScore.ResScoreBoard;
import com.example.learning_english.dto.UserScore.ResUserScoreDto;
import com.example.learning_english.dto.UserScore.UserScoreDto;
import com.example.learning_english.entity.*;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.service.ExerciseService;
import com.example.learning_english.service.UserScoreService;
import com.example.learning_english.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.learning_english.ultils.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.ultils.ExceptionMessage.NOT_FOUND;

@RestController
@RequestMapping(path = "/api/v1/user_score")
public class UserScoreController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserScoreService userScoreService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseService exerciseService;



    @RequestMapping(method = RequestMethod.GET,path = "/{id}")
    public ResponseEntity<ResScoreBoard> findAll(@PathVariable int id){
        return  ResponseEntity.ok(userScoreService.findTop10ByOrderByScoreDesc(id));
    }

//    @RequestMapping(method = RequestMethod.GET, path="/{id}")
//    public ResponseEntity<?> findById(@PathVariable int id){
//        UserScore userScore = userScoreService.findById(id).orElseThrow(()-> new RuntimeException("User not found!"));;
//        return ResponseEntity.ok(userScore);
//    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResUserScoreDto> save(@Valid @RequestBody UserScoreDto userScoreDto){
        User user = userService.findById(userScoreDto.getUserId()).orElseThrow(()-> new RuntimeException("User not found!"));
        Exercise exercise = exerciseService.findById(userScoreDto.getExerciseId()).orElseThrow(()-> new RuntimeException("Exercise not found!"));

        //TODO: CREATE composite key for userscore
        UserScoreId userScoreId = new UserScoreId(user.getId(),exercise.getId());
        UserScore userScore = new UserScore(userScoreId,userScoreDto.getName(),userScoreDto.getScore(),user,exercise);

        UserScore res = userScoreService.save(userScore);

        return ResponseEntity.ok(modelMapper.map(res, ResUserScoreDto.class));
    }

    @RequestMapping(method = RequestMethod.PUT, path="/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody UserScoreDto userScoreDto){
        UserScore userScore = userScoreService.findById(id).orElseThrow(()-> new RuntimeException("User score not found!"));
        User user = userService.findById(userScoreDto.getUserId()).orElseThrow(()-> new RuntimeException("User not found!"));
        Exercise exercise = exerciseService.findById(userScoreDto.getExerciseId()).orElseThrow(()-> new RuntimeException("Exercise not found!"));


        userScore.setUpdateAt(LocalDateTime.now());
        userScore.setScore(userScoreDto.getScore());
        userScore.setName(userScoreDto.getName());
        userScore.setUser(user);
        userScore.setExercise(exercise);

        return ResponseEntity.ok(userScoreService.save(userScore));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Optional<UserScore> userScoreOptional = userScoreService.findById(id);

        if (!userScoreOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        userScoreService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ACTION_SUCCESS);

    }
    @RequestMapping(method = RequestMethod.POST,path = "/search")
    public Page<UserScore> search (@RequestBody SearchRequest searchRequest){
        return userScoreService.search(searchRequest);
    }
}