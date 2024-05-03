/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

  private UUID id;
  private String code;
  private RoomStatus status;
  private Game game;
  private int maxPlayers;
  private int currentPlayers;
  private int numQuestions;
  private LocalDateTime createdAt;
  @Builder.Default private final Map<UUID, Player> players = Map.of();

  public int countReadyPlayers() {
    if (players == null) {
      return 0;
    }
    return (int)
        players.values().stream()
            .filter(player -> player.getStatus() == PlayerStatus.IN_LOBBY_READY)
            .count();
  }
}
