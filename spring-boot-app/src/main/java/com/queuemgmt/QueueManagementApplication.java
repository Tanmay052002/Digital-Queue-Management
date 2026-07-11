package com.queuemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// main class - starts everything
@SpringBootApplication
public class QueueManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueueManagementApplication.class, args);
        System.out.println("=================================================");
        System.out.println(" Queue Management System started!");
        System.out.println(" Open http://localhost:80/login in your browser");
        System.out.println("=================================================");
    }

}
