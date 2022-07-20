package com.test.devshub;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class SimpleSender
{
	@Autowired
	private JmsTemplate jmsTemplate;

    @Autowired
    private SQL database;

    @Autowired
    private OnlineUsers onlineUsers;

    public SimpleSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendSimpleMessage(Message message)
    {
        ArrayList<Message> inbox;
        Member sender;

        sender = onlineUsers.getMember(message.getSender());
        if(sender.getMessages().containsKey(message.getReceiver()))
        {
            inbox = sender.getMessages().get(message.getReceiver());
            inbox.add(message);
        }
        else
        {
            inbox = new ArrayList<>();
            inbox.add(message);
        }
        sender.addMessagesToInbox(message.getReceiver(), inbox);

        System.out.println("Sending: " + message);
        jmsTemplate.convertAndSend("destination", message);
        try {
        	Thread.sleep(500);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}