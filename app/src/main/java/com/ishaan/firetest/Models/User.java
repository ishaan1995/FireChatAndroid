package com.ishaan.firetest.Models;

/**
 * Created by ishaan on 29/8/16.
 */
public class User {

    private String userId;
    private String senderId;
    private String message;
    private long timeStamp;
    private int messageType;

    public User(){
        //timeStamp = System.currentTimeMillis();
    }

    public User(String message, String userId, int messageType){
        this.userId = userId;
        this.senderId = userId;
        this.message = message;
        this.timeStamp = -System.currentTimeMillis();
        this.messageType = messageType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getUserId() {
        return userId;
    }

    public int getMessageType() {
        return messageType;
    }
}
