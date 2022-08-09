package com.example.learning_english.entity;

import com.example.learning_english.entity.enums.EGroupLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    private String name ;
    @Enumerated(EnumType.STRING)
    private EGroupLevel groupLevel;
    private BigDecimal price;

    //Cách 1:
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<GroupMember> groupMembers;

    //Cách 2:
//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                CascadeType.PERSIST,
//                CascadeType.MERGE
//            }
//    )
//    @JoinTable(name = "group_member",
//        joinColumns = @JoinColumn(name = "group_id"),
//        inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private Set<User> users = new HashSet<>();

}
