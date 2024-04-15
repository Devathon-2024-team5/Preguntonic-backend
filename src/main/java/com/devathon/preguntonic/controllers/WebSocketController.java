/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/** This class contains the methods that handle the WebSocket messages. */
@Slf4j
@Controller
public class WebSocketController {

  /**
   * Example of a method that receives a message from the client and sends it to the topic
   * "/room/room1".
   *
   * HELP:
   * <p>[1] The annotation @MessageMapping("/message") is used to map the message to the
   *    * destination "app/message" (the prefix is defined in the WebSocketConfig). [2] The
   *    * annotation @SendTo("/room/room1") is used to send the message to the topic "/room/room1".
   *
   * @param message the message received from the client.
   * @return the message received.
   */
  @MessageMapping("/message") // [1]
  @SendTo("/room/room1") // [2]
  public String example(String message) {

    log.info(
        "New message was received: {}. Sending it to the topic /room/room1...", message);
    return message;
  }
}
