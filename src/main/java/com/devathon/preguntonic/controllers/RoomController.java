/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.dto.BasicPlayer;
import com.devathon.preguntonic.dto.LobbyEvent;
import com.devathon.preguntonic.dto.RoomConfiguration;
import com.devathon.preguntonic.model.Room;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@RequestMapping("/v1/rooms")
public interface RoomController {

  @PostMapping
  ResponseEntity<String> createRoom(@RequestBody RoomConfiguration roomConfiguration);

  @GetMapping
  ResponseEntity<List<String>> getRooms();

  @GetMapping("/{roomId}")
  ResponseEntity<Room> getRoom(@PathVariable String roomId);

  @PostMapping("/{roomId}/players")
  ResponseEntity<BasicPlayer> addPlayerRoom(
      @PathVariable String roomId, @RequestBody BasicPlayer player);

  // Websocket endpoints
  @MessageMapping("/rooms/{roomId}/lobby/join")
  ResponseEntity<LobbyEvent> joinRoom(@DestinationVariable String roomId, BasicPlayer player);

  @MessageMapping("/rooms/{roomId}/lobby/players/{playerId}/ready")
  ResponseEntity<LobbyEvent> playerReadyInRoom(
      @DestinationVariable String roomId, @DestinationVariable int playerId);

  @MessageMapping("/rooms/{roomId}/lobby/players/{playerId}/unready")
  ResponseEntity<LobbyEvent> playerUnReadyInRoom(
      @DestinationVariable String roomId, @DestinationVariable int playerId);

  @EventListener
  void handleWebSocketConnectListener(SessionConnectEvent event);

  @EventListener
  void handleWebSocketDisconnectListener(SessionDisconnectEvent event);
}
