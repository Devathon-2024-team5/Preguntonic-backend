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

public record LobbyStatusDto(
    @JsonProperty("current_players") List<Player> players,
    @JsonProperty("ready_players") int readyPlayers,
    @JsonProperty("max_players") int maxPlayers,
    @JsonProperty("room_code") String roomCode,
    @JsonProperty("room_status") RoomStatus status) {

  /**
   * Create a LobbyStatusDto from a Room
   *
   * @param room the room
   * @return the LobbyStatusDto
   */
  public static LobbyStatusDto from(final Room room) {
    var readyPlayers = room.countReadyPlayers();
    var roomStatus = calculateRoomStatus(room, readyPlayers);
    return new LobbyStatusDto(
        room.getPlayers().values().stream().toList(),
        readyPlayers,
        room.getMaxPlayers(),
        room.getCode(),
        roomStatus);
  }

  private static RoomStatus calculateRoomStatus(final Room room, final int readyPlayers) {
    if (room.getStatus() == RoomStatus.WAITING && readyPlayers == room.getMaxPlayers()) {
      return RoomStatus.READY;
    }
    return room.getStatus();
  }
}
