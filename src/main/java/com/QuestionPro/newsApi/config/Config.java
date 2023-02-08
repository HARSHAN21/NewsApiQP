package com.QuestionPro.newsApi.config;

import java.util.concurrent.Executor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// This is used to configure multi threading environment 

@EnableAsync
@Configuration
@EnableAutoConfiguration
public class Config {
	
	@Bean
	public Executor executor() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(500);
		executor.setMaxPoolSize(500);
		executor.setQueueCapacity(1000);
		executor.initialize();
		return executor;
	}

}
