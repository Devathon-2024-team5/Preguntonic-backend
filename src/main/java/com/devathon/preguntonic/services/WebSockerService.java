/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class WebSockerService {

  @Autowired private SimpMessagingTemplate simpMessagingTemplate;

  /** Example of a method that sends a message to the topic "/room/room1". */
  @Scheduled(fixedRate = 5000)
  public void exampleScheduledMethod() {

    final String message = "Hello, this is a scheduled message!";
    final String topic = "/room/room1";

    simpMessagingTemplate.convertAndSend(topic, message);
  }
}
