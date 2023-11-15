package com.example.mychatbot.model;

// Message.java
public class Message {
    public static final String  SENT_BY_USER = "me";
    public static final String  SENT_BY_BOT = "bot";

    private String content;
    private String  role;

    public Message(String content, String  role) {
        this.content = content;
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public String getRole() {
        return role;
    }
}
