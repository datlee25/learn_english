package com.example.learning_english.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "refreshtoken")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    @OneToOne
    @JoinColumn(name = "userId",referencedColumnName = "id",updatable = false,insertable = false)
    private User user;
    @Column(nullable = false,unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;

    public RefreshToken(User user, String token, Instant expiryDate){
        this.userId = user.getId();
        this.user = user;
        this.token = token;
        this.expiryDate = expiryDate;
    }
}
