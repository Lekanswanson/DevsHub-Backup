package com.test.devshub;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Date;

@Component
@RequestScope
public class Message
{
    private int size=0;
    private String sender;
    private String receiver;
    private String date = new Date().toString();
    private String message;
    private String showClass="show";
    private String hideClass="hide";

    public Message() {
    }

    public Message(String sender, String receiver, String message, String showClass, String hideClass) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.showClass=showClass;
        this.hideClass=hideClass;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShowClass() {
        return showClass;
    }

    public void setShowClass(String showClass) {
        this.showClass = showClass;
    }

    public String getHideClass() {
        return hideClass;
    }

    public void setHideClass(String hideClass) {
        this.hideClass = hideClass;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("Received: %s,From: %s,To: %s,%s", date,sender,receiver,message);
    }
}
