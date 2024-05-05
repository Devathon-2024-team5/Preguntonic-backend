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
import com.devathon.preguntonic.namegenerator.RoomCodeGenerator;
import com.devathon.preguntonic.storage.RoomStorage;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

  private RoomStorage roomStorage;
  private RoomCodeGenerator roomCodeGenerator;

  @Override
  public Room createRoom(final RoomConfiguration roomConfiguration) {
    if (Objects.isNull(roomConfiguration)) {
      throw new InvalidParameterException("Room configuration is required");
    }
    Room room =
        Room.builder()
            .code(roomCodeGenerator.generateCode())
            .maxPlayers(roomConfiguration.maxPlayers())
            .numQuestions(roomConfiguration.numberOfQuestions())
            .build();
    roomStorage.saveRoom(room);
    return room;
  }

  @Override
  public List<Room> getRooms() {
    return roomStorage.getRooms();
  }

  @Override
  public Optional<Room> getRoom(final String roomCode) {
    return roomStorage.getRoom(roomCode);
  }

  @Override
  public BasicPlayer joinRoom(final String roomCode, final BasicPlayer playerInfo)
      throws InvalidParameterException {
    if (Objects.isNull(playerInfo) || Objects.isNull(playerInfo.id())) {
      throw new InvalidParameterException("Player info is required");
    }
    if (!roomContainsPlayer(roomCode, playerInfo.id())) {
      throw new InvalidParameterException("Room or player does not exist");
    }

    Optional<Player> playerO = roomStorage.getPlayerFromRoom(roomCode, playerInfo.id());

    playerO.ifPresent(
        player -> {
          player.setStatus(PlayerStatus.IN_LOBBY_UNREADY);
          roomStorage.updatePlayerInRoom(roomCode, player);
        });

    return playerO.map(BasicPlayer::from).orElse(null);
  }

  @Override
  public BasicPlayer addRoom(final String roomCode, final BasicPlayer playerInfo)
      throws InvalidParameterException {

    if (roomContainsPlayer(roomCode, playerInfo.id())) {
      log.info("Player {} already in room {}", playerInfo.name(), roomCode);
      return playerInfo;
    }

    Player addedPlayer =
        roomStorage
            .addPlayerToRoom(roomCode, buildNewPlayer(playerInfo))
            .orElseThrow(() -> new InvalidParameterException("Player could not be added to room"));
    return BasicPlayer.from(addedPlayer);
  }

  @Override
  public boolean roomContainsPlayer(final String roomCode, final UUID playerId) {
    return Objects.nonNull(playerId) && roomStorage.existsPlayerInRoom(roomCode, playerId);
  }

  private static Player buildNewPlayer(final BasicPlayer playerInfo) {
    final UUID playerId = Optional.ofNullable(playerInfo.id()).orElse(UUID.randomUUID());

    return Player.builder()
        .id(playerId)
        .name(playerInfo.name())
        .avatar(playerInfo.avatar())
        .status(PlayerStatus.CONNECTING)
        .build();
  }

  @Override
  public BasicPlayer changePlayerReadyStatus(
      final String roomCode, final UUID playerId, final PlayerStatus readyStatus)
      throws InvalidParameterException {
    Optional<Player> playerFromRoom = roomStorage.getPlayerFromRoom(roomCode, playerId);

    playerFromRoom.ifPresentOrElse(
        player -> {
          player.setStatus(readyStatus);
          roomStorage.updatePlayerInRoom(roomCode, player);
        },
        () -> {
          throw new InvalidParameterException("Player not found in room");
        });

    return playerFromRoom.map(BasicPlayer::from).orElse(null);
  }

  @Override
  public Optional<Player> getPlayer(final String roomCode, final UUID playerId) {
    return roomStorage.getPlayerFromRoom(roomCode, playerId);
  }

  @Override
  public List<Player> getPlayersInRoom(final String roomCode) {
    return roomStorage.getPlayersFromRoom(roomCode);
  }

  @Override
  public Optional<BasicPlayer> getBasicPlayer(final String roomCode, final UUID playerId) {
    return this.getPlayer(roomCode, playerId).map(BasicPlayer::from);
  }

  @Override
  public Optional<Game> getGame(final String roomCode) {
    return roomStorage.getRoom(roomCode).map(Room::getGame);
  }

  @Override
  public Game createGame(final String roomCode) throws InvalidParameterException {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void resetGame(final String roomCode) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
