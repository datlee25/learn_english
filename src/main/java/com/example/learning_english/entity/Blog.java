package com.example.learning_english.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(nullable = false)
    @NotBlank(message = "Blog title is mandatory")
    public String title;
    public String synopsis;
    @Column(nullable = false)
    @NotBlank(message = "Blog detail is mandatory")
    public String detail;
}
