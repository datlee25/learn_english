package com.example.learning_english.entity;

import com.example.learning_english.entity.base.BaseEntity;
import com.example.learning_english.entity.enums.EAuthProvider;
import com.example.learning_english.entity.enums.EGroupLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    public int id;
    public String username;
    @JsonIgnore
    public String password;
    public String email;
    public String age;
    public EGroupLevel level;

    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> roles = new HashSet<>();

    //Cách 1:
    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JsonManagedReference
    private Set<GroupMember> groupMembers;

    //Cách 2:
//
//    @ManyToMany(fetch = FetchType.LAZY,
//        cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//        },
//        mappedBy = "users")
//    @JsonIgnore
//    private Set<Group> groups = new HashSet<>();

//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private Set<UserScore> userScore;

    public User(String username, String password, String email,boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled =enabled;
    }
}
