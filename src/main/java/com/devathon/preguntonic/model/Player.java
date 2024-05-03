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
  @JsonProperty("player_id")
  Integer id;

  @JsonProperty("player_name")
  String name;

  @JsonProperty("avatar_id")
  String avatarId;

  @JsonProperty("is_ready")
  boolean ready;

  @JsonProperty("is_owner")
  boolean owner;

  @JsonProperty("score")
  Integer score;
}
