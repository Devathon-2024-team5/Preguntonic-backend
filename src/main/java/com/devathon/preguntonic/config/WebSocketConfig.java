/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/** This file contains the configuration for the WebSocket. */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * This method registers the endpoint for the WebSocket.
   *
   * @param registry the registry of the Stomp endpoints.
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint("/preguntonic") // The endpoint for the WebSocket.
        .setAllowedOriginPatterns("*") // The allowed origins.
        .withSockJS(); // Enable SockJS -> Add support to clients that don't support WebSocket.
  }

  /**
   * This method configures the message broker.
   *
   * <p>[1] The prefix for the application destination is used to filter the messages that are sent
   * from the client to the server. [2] Enable a simple broker in memory to send messages from the
   * server to the client. The destinations are used to filter the messages. Any message that
   * doesn't match with any of the destinations will be ignored.
   *
   * @param registry the registry of the message broker.
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry
        // [1] The prefix for the application destination.
        .setApplicationDestinationPrefixes("/app")
        // [2] Enable the simple broker (some examples of destinations).
        .enableSimpleBroker("/room");
  }
}
