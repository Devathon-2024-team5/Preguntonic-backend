/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.dto.BasicPlayer;
import com.devathon.preguntonic.dto.PlayerEvent;
import com.devathon.preguntonic.dto.RoomPlayerInitInfo;
import com.devathon.preguntonic.dto.RoomPlayerInitResponse;
import com.devathon.preguntonic.model.Room;
import com.devathon.preguntonic.model.RoomEvent;
import com.devathon.preguntonic.services.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomControllerImpl implements RoomController {

  private static final String ROOM_DESTINATION_BASE_PATH = "/room/";
  private final RoomService roomService;
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public ResponseEntity<RoomPlayerInitResponse> createRoom(
      final RoomPlayerInitInfo roomPlayerInitInfo) {
    final RoomPlayerInitResponse roomPlayerInitResponse =
        roomService.createRoom(roomPlayerInitInfo);
    log.info("Room created: {}", roomPlayerInitResponse);

    return ResponseEntity.ok(roomPlayerInitResponse);
  }

  @Override
  public ResponseEntity<List<String>> getRooms() {
    return ResponseEntity.ok(roomService.getRooms().stream().map(Room::getCode).toList());
  }

  @Override
  public ResponseEntity<Room> getRoom(final String roomId) {
    return roomService
        .getRoom(roomId)
        .map(
            room -> {
              log.info("Room found: {}", room);
              return ResponseEntity.ok(room);
            })
        .orElse(ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<PlayerEvent> joinRoom(final String roomId, final BasicPlayer player) {
    log.info("Joining room {} with user {}", roomId, player.name());
    if (roomService.getRoom(roomId).isEmpty()) {
      log.info("Room {} not found", roomId);
      return ResponseEntity.notFound().build();
    }

    int playerId = roomService.joinRoom(roomId, player.name());
    BasicPlayer playerWithId = new BasicPlayer(playerId, player.name(), player.avatarId());

    PlayerEvent playerEvent =
        PlayerEvent.builder().player(playerWithId).event(RoomEvent.JOIN.name()).build();
    messagingTemplate.convertAndSend(ROOM_DESTINATION_BASE_PATH + roomId, playerEvent);

    log.info("Player {} joined room {}", playerId, roomId);
    return ResponseEntity.ok(playerEvent);
  }

  @Override
  public ResponseEntity<PlayerEvent> playerReadyInRoom(final String roomId, final int playerId) {
    log.info("Player {} is ready in room {}", playerId, roomId);

    PlayerEvent playerEvent = buildPlayerEvent(roomId, playerId, RoomEvent.READY);

    messagingTemplate.convertAndSend(ROOM_DESTINATION_BASE_PATH + roomId, playerEvent);
    return ResponseEntity.ok(playerEvent);
  }

  @Override
  public ResponseEntity<PlayerEvent> playerUnReadyInRoom(final String roomId, final int playerId) {
    log.info("Player {} is unready in room {}", playerId, roomId);

    PlayerEvent playerEvent = buildPlayerEvent(roomId, playerId, RoomEvent.UNREADY);

    messagingTemplate.convertAndSend(ROOM_DESTINATION_BASE_PATH + roomId, playerEvent);
    return ResponseEntity.ok(playerEvent);
  }

  private PlayerEvent buildPlayerEvent(
      final String roomId, final int playerId, final RoomEvent roomEvent) {
    BasicPlayer player =
        roomService
            .getPlayer(roomId, playerId)
            .orElseThrow(() -> new IllegalArgumentException("Player not found"));

    return PlayerEvent.builder().player(player).event(roomEvent.name()).build();
  }

  @Override
  public void handleWebSocketConnectListener(final SessionConnectEvent event) {
    var headers = StompHeaderAccessor.wrap(event.getMessage());
    log.info("Session connected => headers: {}", headers);
  }

  @Override
  public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {
    var headers = StompHeaderAccessor.wrap(event.getMessage());
    log.info("Session disconnected => headers: {}", headers);
  }
}
