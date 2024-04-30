/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.devathon.preguntonic.model.RoomStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class RoomStatusDtoTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void serializeBasicObject() throws Exception {
    var roomStatusDto = new LobbyStatusDto(null, 0, 0, "roomCode", RoomStatus.IN_GAME);
    var expected =
        "{\"current_players\":null,\"room_code\":\"roomCode\",\"room_status\":\"IN_GAME\",\"readyPlayers\":0,\"maxPlayers\":0}";

    var serialized = objectMapper.writeValueAsString(roomStatusDto);

    assertEquals(expected, serialized);
  }

  @Test
  void deserialize() throws Exception {
    var json =
        "{\"current_players\":null,\"readyPlayers\":0,\"maxPlayers\":0,\"room_code\":\"roomCode\",\"room_status\":\"WAITING\"}";
    var expected = new LobbyStatusDto(null, 0, 0, "roomCode", RoomStatus.WAITING);

    var deserialized = objectMapper.readValue(json, LobbyStatusDto.class);

    assertEquals(expected, deserialized);
  }
}
