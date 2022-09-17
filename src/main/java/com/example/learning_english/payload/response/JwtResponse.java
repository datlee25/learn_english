package com.example.learning_english.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class JwtResponse {
    private int id;
    private String accessToken;
    private String type = "Bearer";
    private String username;
    private String email;
    private List<Integer> groupId;

    private String refreshToken;

    private List<String> roles;

    public JwtResponse(int id, String accessToken, String refreshToken, String username, String email,List<Integer> groupId, List<String> roles) {
        this.id = id;
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.groupId =groupId;
        this.refreshToken = refreshToken;
    }

    public JwtResponse(String accessToken, String refreshToken, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.refreshToken = refreshToken;
    }
}

