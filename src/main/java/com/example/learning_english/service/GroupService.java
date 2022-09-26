package com.example.learning_english.service;

import com.example.learning_english.dto.Group.ReportGroupDto;
import com.example.learning_english.dto.Group.ResGroupDto;
import com.example.learning_english.dto.GroupMember.ResGroupMemberDto;
import com.example.learning_english.dto.User.ResUserDto;
import com.example.learning_english.dto.UserScore.ResScoreBoard;
import com.example.learning_english.dto.UserScore.ResUserScoreDto;
import com.example.learning_english.entity.*;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.repository.GroupRepository;
import com.example.learning_english.specifications.SearchSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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

                //TODO:Format LocalDateTime to dd/mm/yyyy
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

    public List<Group> getAll(){
        return groupRepository.findAll();
    }
    public Optional<Group> findById(int id) {
        return groupRepository.findById(id);
    }

    public void delete(int id) {
        groupRepository.deleteById(id);
    }

    public Group save(Group data) {
        data.setCreateAt(LocalDateTime.now());
        data.setUpdateAt(LocalDateTime.now());
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
    public List<ReportGroupDto> report() {
        List<ReportGroupDto> groupList = groupRepository.findAll().stream().map(group -> {
            ReportGroupDto reportGroupDto = new ReportGroupDto();
            group.getGroupMembers().forEach(groupMember -> {
                reportGroupDto.setName(groupMember.getUser().getUsername());
                reportGroupDto.setGroupLevel(group.getGroupLevel());
                reportGroupDto.setPrice(group.getPrice());
            });
            return reportGroupDto;
        }).collect(Collectors.toList());
        return groupList;
    }
    public Page<Course> search(SearchRequest searchRequest){
        SearchSpecification<Course> specification = new SearchSpecification<>(searchRequest);
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage(), searchRequest.getLimit());
        return groupRepository.findAll(specification, pageRequest);
    }

    private static ResScoreBoard convertTreeMapToModel(Map<String, BigDecimal> top10User, Map<String,BigDecimal> currentUser, int currentUserRank){
        List<ResUserScoreDto> listTop10UserScore = new ArrayList<>();
        int rank =0;
        for (Map.Entry<String, BigDecimal> userInTop10 :
                top10User.entrySet()
        ){
            rank++;
            listTop10UserScore.add(new ResUserScoreDto(userInTop10.getKey(),userInTop10.getValue(),rank));
        }

        ResUserScoreDto resCurrentUserScore = new ResUserScoreDto(currentUser.keySet().iterator().next(),currentUser.values().iterator().next(),currentUserRank );
        return new ResScoreBoard(listTop10UserScore,resCurrentUserScore);
    }

    public static Map<String,BigDecimal> countOccurrences(List<UserScore> userScores){
        Map<String,BigDecimal> map = new TreeMap<>();
        for (UserScore userScore :
                userScores) {
            String name =userScore.getUser().getUsername();
            BigDecimal score =userScore.getScore();
            if (map.containsKey(name)){
                BigDecimal totalScore = map.get(name).add(score);
                map.put(name,totalScore);
            }else{
                map.put(name,score);
            }
        }
        return map;

    }

}