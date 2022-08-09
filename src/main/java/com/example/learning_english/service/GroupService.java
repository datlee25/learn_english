package com.example.learning_english.service;

import com.example.learning_english.dto.Group.ResGroupDto;
import com.example.learning_english.dto.GroupMember.ResGroupMemberDto;
import com.example.learning_english.dto.User.ResUserDto;
import com.example.learning_english.entity.Group;
import com.example.learning_english.entity.GroupMember;
import com.example.learning_english.entity.GroupMemberId;
import com.example.learning_english.entity.User;
import com.example.learning_english.repository.GroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.learning_english.ultils.FormatDateTime.formatDateTime;

@Service
public class GroupService {
    @Autowired
    public GroupRepository groupRepository;
    @Autowired
    public ModelMapper modelMapper;

    public Page<ResGroupDto> findAll(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return groupRepository.findAll(pageRequest).map(group -> {
            Set<ResGroupMemberDto> resGroupMemberDtos = group.getGroupMembers().stream().map(groupMember -> {
                //TODO: Convert to ResUserDto
                ResUserDto resUserDto = modelMapper.map(groupMember.getUser(),ResUserDto.class);

                //TODO: Convert GroupMember class to ResGroupMemberDto
                ResGroupMemberDto resGroupMemberDto = modelMapper.map(groupMember,ResGroupMemberDto.class);

                //TODO:Format LocalDateTime to dd/mm//yyyy
                resGroupMemberDto.setCreateAt(formatDateTime(groupMember.getCreateAt()));
                resGroupMemberDto.setUpdateAt(formatDateTime(groupMember.getUpdateAt()));

                //TODO: Set User to ResGroupMemberDto
                resGroupMemberDto.setUser(resUserDto);
                return resGroupMemberDto;
            }).collect(Collectors.toSet());

            //TODO: Convert Group class to ResGroupDto class
            ResGroupDto resGroupDto = modelMapper.map(group, ResGroupDto.class);
            resGroupDto.setGroupMembers(resGroupMemberDtos);
            return resGroupDto;
        });
    }

    public Optional<Group> findById(int id) {
        return groupRepository.findById(id);
    }

    public void delete(int id) {
        groupRepository.deleteById(id);
    }

    public Group save(Group data) {
        return groupRepository.save(data);
    }

    public Group addUserToGroup(Group group, User user){
        Set<GroupMember> groupMembers = group.getGroupMembers();
        LocalDateTime dateTime = LocalDateTime.now();
        GroupMemberId groupMemberId = new GroupMemberId(group.getId(),user.getId());
        GroupMember groupMember = new GroupMember(groupMemberId,group,user,dateTime,dateTime);
        groupMembers.add(groupMember);
        group.setGroupMembers(groupMembers);
        return groupRepository.save(group);
    }
}