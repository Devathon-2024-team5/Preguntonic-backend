/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.storage;

import com.devathon.preguntonic.model.Player;
import com.devathon.preguntonic.model.Room;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomStorage {

  void saveRoom(Room room);

  Optional<Room> getRoom(String roomCode);

  List<Room> getRooms();

  Optional<Room> deleteRoom(String roomCode);

  Optional<Room> updateRoom(String roomCode, Room room);

  boolean existsRoom(String roomCode);

  Optional<Player> addPlayerToRoom(String roomCode, Player player);

  Optional<Player> updatePlayerInRoom(String roomCode, Player player);

  Optional<Player> removePlayerFromRoom(String roomCode, UUID playerId);

  Optional<Player> getPlayerFromRoom(String roomCode, UUID playerId);

  List<Player> getPlayersFromRoom(String roomCode);

  boolean existsPlayerInRoom(String roomCode, UUID playerId);
}
