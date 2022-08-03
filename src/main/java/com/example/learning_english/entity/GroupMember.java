//package com.example.learning_english.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "group_member")
//public class GroupMember {
//    @EmbeddedId
//    private GroupMemberId id;
//
//    @ManyToOne
//    @MapsId("groupId")
//    @JsonBackReference
//    private Group group;
//
//    @ManyToOne
//    @MapsId("userId")
//    @JsonBackReference
//    private User user;
//}
