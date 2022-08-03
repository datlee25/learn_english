package com.example.learning_english.controller;

import com.example.learning_english.dto.Group.GroupDto;
import com.example.learning_english.dto.Group.ResGroupDto;
import com.example.learning_english.entity.Group;
import com.example.learning_english.entity.User;
import com.example.learning_english.service.GroupService;
import com.example.learning_english.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

import static com.example.learning_english.util.ExceptionMessage.ACTION_SUCCESS;
import static com.example.learning_english.util.ExceptionMessage.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    private ModelMapper modelMapper;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ResGroupDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int limit){
        Page<ResGroupDto> resGroupDtos =  groupService.findAll(page, limit).map(group -> modelMapper.map(group,ResGroupDto.class));

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
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody GroupDto groupDto){
        Optional<Group> groupOptional = groupService.findById(id);
        if (!groupOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }

        Group exitGroup = groupOptional.get();
        exitGroup.setName(groupDto.getName());
        exitGroup.setGroupLevel(groupDto.getGroupLevel());

        return ResponseEntity.ok(groupService.save(exitGroup));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
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

        //check độ tuổi.
        ResGroupDto resGroupDto = modelMapper.map(groupService.addUserToGroup(group,user),ResGroupDto.class);
        return ResponseEntity.ok(resGroupDto);
    }
}
