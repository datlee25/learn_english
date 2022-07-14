package com.example.learning_english.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshResponse {
    private String accessToken;
    private String type = "Bearer";
    private String refreshToken;

    public TokenRefreshResponse(String accessToken,String refreshToken ){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
