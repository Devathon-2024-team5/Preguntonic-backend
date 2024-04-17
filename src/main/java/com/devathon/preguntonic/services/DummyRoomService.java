/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

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
  private final List<String> rooms;
  private final Map<String, List<String>> roomUsers;

  public DummyRoomService() {
    this.rooms = new ArrayList<>();
    this.roomUsers = new HashMap<>();
  }

  @Override
  public String createRoom() {
    String roomId = "R0Om" + random.nextInt(1000);
    rooms.add(roomId);
    return roomId;
  }

  @Override
  public List<String> getRooms() {
    return rooms;
  }

  @Override
  public Optional<String> getRoom(final String roomId) {
    return rooms.stream().filter(r -> r.equals(roomId)).findFirst();
  }

  @Override
  public void joinRoom(final String roomId, final String username) {
    roomUsers.computeIfAbsent(roomId, k -> new ArrayList<>()).add(username);
  }
}
