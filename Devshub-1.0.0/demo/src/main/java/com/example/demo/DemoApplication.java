package com.example.demo;

import com.test.devshub.Member;
import com.test.devshub.OnlineUsers;
import com.test.devshub.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import javax.jms.ConnectionFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
@EnableAutoConfiguration
public class DemoApplication
{
	public static void main(String[] args)
	{
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

//		System.out.println("\n----Books--------\n");
//		for(int i=0; i<ctx.getBean(Book.class).getBooksdb().size(); i++)
//		{
//			System.out.println(ctx.getBean(Book.class).getBooksdb().get(i).toString());
//		}

		ctx.getBean(SQL.class).initDBConnection();
	}

	@Bean
	@Scope("request")
	public String timestamp()
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		return timeStamp;
	}

	// This bean is necessary, to allow custom objects to be passed as message payloads (this converter converts objects to/from JSON).
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_id");
		return converter;
	}

	// This bean is not really needed - it just does exactly what the default container factory would do in Spring Boot.
	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

		// This provides all boot's default to this factory, including the message converter.
		// You can override some of Boot's default if necessary.
		configurer.configure(factory, connectionFactory);
		return factory;
	}
}
