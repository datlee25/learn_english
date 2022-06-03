package com.example.learning_english.dto;

import com.example.learning_english.entity.Account;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AccountDto {
    private long id;
    private String email;
    private String role;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AccountDto(Account account){
        this.id =account.getId();
        this.createdAt=account.getCreateAt();
        this.email=account.getEmail();
        this.role=account.getRole().getName();
        this.status=account.getStatus();
        this.updatedAt=account.getUpdateAt();
    }
}
