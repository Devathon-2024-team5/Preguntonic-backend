/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.devathon.preguntonic.dto.RoomConfiguration;
import com.devathon.preguntonic.model.Room;
import com.devathon.preguntonic.namegenerator.PrefixedRandomRoomCodeGenerator;
import com.devathon.preguntonic.namegenerator.RoomCodeGenerator;
import com.devathon.preguntonic.storage.MemoryRoomStorage;
import com.devathon.preguntonic.storage.RoomStorage;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomServiceImplTest {

  private RoomServiceImpl roomService;
  private RoomStorage roomStorage;
  private RoomCodeGenerator roomCodeGenerator;

  @BeforeEach
  void setUp() {
    roomStorage = MemoryRoomStorage.builder().rooms(new HashMap<>()).build();
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
  void getRooms() {}

  @Test
  void getRoom() {}

  @Test
  void joinRoom() {}

  @Test
  void changePlayerReadyStatus() {}

  @Test
  void getPlayer() {}

  @Test
  void getGame() {}

  @Test
  void createGame() {}
}
