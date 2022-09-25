package com.example.learning_english.entity;

import com.example.learning_english.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "user_score")
public class UserScore extends BaseEntity {
    @EmbeddedId
    @JsonIgnore
    private UserScoreId id;
    private String name;
    private BigDecimal score;

    @ManyToOne
    @MapsId("userId")
    @JsonBackReference
    private User user;

    @ManyToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    @MapsId("exerciseId")
    @JsonBackReference
    private Exercise exercise;
}