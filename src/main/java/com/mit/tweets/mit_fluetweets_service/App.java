package com.mit.tweets.mit_fluetweets_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * starting point of application. 
 *
 */
@SpringBootApplication(scanBasePackages = {"com.mit.tweets.controlller","com.mit.tweets.service","com.mit.tweets.exception"})
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
}
