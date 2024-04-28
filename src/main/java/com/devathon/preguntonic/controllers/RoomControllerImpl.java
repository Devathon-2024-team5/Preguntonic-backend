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
import com.devathon.preguntonic.dto.RoomStatusDto;
import com.devathon.preguntonic.dto.RoomStatusEvent;
import com.devathon.preguntonic.model.Room;
import com.devathon.preguntonic.model.RoomEvent;
import com.devathon.preguntonic.services.RoomService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
  private static final String ROOM_NOT_FOUND = "Room {} not found";
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
              log.info(ROOM_NOT_FOUND, room);
              return ResponseEntity.ok(room);
            })
        .orElse(ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<PlayerEvent> joinRoom(final String roomId, final BasicPlayer player) {
    log.info("Joining room {} with user {}", roomId, player.name());
    Optional<Room> room = roomService.getRoom(roomId);
    if (room.isEmpty()) {
      log.info(ROOM_NOT_FOUND, roomId);
      return ResponseEntity.notFound().build();
    }
    int playerId = -1;
    // Special cases for the room creator!
    if (Objects.nonNull(player.id())) {
      playerId = player.id();
    } else {
      playerId = roomService.joinRoom(roomId, player.name());
    }
    BasicPlayer playerWithId = new BasicPlayer(playerId, player.name(), player.avatarId());

    RoomStatusEvent roomStatusEvent =
        RoomStatusEvent.builder()
            .event(RoomEvent.JOIN)
            .roomStatus(RoomStatusDto.from(room.get()))
            .build();
    messagingTemplate.convertAndSend(ROOM_DESTINATION_BASE_PATH + roomId, roomStatusEvent);

    log.info("Player {} joined room {}", playerId, roomId);
    PlayerEvent playerEvent =
        PlayerEvent.builder().player(playerWithId).event(RoomEvent.JOIN).build();
    return ResponseEntity.ok(playerEvent);
  }

  @Override
  public ResponseEntity<PlayerEvent> playerReadyInRoom(final String roomId, final int playerId) {
    log.info("Player {} is ready in room {}", playerId, roomId);
    Optional<Room> room = roomService.getRoom(roomId);
    if (room.isEmpty()) {
      log.info(ROOM_NOT_FOUND, roomId);
      return ResponseEntity.notFound().build();
    }
    // Move to service
    room.get().getPlayers().stream()
        .filter(p -> p.getId() == playerId)
        .findFirst()
        .ifPresent(player -> player.setReady(true));

    RoomStatusEvent roomStatusEvent =
        RoomStatusEvent.builder()
            .event(RoomEvent.READY)
            .roomStatus(RoomStatusDto.from(room.get()))
            .build();
    messagingTemplate.convertAndSend(ROOM_DESTINATION_BASE_PATH + roomId, roomStatusEvent);

    PlayerEvent playerEvent = buildPlayerEvent(roomId, playerId, RoomEvent.READY);
    return ResponseEntity.ok(playerEvent);
  }

  @Override
  public ResponseEntity<PlayerEvent> playerUnReadyInRoom(final String roomId, final int playerId) {
    log.info("Player {} is unready in room {}", playerId, roomId);
    Optional<Room> room = roomService.getRoom(roomId);
    if (room.isEmpty()) {
      log.info(ROOM_NOT_FOUND, roomId);
      return ResponseEntity.notFound().build();
    }
    // Move to service
    room.get().getPlayers().stream()
        .filter(p -> p.getId() == playerId)
        .findFirst()
        .ifPresent(player -> player.setReady(false));
    RoomStatusEvent roomStatusEvent =
        RoomStatusEvent.builder()
            .event(RoomEvent.UNREADY)
            .roomStatus(RoomStatusDto.from(room.get()))
            .build();

    messagingTemplate.convertAndSend(ROOM_DESTINATION_BASE_PATH + roomId, roomStatusEvent);

    PlayerEvent playerEvent = buildPlayerEvent(roomId, playerId, RoomEvent.UNREADY);
    return ResponseEntity.ok(playerEvent);
  }

  private PlayerEvent buildPlayerEvent(
      final String roomId, final int playerId, final RoomEvent roomEvent) {
    BasicPlayer player =
        roomService
            .getPlayer(roomId, playerId)
            .orElseThrow(() -> new IllegalArgumentException("Player not found"));

    return PlayerEvent.builder().player(player).event(roomEvent).build();
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
