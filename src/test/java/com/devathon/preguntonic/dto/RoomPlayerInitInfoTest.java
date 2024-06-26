/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class RoomPlayerInitInfoTest {
  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void serialize() throws JsonProcessingException {
    var roomPlayerInitInfo = new RoomPlayerInitInfo(4, 10, "playerName", "1");
    var expected =
        "{\"playerName\":\"playerName\",\"avatar\":\"1\",\"maxPlayers\":4,\"numberOfQuestions\":10}";

    var serialized = objectMapper.writeValueAsString(roomPlayerInitInfo);
    assertEquals(expected, serialized);
  }

  @Test
  void deserialize() throws JsonProcessingException {
    var json =
        "{\"playerName\":\"playerName\",\"avatar\":\"1\",\"maxPlayers\":4,\"numberOfQuestions\":10}";
    var expected = new RoomPlayerInitInfo(4, 10, "playerName", "1");

    var deserialized = objectMapper.readValue(json, RoomPlayerInitInfo.class);
    assertEquals(expected, deserialized);
  }
}
