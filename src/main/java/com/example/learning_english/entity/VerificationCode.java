package com.example.learning_english.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "verification_code")
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 6,nullable = false)
    private String code;

    private LocalDateTime expiryDate;

    private int user_id;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.REMOVE,CascadeType.PERSIST})
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private User user;

    public VerificationCode(String code, User user) {
        this.code = code;
        this.expiryDate = LocalDateTime.now().plusDays(1);
        this.user_id = user.getId();
        this.user = user;
    }
}
