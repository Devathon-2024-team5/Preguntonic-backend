/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class RoomPlayerInitResponseTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void serialize() throws JsonProcessingException {
    var roomPlayerInitResponse =
        RoomPlayerInitResponse.builder().roomCode("roomCode").playerId(1).build();
    var expected = "{\"room_code\":\"roomCode\",\"player_id\":1}";

    var serialized = objectMapper.writeValueAsString(roomPlayerInitResponse);

    assertEquals(expected, serialized);
  }

  @Test
  void deserialize() throws JsonProcessingException {
    var json = "{\"room_code\":\"roomCode\",\"player_id\":1}";
    var expected = RoomPlayerInitResponse.builder().roomCode("roomCode").playerId(1).build();

    var deserialized = objectMapper.readValue(json, RoomPlayerInitResponse.class);

    assertEquals(expected, deserialized);
  }
}
