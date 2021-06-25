package com.socialmedia.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@SpringBootApplication
public class SocialmediaApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SocialmediaApplication.class, args);
    }


}
