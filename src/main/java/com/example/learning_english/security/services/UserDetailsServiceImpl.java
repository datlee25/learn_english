package com.example.learning_english.security.services;

import com.example.learning_english.dto.AccountDto;
import com.example.learning_english.dto.RegisterDto;
import com.example.learning_english.entity.Account;
import com.example.learning_english.entity.Role;
import com.example.learning_english.entity.enums.ERole;
import com.example.learning_english.repository.AccountRepository;
import com.example.learning_english.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    private static final String USER_ROLE = "user";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        Account account = accountOptional.orElse(null);

        if(account == null){
            throw new UsernameNotFoundException("User not found in web");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add((new SimpleGrantedAuthority(account.getRole().getName().toString())));
        return new User(account.getEmail(),account.getPassword(),authorities);
    }

    public AccountDto saveAccount(RegisterDto registerDto){
        Optional<Role> roleOptional = roleRepository.findByName(ERole.ROLE_USER);
        Role userRole = roleOptional.orElse(null);

        if(userRole == null){
            userRole = roleRepository.save(new Role(ERole.ROLE_USER));
        }

        Optional<Account> optionalAccount = accountRepository.findByEmail(registerDto.getEmail());

        if (optionalAccount.isPresent()){
            return null;
        }
        Account account = new Account();

        account.setEmail(registerDto.getEmail());
        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        account.setCreateAt(LocalDateTime.now());
        account.setUpdateAt(LocalDateTime.now());
        account.setRole(userRole);
        account.setStatus(1);
        Account save = accountRepository.save(account);
        return new AccountDto(save);

    }
    public Account getAccount(String email) {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        return accountOptional.orElse(null);
    }
}
