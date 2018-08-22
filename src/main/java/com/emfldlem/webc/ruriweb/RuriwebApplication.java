package com.emfldlem.webc.ruriweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RuriwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuriwebApplication.class, args);
    }

/*
    @Bean
    public TaskScheduler taskScheduler1() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public TaskScheduler taskScheduler2() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }*/


}
