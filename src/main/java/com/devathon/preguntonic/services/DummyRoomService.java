/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import com.devathon.preguntonic.dto.BasicPlayer;
import com.devathon.preguntonic.dto.RoomPlayerInitInfo;
import com.devathon.preguntonic.dto.RoomPlayerInitResponse;
import com.devathon.preguntonic.model.Room;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class DummyRoomService implements RoomService {

  private static final Random random = new Random();
  private final List<Room> rooms;
  private final Map<String, List<String>> roomUsers;

  public DummyRoomService() {
    this.rooms = new ArrayList<>();
    this.roomUsers = new HashMap<>();
  }

  @Override
  public RoomPlayerInitResponse createRoom(final RoomPlayerInitInfo roomPlayerInitInfo) {
    String roomCode = "R0Om" + random.nextInt(1000);
    Room room =
        Room.builder()
            .code(roomCode)
            .maxPlayers(roomPlayerInitInfo.maxPlayers())
            .numQuestions(roomPlayerInitInfo.numberOfQuestions())
            .createdAt(LocalDateTime.now())
            .build();
    rooms.add(room);
    return RoomPlayerInitResponse.builder().roomCode(roomCode).playerId(rooms.size()).build();
  }

  @Override
  public List<Room> getRooms() {
    return rooms;
  }

  @Override
  public Optional<Room> getRoom(final String roomCode) {
    return rooms.stream().filter(r -> r.getCode().equals(roomCode)).findFirst();
  }

  @Override
  public int joinRoom(final String roomCode, final String username) {
    roomUsers.computeIfAbsent(roomCode, k -> new ArrayList<>()).add(username);
    return roomUsers.get(roomCode).size();
  }

  @Override
  public Optional<BasicPlayer> getPlayer(final String roomCode, final int playerId) {
    if (roomUsers.containsKey(roomCode) && roomUsers.get(roomCode).size() >= playerId) {
      return Optional.of(
          BasicPlayer.builder()
              .name(roomUsers.get(roomCode).get(playerId - 1))
              .id(playerId)
              .build());
    }
    return Optional.empty();
  }
}
