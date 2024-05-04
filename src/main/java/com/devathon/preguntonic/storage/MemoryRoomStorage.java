/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.storage;

import com.devathon.preguntonic.model.Player;
import com.devathon.preguntonic.model.Room;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.extern.slf4j.Slf4j;

/** Memory implementation of the {@link RoomStorage} interface. */
@Slf4j
@Builder
public class MemoryRoomStorage implements RoomStorage {

  private static final String ROOM_NOT_FOUND = "Room not found: {}";
  @Default private Map<String, Room> rooms = Map.of();

  @Override
  public void saveRoom(final Room room) {
    if (rooms.containsKey(room.getCode())) {
      log.warn("Room already exists: {}", room);
      return;
    }
    rooms.put(room.getCode(), room);
    log.info("Room saved: {}", room);
  }

  @Override
  public Optional<Room> getRoom(final String roomCode) {
    return Optional.ofNullable(rooms.get(roomCode));
  }

  @Override
  public List<Room> getRooms() {
    return rooms.values().stream().toList();
  }

  @Override
  public Optional<Room> deleteRoom(final String roomCode) {
    if (!rooms.containsKey(roomCode)) {
      return Optional.empty();
    }

    Room room = rooms.remove(roomCode);
    log.info("Room deleted: {}", room);
    return Optional.of(room);
  }

  @Override
  public Optional<Room> updateRoom(final String roomCode, final Room room) {
    if (!rooms.containsKey(roomCode)) {
      log.warn(ROOM_NOT_FOUND, roomCode);
      return Optional.empty();
    }

    rooms.put(roomCode, room);
    log.info("Room updated: {}", room);
    return Optional.of(room);
  }

  @Override
  public boolean existsRoom(final String roomCode) {
    return rooms.containsKey(roomCode);
  }

  @Override
  public Optional<Player> addPlayerToRoom(final String roomCode, final Player player) {
    if (!rooms.containsKey(roomCode)) {
      log.warn(ROOM_NOT_FOUND, roomCode);
      return Optional.empty();
    }

    Room room = rooms.get(roomCode);
    Player playerSaved = room.addPlayer(player);
    log.info("Player added to room: {}", playerSaved);
    return Optional.of(playerSaved);
  }

  @Override
  public Optional<Player> updatePlayerInRoom(final String roomCode, final Player player) {
    // With this implementation, we don't need to update the player in the room
    // (no additional logic needed in storage)
    return Optional.ofNullable(player);
  }

  @Override
  public Optional<Player> removePlayerFromRoom(final String roomCode, final UUID playerId) {
    if (!rooms.containsKey(roomCode)) {
      log.warn(ROOM_NOT_FOUND, roomCode);
      return Optional.empty();
    }

    Room room = rooms.get(roomCode);
    Player player = room.removePlayer(playerId);
    log.info("Player removed from room: {}", player);
    return Optional.of(player);
  }

  @Override
  public Optional<Player> getPlayerFromRoom(final String roomCode, final UUID playerId) {
    if (!rooms.containsKey(roomCode)) {
      log.warn(ROOM_NOT_FOUND, roomCode);
      return Optional.empty();
    }

    Room room = rooms.get(roomCode);
    Optional<Player> player = room.getPlayer(playerId);
    if (player.isEmpty()) {
      log.warn("Player not found: {}", playerId);
      return Optional.empty();
    }

    log.info("Player found: {}", player.get());
    return player;
  }

  @Override
  public List<Player> getPlayersFromRoom(final String roomCode) {
    if (!rooms.containsKey(roomCode)) {
      log.warn(ROOM_NOT_FOUND, roomCode);
      return List.of();
    }

    return rooms.get(roomCode).getPlayers();
  }

  @Override
  public boolean existsPlayerInRoom(final String roomCode, final UUID playerId) {
    if (!rooms.containsKey(roomCode)) {
      log.warn(ROOM_NOT_FOUND, roomCode);
      return false;
    }

    return rooms.get(roomCode).getPlayer(playerId).isPresent();
  }
}
