package com.frontier.botChat;

public class User {

    int type;
    String message;
    String imageId;
    String chatTime;

    public User(int type, String message, String chatTime) {
        this.type = type;
        this.message = message;
        this.chatTime = chatTime;
    }

    public User(int type, String message, String imageId, String chatTime) {
        this.type = type;
        this.message = message;
        this.imageId = imageId;
        this.chatTime = chatTime;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    public String getImageId() {
        return imageId;
    }

    public String getChatTime() {
        return chatTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
