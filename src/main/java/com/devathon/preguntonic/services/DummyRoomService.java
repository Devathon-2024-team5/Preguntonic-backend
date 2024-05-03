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
import com.devathon.preguntonic.model.Room;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class DummyRoomService implements RoomService {

  private static final Random random = new Random();
  private static final String ROOM_NOT_FOUND_MSG = "Room not found";
  private final List<Room> rooms;
  private final Map<String, List<BasicPlayer>> roomUsers;

  public DummyRoomService() {
    this.rooms = new ArrayList<>();
    this.roomUsers = new HashMap<>();
  }

  @Override
  public Room createRoom(final RoomConfiguration roomConfiguration) {
    var roomCode = "R0Om" + random.nextInt(1000);
    var room =
        Room.builder()
            .code(roomCode)
            .maxPlayers(roomConfiguration.maxPlayers())
            .numQuestions(roomConfiguration.numberOfQuestions())
            .createdAt(LocalDateTime.now())
            .players(new ArrayList<>())
            .build();
    rooms.add(room);
    return room;
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
  public BasicPlayer joinRoom(final String roomCode, final BasicPlayer player) {
    roomUsers.computeIfAbsent(roomCode, k -> new ArrayList<>()).add(player);
    int playerId = -1;
    if (Objects.isNull(player.id())) {
      playerId = roomUsers.get(roomCode).size();
    } else {
      playerId = player.id();
    }

    var newPlayer =
        Player.builder()
            .id(playerId)
            .name(player.name())
            .avatarId(player.avatarId())
            .ready(false)
            .build();

    rooms.stream()
        .filter(r -> r.getCode().equals(roomCode))
        .findFirst()
        .ifPresent(r -> r.getPlayers().add(newPlayer));

    return BasicPlayer.builder()
        .id(newPlayer.getId())
        .avatarId(newPlayer.getAvatarId())
        .name(newPlayer.getName())
        .build();
  }

  @Override
  public BasicPlayer changePlayerReadyStatus(
      final String roomCode, final int playerId, final boolean ready)
      throws InvalidParameterException {
    Optional<Player> playerO = findPlayerInRoom(roomCode, playerId);

    if (playerO.isEmpty()) {
      throw new InvalidParameterException("Player not found");
    }

    var player = playerO.get();
    player.setReady(ready);
    return BasicPlayer.builder()
        .id(player.getId())
        .avatarId(player.getAvatarId())
        .name(player.getName())
        .build();
  }

  @Override
  public Optional<Game> getGame(final String roomCode) {
    Room room =
        rooms.stream()
            .filter(r -> r.getCode().equals(roomCode))
            .findFirst()
            .orElseThrow(() -> new InvalidParameterException(ROOM_NOT_FOUND_MSG));

    return Optional.ofNullable(room.getGame());
  }

  @Override
  public Game createGame(final String roomCode) throws InvalidParameterException {
    Room room =
        rooms.stream()
            .filter(r -> r.getCode().equals(roomCode))
            .findFirst()
            .orElseThrow(() -> new InvalidParameterException(ROOM_NOT_FOUND_MSG));

    Game game = Game.builder().build();
    room.setGame(game);
    return game;
  }

  @Override
  public Optional<BasicPlayer> getPlayer(final String roomCode, final int playerId) {
    return findPlayerInRoom(roomCode, playerId)
        .map(
            p ->
                BasicPlayer.builder()
                    .id(p.getId())
                    .avatarId(p.getAvatarId())
                    .name(p.getName())
                    .build());
  }

  private Optional<Player> findPlayerInRoom(String roomCode, int playerId) {
    return rooms.stream()
        .filter(r -> r.getCode().equals(roomCode))
        .findFirst()
        .orElseThrow(() -> new InvalidParameterException(ROOM_NOT_FOUND_MSG))
        .getPlayers()
        .stream()
        .filter(p -> p.getId() == playerId)
        .findFirst();
  }
}
