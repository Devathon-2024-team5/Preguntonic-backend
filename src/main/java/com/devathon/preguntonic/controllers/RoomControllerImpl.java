/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.services.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomControllerImpl implements RoomController {
  private final RoomService roomService;
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public ResponseEntity<String> createRoom() {
    final String roomId = roomService.createRoom();
    log.info("Room created: {}", roomId);
    return ResponseEntity.ok(roomId);
  }

  @Override
  public ResponseEntity<List<String>> getRooms() {
    return ResponseEntity.ok(roomService.getRooms());
  }

  @Override
  public ResponseEntity<String> getRoom(final String roomId) {
    if (roomService.getRoom(roomId).isPresent()) return ResponseEntity.ok(roomId);
    return ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<String> joinRoom(final String roomId, final String username) {
    log.info("Joining room {} with user {}", roomId, username);
    if (roomService.getRoom(roomId).isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    roomService.joinRoom(roomId, username);
    messagingTemplate.convertAndSend("/room/" + roomId, username + " joined the room");
    return ResponseEntity.ok("Joined room " + roomId);
  }
}
