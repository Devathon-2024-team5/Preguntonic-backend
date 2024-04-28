/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.devathon.preguntonic.model.Player;
import com.devathon.preguntonic.model.Room;
import com.devathon.preguntonic.model.RoomStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RoomStatusDto(
    @JsonProperty("current_players") List<Player> players,
    @JsonProperty("ready_players") int readyPlayers,
    @JsonProperty("max_players") int maxPlayers,
    @JsonProperty("room_code") String roomCode,
    @JsonProperty("room_status") RoomStatus status) {

  public static RoomStatusDto from(final Room room) {
    return new RoomStatusDto(
        room.getPlayers(),
        room.countReadyPlayers(),
        room.getMaxPlayers(),
        room.getCode(),
        room.getStatus());
  }
}
