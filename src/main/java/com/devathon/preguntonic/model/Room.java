/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
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
  private int numQuestions;
  private LocalDateTime createdAt;
  private final Map<UUID, Player> players = Map.of();

  public Optional<Player> getPlayer(final UUID playerId) {
    return Optional.ofNullable(players.get(playerId));
  }

  public Player addPlayer(final Player player) {
    players.put(player.getId(), player);
    return player;
  }

  public void removePlayer(final UUID playerId) {
    players.remove(playerId);
  }

  public void cleanUp() {
    players.clear();
  }
}
