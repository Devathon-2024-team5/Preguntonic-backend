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
import java.util.List;
import java.util.Optional;

/** Service to manage rooms */
public interface RoomService {

  RoomPlayerInitResponse createRoom(RoomPlayerInitInfo roomPlayerInitInfo);

  List<Room> getRooms();

  Optional<Room> getRoom(String roomCode);

  /**
   * Join a room, we are going to add a new user to the room and assign a playerId to it. if the
   * room does not exist, it will return Exception
   *
   * @param roomCode the room code
   * @param username the username
   * @return the player id assigned to the new user in the room
   */
  int joinRoom(String roomCode, String username);

  Optional<BasicPlayer> getPlayer(String roomCode, int playerId);
}
