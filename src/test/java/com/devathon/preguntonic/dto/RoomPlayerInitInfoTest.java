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
    var roomPlayerInitInfo = new RoomPlayerInitInfo(4, 10, "playerName", 1);
    var expected =
        "{\"max_players\":4,\"num_of_question\":10,\"player_name\":\"playerName\",\"avatar_id\":1}";

    var serialized = objectMapper.writeValueAsString(roomPlayerInitInfo);
    assertEquals(expected, serialized);
  }

  @Test
  void deserialize() throws JsonProcessingException {
    var json =
        "{\"max_players\":4,\"num_of_question\":10,\"player_name\":\"playerName\",\"avatar_id\":1}";
    var expected = new RoomPlayerInitInfo(4, 10, "playerName", 1);

    var deserialized = objectMapper.readValue(json, RoomPlayerInitInfo.class);
    assertEquals(expected, deserialized);
  }
}
