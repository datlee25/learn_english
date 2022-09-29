package com.example.learning_english.service;

import com.example.learning_english.dto.Group.ResGroupByUserIdDto;
import com.example.learning_english.entity.*;
import com.example.learning_english.entity.enums.ERole;
import com.example.learning_english.payload.request.SignupRequest;
import com.example.learning_english.payload.request.search.SearchRequest;
import com.example.learning_english.repository.*;
import com.example.learning_english.specifications.SearchSpecification;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

import static com.example.learning_english.ultils.FormatDateTime.formatDateTime;
import static com.example.learning_english.ultils.RandomVerificationCode.randomVerificationCode;

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
    private VerificationCodeRepository verifiCodeRepository;
    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public GroupMemberRepository groupMemberRepository;

    @Autowired
    private EmailService emailService;

    public Page<User> getAll(int page, int limit){
        Pageable pageable = PageRequest.of(page,limit);
        return userRepository.findAll(pageable);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User register(SignupRequest signupRequest) throws MessagingException {

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
        //send verification code
        String code = randomVerificationCode();
        VerificationCode verificationCode = new VerificationCode(code,user);
        verifiCodeRepository.save(verificationCode);
        emailService.sendMail(user.getEmail(),code);
        return user;
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }
    public User update(User user) throws MessagingException {
        userRepository.save(user);
        //send verification code
        String code = randomVerificationCode();
        VerificationCode verificationCode = new VerificationCode(code,user);
        verifiCodeRepository.save(verificationCode);
        emailService.sendMail(user.getEmail(),code);
        return user;
    }
    public void confirmEmail(String code){
        VerificationCode verificationCode = verifiCodeRepository.findByCode(code);
        System.out.println(verificationCode.getCode());
        if(verificationCode != null){
             User user = userRepository.findById(verificationCode.getUser_id()).orElseThrow(()-> new RuntimeException("User not found!"));
             user.setEnabled(true);
             userRepository.save(user);
         };
    }
    public Page<User> search(SearchRequest searchRequest){
        SearchSpecification<User> specification = new SearchSpecification<>(searchRequest);
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage(), searchRequest.getLimit());
        return userRepository.findAll(specification, pageRequest);
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
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public boolean verificationUserEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean patternMatches(String email, String regexPattern){
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
}