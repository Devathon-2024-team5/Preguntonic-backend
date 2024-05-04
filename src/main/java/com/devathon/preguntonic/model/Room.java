/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
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

  @Getter(AccessLevel.NONE)
  @Builder.Default
  private Map<UUID, Player> players = new HashMap<>(Map.of());

  public int countReadyPlayers() {
    if (players == null) {
      return 0;
    }
    return (int)
        players.values().stream()
            .filter(player -> player.getStatus() == PlayerStatus.IN_LOBBY_READY)
            .count();
  }

  public Optional<Player> getPlayer(final UUID playerId) {
    return Optional.ofNullable(players.get(playerId));
  }

  public Player addPlayer(final Player player) {
    players.put(player.getId(), player);
    return player;
  }

  public Player removePlayer(final UUID playerId) {
    return players.remove(playerId);
  }

  /**
   * Get the players in the room
   *
   * @return the players in the room
   */
  public List<Player> getPlayers() {
    return List.copyOf(players.values());
  }

  public void cleanUp() {
    players.clear();
  }
}
