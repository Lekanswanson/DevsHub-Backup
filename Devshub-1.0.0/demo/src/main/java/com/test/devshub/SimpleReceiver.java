package com.test.devshub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class SimpleReceiver
{
	@Value("${filename}")
	private String filename;

	@Autowired
	private SQL database;

	@Autowired
	private OnlineUsers onlineUsers;

	@JmsListener(destination="destination", containerFactory="myFactory")
	public void receiveSimpleMessage(Message message)
	{
		Member receiver;
		try {
			ArrayList<Message> inbox;
			receiver = onlineUsers.getMember(message.getReceiver());

			System.out.println("Receiving: "+message);
			if(receiver.getMessages().containsKey(message.getSender()))
			{
				inbox = receiver.getMessages().get(message.getSender());
				inbox.add(message);
			}
			else
			{
				inbox = new ArrayList<>();
				inbox.add(message);
			}
			receiver.addMessagesToInbox(message.getSender(), inbox);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
