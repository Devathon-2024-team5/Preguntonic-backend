/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player {
  @JsonProperty("playerId")
  Integer id;

  @JsonProperty("playerName")
  String name;

  @JsonProperty("avatar")
  String avatarId;

  @JsonProperty("isReady")
  boolean ready;

  @JsonProperty("isOwner")
  boolean owner;

  @JsonProperty("score")
  Integer score;
}
