/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import com.devathon.preguntonic.dto.BasicPlayer;
import com.devathon.preguntonic.dto.RoomConfiguration;
import com.devathon.preguntonic.model.Game;
import com.devathon.preguntonic.model.Room;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

/** Service to manage rooms */
public interface RoomService {

  Room createRoom(RoomConfiguration roomConfiguration);

  List<Room> getRooms();

  Optional<Room> getRoom(String roomCode);

  /**
   * Join a room, we are going to add a new user to the room and assign a playerId to it. if the
   * room does not exist, it will return {@link InvalidParameterException}
   *
   * @param roomCode the room code
   * @param username the username
   * @return the player id assigned to the new user in the room
   */
  BasicPlayer joinRoom(String roomCode, BasicPlayer username) throws InvalidParameterException;

  /**
   * Change the ready status of a player in a room, if room or player does not exist, it will return
   * a {@link InvalidParameterException}
   *
   * @param roomCode the room code
   * @param playerId the player id
   * @param ready the new ready status
   * @return the player with the new ready status
   */
  BasicPlayer changePlayerReadyStatus(String roomCode, int playerId, boolean ready)
      throws InvalidParameterException;

  Optional<BasicPlayer> getPlayer(String roomCode, int playerId);

  /**
   * Get the game for a room, if the room does not exist, it will return {@link
   * InvalidParameterException}
   *
   * @param roomCode
   * @return the game for the room
   */
  Optional<Game> getGame(String roomCode);

  /**
   * create a game for a room, if the room does not exist, it will return {@link
   * InvalidParameterException} If the room already has a game, it will return {@link
   * InvalidParameterException}
   *
   * @param roomCode
   * @return the game created
   */
  Game createGame(String roomCode) throws InvalidParameterException;
}
