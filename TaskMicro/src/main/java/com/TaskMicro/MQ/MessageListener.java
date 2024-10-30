package com.TaskMicro.MQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @JmsListener(destination = "MicroService-Trainer-ADD")
    public void listenadd(String message) {
        System.out.println("Received message: " + message);
    }
    @JmsListener(destination = "MicroService-Trainer-DELETE")
    public void listendelete(String message) {
        System.out.println("Received message: " + message);
    }
}