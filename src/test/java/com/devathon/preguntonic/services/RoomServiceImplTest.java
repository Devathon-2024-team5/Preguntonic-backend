/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomServiceImplTest {

  private RoomServiceImpl roomService;

  @BeforeEach
  void setUp() {
    roomService = new RoomServiceImpl();
  }

  @Test
  void createRoom() {
    roomService.createRoom(null);
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
