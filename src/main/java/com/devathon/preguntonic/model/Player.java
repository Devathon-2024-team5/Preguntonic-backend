/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

  @JsonProperty("playerId")
  private UUID id;

  private boolean admin;
  private String ipAddress;
  private String avatar;

  @JsonProperty("playerName")
  private String name;

  private PlayerStatus status;
  private int score;

  // Transient (game)
  private boolean responded;
  private long responseTime;
  private UUID responseId;
  private boolean timeout;
  private boolean readyForNextQuestion;

  public void response(UUID responseId, long responseTime, boolean timeout) {
    this.responseId = responseId;
    this.responseTime = responseTime;
    this.timeout = timeout;
    this.responded = true;
  }

  public void resetResponse() {
    this.responseId = null;
    this.responseTime = -1;
    this.responded = false;
    this.timeout = false;
  }
}
