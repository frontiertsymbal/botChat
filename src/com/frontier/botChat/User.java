package com.frontier.botChat;

public class User {

    int type;
    String message;

    public User(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }
}
