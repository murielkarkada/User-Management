package com.Impelsys.UserDemo;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class UserDemoApplication {

	public static void main(String[] args)
	{

		SpringApplication.run(UserDemoApplication.class, args);
	}

}
