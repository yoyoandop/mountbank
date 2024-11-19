package com.example.mountbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
@SpringBootApplication
@EnableWebSocket
public class MountbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(MountbankApplication.class, args);
    }

}
