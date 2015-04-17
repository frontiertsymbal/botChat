package com.frontier.botChat;

public class User {

    int type;
    String message;
    String imageId;

    public User(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public User(int type, String message, String imageId) {
        this.type = type;
        this.message = message;
        this.imageId = imageId;
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

    @Override
    public String toString() {
        return "User{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
