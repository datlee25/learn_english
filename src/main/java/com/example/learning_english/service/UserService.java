package com.example.learning_english.service;

import com.example.learning_english.dto.Group.ResGroupByUserIdDto;
import com.example.learning_english.entity.GroupMember;
import com.example.learning_english.entity.Role;
import com.example.learning_english.entity.User;
import com.example.learning_english.entity.enums.ERole;
import com.example.learning_english.payload.request.SignupRequest;
import com.example.learning_english.repository.GroupMemberRepository;
import com.example.learning_english.repository.GroupRepository;
import com.example.learning_english.repository.RoleRepository;
import com.example.learning_english.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

import static com.example.learning_english.ultils.FormatDateTime.formatDateTime;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public GroupRepository groupRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public GroupMemberRepository groupMemberRepository;

    public Page<User> getAll(int page, int limit){
        Pageable pageable = PageRequest.of(page,limit);
        return userRepository.findAll(pageable);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public User register(SignupRequest signupRequest) {

        String password = "";

        // Create new user's account
        if (signupRequest.getPassword() != null){
            password = signupRequest.getPassword();
        }


        User user = new User(signupRequest.getUsername(),
                passwordEncoder.encode(password),
                signupRequest.getEmail(),false);

        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        //check user role
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }


        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }
    public List<ResGroupByUserIdDto> findGroupByUserId(int id){
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMembersByUserId(id);
        List<ResGroupByUserIdDto> groups = new ArrayList<>();
        for (GroupMember groupMember :
                groupMembers) {
            //TODO: Get group via groupMember and convert to ResGroupByUserIdDto
            ResGroupByUserIdDto resGroupByUserIdDto = modelMapper.map(groupMember.getGroup(), ResGroupByUserIdDto.class);
            resGroupByUserIdDto.setCreateAt(formatDateTime(groupMember.getCreateAt()));
            resGroupByUserIdDto.setUpdateAt(formatDateTime(groupMember.getUpdateAt()));
            groups.add(resGroupByUserIdDto);
        }

        return groups;
    }

    public boolean verificationUserEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean patternMatches(String email, String regexPattern){
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
}