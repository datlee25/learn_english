package com.example.learning_english.service;

import com.example.learning_english.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupService {
    @Autowired
    public GroupRepository groupRepository;
}
