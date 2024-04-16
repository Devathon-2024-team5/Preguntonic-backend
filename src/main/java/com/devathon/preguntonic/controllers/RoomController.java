/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/rooms")
public interface RoomController {

  @PostMapping
  ResponseEntity<String> createRoom();

  @GetMapping
  ResponseEntity<List<String>> getRooms();

  @GetMapping("/{roomId}")
  ResponseEntity<String> getRoom(@PathVariable String roomId);

  @SubscribeMapping("/rooms.join/{roomId}")
  @SendTo("/topic/rooms/{roomId}")
  ResponseEntity<String> joinRoom(@DestinationVariable String roomId, String username);
}
