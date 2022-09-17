package com.example.learning_english.entity;

import com.example.learning_english.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_member")
public class GroupMember extends BaseEntity {
    @EmbeddedId
    @JsonIgnore
    private GroupMemberId id;

    @ManyToOne
    @MapsId("groupId")
    @JsonBackReference
    private Group group;
    @ManyToOne
    @MapsId("userId")
    @JsonBackReference
    private User user;

    public GroupMember(GroupMemberId id, Group group, User user,LocalDateTime createAt, LocalDateTime updateAt) {
        super(createAt, updateAt);
        this.id = id;
        this.group = group;
        this.user = user;
    }
}
