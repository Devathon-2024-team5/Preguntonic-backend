/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.dto.BasicPlayer;
import com.devathon.preguntonic.dto.LobbyEvent;
import com.devathon.preguntonic.dto.RoomCodeResponse;
import com.devathon.preguntonic.dto.RoomConfiguration;
import com.devathon.preguntonic.model.Player;
import com.devathon.preguntonic.model.Room;
import java.util.List;
import java.util.UUID;
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
@MessageMapping("/rooms/{roomId}/lobby")
public interface RoomController {

  @PostMapping
  ResponseEntity<RoomCodeResponse> createRoom(@RequestBody RoomConfiguration roomConfiguration);

  @GetMapping
  ResponseEntity<List<Room>> getRooms();

  @GetMapping("/{roomId}")
  ResponseEntity<Room> getRoom(@PathVariable("roomId") String roomId);

  @PostMapping("/{roomId}/players")
  ResponseEntity<BasicPlayer> addPlayerRoom(
      @PathVariable("roomId") String roomId, @RequestBody BasicPlayer player);

  @GetMapping("/{roomId}/players")
  ResponseEntity<List<Player>> getPlayersInRoom(@PathVariable("roomId") String roomId);

  // Websocket endpoints
  @MessageMapping("/join")
  ResponseEntity<LobbyEvent> joinRoom(
      @DestinationVariable("roomId") String roomId, BasicPlayer player);

  @MessageMapping("/players/{playerId}/ready")
  ResponseEntity<LobbyEvent> playerReadyInRoom(
      @DestinationVariable("roomId") String roomId, @DestinationVariable("playerId") UUID playerId);

  @MessageMapping("/players/{playerId}/unready")
  ResponseEntity<LobbyEvent> playerUnReadyInRoom(
      @DestinationVariable("roomId") String roomId, @DestinationVariable("playerId") UUID playerId);

  @EventListener
  void handleWebSocketConnectListener(SessionConnectEvent event);

  @EventListener
  void handleWebSocketDisconnectListener(SessionDisconnectEvent event);
}
