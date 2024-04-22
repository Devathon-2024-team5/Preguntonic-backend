/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RoomPlayerInitResponse(
    @JsonProperty("room_code") String roomCode, @JsonProperty("player_id") int playerId) {}
