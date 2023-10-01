package com.example.messengerfirebase.model;

public class Message {

    private String text;
    private String senderID;
    private String receiverID;

    public Message(String text, String senderID, String receiverID) {
        this.text = text;
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public Message() {
    }

    public String getText() {
        return text;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }
}
