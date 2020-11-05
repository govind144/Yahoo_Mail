package com.teama.yahoomail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YahooMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(YahooMailApplication.class, args);
	}

}
