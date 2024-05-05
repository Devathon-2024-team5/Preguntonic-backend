/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import com.devathon.preguntonic.dto.BasicPlayer;
import com.devathon.preguntonic.dto.RoomConfiguration;
import com.devathon.preguntonic.model.Game;
import com.devathon.preguntonic.model.Player;
import com.devathon.preguntonic.model.PlayerStatus;
import com.devathon.preguntonic.model.Room;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
   * @param playerInfo the playerInfo
   * @return the playerInfo id assigned to the new user in the room
   */
  BasicPlayer joinRoom(String roomCode, BasicPlayer playerInfo) throws InvalidParameterException;

  /**
   * Change the ready status of a player in a room, if room or player does not exist, it will return
   * a {@link InvalidParameterException}
   *
   * @param roomCode the room code
   * @param playerId the player id
   * @param ready the new ready status
   * @return the player with the new ready status
   */
  BasicPlayer changePlayerReadyStatus(String roomCode, UUID playerId, PlayerStatus ready)
      throws InvalidParameterException;

  /**
   * Get the player for a room, if the room does not exist, it will return Optional.Empty
   *
   * @param roomCode the room code
   * @param playerId the player id
   * @return the player
   */
  Optional<Player> getPlayer(String roomCode, UUID playerId);

  /**
   * Get the basic player for a room, if the room or player does not exist, it will return
   * Optional.Empty
   *
   * @param roomCode the room code
   * @param playerId the player id
   * @return the basic player
   */
  Optional<BasicPlayer> getBasicPlayer(String roomCode, UUID playerId);

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

  /**
   * Reset all params of the game (ready to start again)
   *
   * @param roomCode the room code
   */
  void resetGame(String roomCode);

  boolean roomContainsPlayer(String roomCode, UUID playerId);
}
