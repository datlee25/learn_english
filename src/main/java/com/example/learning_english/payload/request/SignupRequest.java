package com.example.learning_english.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private Set<String> role;
    
    @Size(min = 6, max = 40)
    private String password;

    public SignupRequest(String username, String email){
        this.email = email;
        this.username = username;
    }
}
