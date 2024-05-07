/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.devathon.preguntonic.dto.BasicPlayer;
import com.devathon.preguntonic.dto.RoomConfiguration;
import com.devathon.preguntonic.model.Player;
import com.devathon.preguntonic.model.PlayerStatus;
import com.devathon.preguntonic.model.Room;
import com.devathon.preguntonic.namegenerator.PrefixedRandomRoomCodeGenerator;
import com.devathon.preguntonic.namegenerator.RoomCodeGenerator;
import com.devathon.preguntonic.storage.MemoryRoomStorage;
import com.devathon.preguntonic.storage.RoomStorage;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomServiceImplIntegrationTest {

  private RoomServiceImpl roomService;
  private RoomStorage roomStorage;
  private RoomCodeGenerator roomCodeGenerator;

  @BeforeEach
  void setUp() {
    roomStorage = MemoryRoomStorage.builder().build();
    roomCodeGenerator = new PrefixedRandomRoomCodeGenerator("ROOM");
    roomService = new RoomServiceImpl(roomStorage, roomCodeGenerator);
  }

  @Test
  void createRoom() {
    Room room =
        roomService.createRoom(
            RoomConfiguration.builder().maxPlayers(4).numberOfQuestions(10).build());
    assertNotNull(room);
    assertNotNull(room.getCode());
    assertEquals(4, room.getMaxPlayers());
    assertEquals(10, room.getNumQuestions());
    assertTrue(room.getCode().startsWith("ROOM"));
  }

  @Test
  void getRooms() {
    Room room1 =
        roomService.createRoom(
            RoomConfiguration.builder().maxPlayers(4).numberOfQuestions(10).build());
    Room room2 =
        roomService.createRoom(
            RoomConfiguration.builder().maxPlayers(4).numberOfQuestions(10).build());

    var rooms = roomService.getRooms();
    assertEquals(2, rooms.size());
    assertTrue(rooms.contains(room1));
    assertTrue(rooms.contains(room2));
  }

  @Test
  void getRoom() {
    Room room1 =
        roomService.createRoom(
            RoomConfiguration.builder().maxPlayers(4).numberOfQuestions(10).build());
    Room room2 =
        roomService.createRoom(
            RoomConfiguration.builder().maxPlayers(4).numberOfQuestions(10).build());

    Room room3 =
        roomService.createRoom(
            RoomConfiguration.builder().maxPlayers(2).numberOfQuestions(5).build());

    var room = roomService.getRoom(room3.getCode());
    assertTrue(room.isPresent());
    assertEquals(room3, room.get());
  }

  @Test
  void joinRoom() {
    Room room =
        roomService.createRoom(
            RoomConfiguration.builder().maxPlayers(4).numberOfQuestions(10).build());
    BasicPlayer basicPlayer = BasicPlayer.builder().name("playerName").avatar("1").build();
    BasicPlayer playerStored = roomService.addRoom(room.getCode(), basicPlayer);

    roomService.joinRoom(room.getCode(), playerStored);
    var player = roomService.getPlayer(room.getCode(), playerStored.id());
    assertNotNull(player);
    assertTrue(player.isPresent());
    assertEquals("playerName", player.get().getName());
    assertEquals("1", player.get().getAvatar());
  }

  @Test
  void changePlayerReadyStatus() {
    Room room =
        roomService.createRoom(
            RoomConfiguration.builder().maxPlayers(4).numberOfQuestions(10).build());
    BasicPlayer basicPlayer = BasicPlayer.builder().name("playerName").avatar("1").build();
    BasicPlayer playerStored = roomService.addRoom(room.getCode(), basicPlayer);
    roomService.joinRoom(room.getCode(), playerStored);

    roomService
        .getPlayersInRoom(room.getCode())
        .forEach(
            player -> {
              assertEquals(PlayerStatus.IN_LOBBY_UNREADY, player.getStatus());
            });

    roomService.changePlayerReadyStatus(
        room.getCode(), playerStored.id(), PlayerStatus.IN_LOBBY_READY);

    Optional<Player> player = roomService.getPlayer(room.getCode(), playerStored.id());
    assertTrue(player.isPresent());
    assertEquals(PlayerStatus.IN_LOBBY_READY, player.get().getStatus());
  }
}
