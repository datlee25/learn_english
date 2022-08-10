package com.example.learning_english.controller;

import com.example.learning_english.dto.Group.ResGroupByUserIdDto;
import com.example.learning_english.dto.User.ResUserDto;
import com.example.learning_english.entity.User;
import com.example.learning_english.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.example.learning_english.ultils.FormatDateTime.formatDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    public ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ResUserDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int limit){
        Page<ResUserDto> userDtos = userService.getAll(page,limit).map(user -> modelMapper.map(user,ResUserDto.class));
        return ResponseEntity.ok(userDtos);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/report")
    public Map<String,Integer> reportUser(){
        List<User> users = userService.getAll();
        Map<String,Integer> map = new TreeMap<>();

        for (User user :
                users) {
            String dateTime = formatDateTime(user.getCreateAt());
            countOccurrences(map,dateTime);
        }
        return map;
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{id}/groups")
    public ResponseEntity<List<ResGroupByUserIdDto>> getGroupByUserId(@PathVariable int id){
        userService.findById(id).orElseThrow(()-> new RuntimeException("User not found!"));
        return ResponseEntity.ok( userService.findGroupByUserId(id));
    }

    public static void countOccurrences(Map<String, Integer> map, String dateTime){
        if (map.containsKey(dateTime)){
            int count = map.get(dateTime)+1;
            map.put(dateTime,count);
        }else{
            map.put(dateTime,1);
        }
    }
}
