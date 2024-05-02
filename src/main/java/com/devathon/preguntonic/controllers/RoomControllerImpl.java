/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.dto.BasicPlayer;
import com.devathon.preguntonic.dto.LobbyEvent;
import com.devathon.preguntonic.dto.LobbyStatusDto;
import com.devathon.preguntonic.dto.RoomCodeResponse;
import com.devathon.preguntonic.dto.RoomConfiguration;
import com.devathon.preguntonic.model.Room;
import com.devathon.preguntonic.model.RoomEvent;
import com.devathon.preguntonic.services.RoomService;
import java.security.InvalidParameterException;
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
  private static final String ROOM_NOT_FOUND = "Room {} not found";
  private static final String PLAYER_READY_ERROR_MSG = "Error changing player ready status";
  private static final String ERROR_JOINING_ROOM_MSG = "Error joining room";
  private final RoomService roomService;
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public ResponseEntity<RoomCodeResponse> createRoom(final RoomConfiguration roomConfiguration) {
    final Room room = roomService.createRoom(roomConfiguration);
    log.info("Room created: {}", room);
    return ResponseEntity.ok(RoomCodeResponse.builder().roomCode(room.getCode()).build());
  }

  @Override
  public ResponseEntity<BasicPlayer> addPlayerRoom(final String roomId, final BasicPlayer player) {
    log.info("Adding player {} to room {}", player.name(), roomId);
    try {
      return ResponseEntity.ok(roomService.joinRoom(roomId, player));
    } catch (final Exception e) {
      log.warn(ERROR_JOINING_ROOM_MSG, e);
      return ResponseEntity.badRequest().build();
    }
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
  public ResponseEntity<LobbyEvent> joinRoom(final String roomId, final BasicPlayer player) {
    log.info("Joining room {} with user {}", roomId, player.name());
    try {
      BasicPlayer playerResponse = roomService.joinRoom(roomId, player);
      log.info("Player {} joined room {}", playerResponse.id(), roomId);
    } catch (final InvalidParameterException e) {
      log.warn(ERROR_JOINING_ROOM_MSG, e);
      return ResponseEntity.badRequest().build();
    } catch (final Exception e) {
      log.warn(ERROR_JOINING_ROOM_MSG, e);
      return ResponseEntity.internalServerError().build();
    }

    var lobbyEvent = sentLobbyEventToRoomTopic(roomId, RoomEvent.JOIN);
    return ResponseEntity.ok(lobbyEvent);
  }

  @Override
  public ResponseEntity<LobbyEvent> playerReadyInRoom(final String roomId, final int playerId) {
    log.info("Player {} is ready in room {}", playerId, roomId);
    try {
      roomService.changePlayerReadyStatus(roomId, playerId, true);
    } catch (final InvalidParameterException e) {
      log.warn(PLAYER_READY_ERROR_MSG, e);
      return ResponseEntity.badRequest().build();
    } catch (final Exception e) {
      log.warn(PLAYER_READY_ERROR_MSG, e);
      return ResponseEntity.internalServerError().build();
    }

    var lobbyEvent = sentLobbyEventToRoomTopic(roomId, RoomEvent.READY);
    return ResponseEntity.ok(lobbyEvent);
  }

  @Override
  public ResponseEntity<LobbyEvent> playerUnReadyInRoom(final String roomId, final int playerId) {
    log.info("Player {} is unready in room {}", playerId, roomId);

    try {
      roomService.changePlayerReadyStatus(roomId, playerId, false);
    } catch (final InvalidParameterException e) {
      log.warn(PLAYER_READY_ERROR_MSG, e);
      return ResponseEntity.badRequest().build();
    } catch (final Exception e) {
      log.warn(PLAYER_READY_ERROR_MSG, e);
      return ResponseEntity.internalServerError().build();
    }

    var lobbyEvent = sentLobbyEventToRoomTopic(roomId, RoomEvent.UNREADY);
    return ResponseEntity.ok(lobbyEvent);
  }

  private LobbyEvent sentLobbyEventToRoomTopic(final String roomId, final RoomEvent roomEvent) {
    return roomService
        .getRoom(roomId)
        .map(
            room -> {
              var lobbyEvent =
                  LobbyEvent.builder()
                      .event(roomEvent)
                      .roomStatus(LobbyStatusDto.from(room))
                      .build();
              messagingTemplate.convertAndSend(ROOM_DESTINATION_BASE_PATH + roomId, lobbyEvent);
              return lobbyEvent;
            })
        .orElse(LobbyEvent.builder().build());
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
