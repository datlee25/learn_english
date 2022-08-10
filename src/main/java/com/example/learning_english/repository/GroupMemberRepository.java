package com.example.learning_english.repository;

import com.example.learning_english.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember,Integer> {
    List<GroupMember> findGroupMembersByUserId(int id);
}
