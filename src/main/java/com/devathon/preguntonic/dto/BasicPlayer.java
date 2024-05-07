/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.devathon.preguntonic.model.Player;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Builder;

@Builder
public record BasicPlayer(
    @JsonProperty("playerId") UUID id,
    @JsonProperty("playerName") String name,
    @JsonProperty String avatar) {

  public static BasicPlayer from(final Player player) {
    return BasicPlayer.builder()
        .id(player.getId())
        .name(player.getName())
        .avatar(player.getAvatar())
        .build();
  }
}
