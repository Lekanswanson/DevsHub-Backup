package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class DemoApplication
{
	public static void main(String[] args)
	{
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

		System.out.println("\n----Books--------\n");
		for(int i=0; i<ctx.getBean(Book.class).getBooksdb().size(); i++)
		{
			System.out.println(ctx.getBean(Book.class).getBooksdb().get(i).toString());
		}
	}

	@Bean
	@Scope("request")
	public String timestamp()
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		return timeStamp;
	}
}
