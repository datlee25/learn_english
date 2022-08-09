package com.example.learning_english.entity;

import com.example.learning_english.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    public Role(ERole name) {
        this.name = name;
    }
}
