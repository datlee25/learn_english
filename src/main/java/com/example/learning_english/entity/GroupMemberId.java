package com.example.learning_english.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GroupMemberId implements Serializable {
    @Column(name = "groupId")
    private int groupId;
    @Column(name = "userId")
    private int userId;
}
