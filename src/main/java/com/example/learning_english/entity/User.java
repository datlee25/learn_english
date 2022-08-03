package com.example.learning_english.entity;

import com.example.learning_english.entity.base.BaseEntity;
import com.example.learning_english.entity.enums.EAuthProvider;
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
@ToString
@Table(name="users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String username;
    public String password;
    public String email;
    public String age;
    public String qualification;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL )
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> roles = new HashSet<>();

    //Cách 1:
//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference
//    private Set<GroupMember> groupMembers;

    //Cách 2:

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy = "users")
    @JsonIgnore
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserScore> userScore;

    public User(String username, String password, String email,boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled =enabled;
    }
}
