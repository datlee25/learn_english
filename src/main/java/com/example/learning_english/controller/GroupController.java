package com.example.learning_english.controller;

import com.example.learning_english.dto.Group.GroupDto;
import com.example.learning_english.dto.Group.ResGroupDto;
import com.example.learning_english.dto.GroupMember.ResGroupMemberDto;
import com.example.learning_english.dto.User.ResUserDto;
import com.example.learning_english.entity.Group;
import com.example.learning_english.entity.User;
import com.example.learning_english.entity.enums.EGroupLevel;
import com.example.learning_english.service.GroupService;
import com.example.learning_english.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.learning_english.ultils.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.ultils.ExceptionMessage.NOT_FOUND;
import static com.example.learning_english.ultils.FormatDateTime.formatDateTime;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    public ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ResGroupDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int limit){

        Page<ResGroupDto> resGroupDtos = groupService.findAll(page, limit);

        return ResponseEntity.ok(resGroupDtos);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GroupDto> save(@Valid @RequestBody GroupDto groupDto){
        Group group = modelMapper.map(groupDto,Group.class);
        groupService.save(group);
        return ResponseEntity.ok(groupDto) ;
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Group> groupOptional = groupService.findById(id);
        if (!groupOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        ResGroupDto resGroupDto = modelMapper.map(groupOptional.get(),ResGroupDto.class);
        return ResponseEntity.ok(resGroupDto);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    @CrossOrigin(value = "*")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody GroupDto groupDto){
        Optional<Group> groupOptional = groupService.findById(id);
        if (!groupOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        Group exitGroup = groupOptional.get();
        exitGroup.setName(groupDto.getName()!=null?groupDto.getName():exitGroup.getName());
        exitGroup.setGroupLevel(groupDto.getGroupLevel() != null?groupDto.getGroupLevel():exitGroup.getGroupLevel());
        exitGroup.setPrice(groupDto.getPrice() != null? groupDto.getPrice():exitGroup.getPrice());
        return ResponseEntity.ok(groupService.save(exitGroup));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @CrossOrigin(value = "*")
    public ResponseEntity<?> delete(@PathVariable int id){
        Optional<Group> groupOptional = groupService.findById(id);

        if (!groupOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        groupService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ACTION_SUCCESS);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{groupId}/add_user/{userId}")
    public ResponseEntity<?> addUserToGroup(@PathVariable int groupId, @PathVariable int userId){
        Group group = groupService.findById(groupId).orElseThrow(()-> new RuntimeException("Error: Group is not found."));
        User user = userService.findById(userId).orElseThrow(()-> new RuntimeException("Error: User is not found."));

        ResGroupDto resGroupDto = modelMapper.map(groupService.addUserToGroup(group,user),ResGroupDto.class);
        return ResponseEntity.ok(resGroupDto);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/report")
    public Map<String,Integer> reportGroup(){
        List<Group> groups = groupService.getAll();
        Map<String,Integer> map = new TreeMap<>();

        for (Group group :
                groups) {
            String dateTime = formatDateTime(group.getCreateAt());
            countOccurrences(map,dateTime);
        }
        return map;
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