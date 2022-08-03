package com.example.learning_english.service;

import com.example.learning_english.entity.Group;
import com.example.learning_english.entity.User;
import com.example.learning_english.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class GroupService implements BaseService<Group> {
    @Autowired
    public GroupRepository groupRepository;


    @Override
    public Page<Group> findAll(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return groupRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Group> findById(int id) {
        return groupRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        groupRepository.deleteById(id);
    }

    @Override
    public Group save(Group data) {
        return groupRepository.save(data);
    }

    public Group addUserToGroup(Group group, User user){
        Set<User> users = group.getUsers();
        users.add(user);
        group.setUsers(users);
        return groupRepository.save(group);
    }
}