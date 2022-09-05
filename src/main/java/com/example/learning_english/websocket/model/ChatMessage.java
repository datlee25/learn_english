package com.example.learning_english.websocket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private MessageType type;
    private String content;
    private String sender;
    private Date createDate;
    public enum MessageType{
        CHAT,JOIN,LEAVE
    }

    public ChatMessage(MessageType type, String content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.createDate = new Date();
    }
}
