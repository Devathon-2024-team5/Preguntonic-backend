/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.devathon.preguntonic.model.RoomEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record LobbyEvent(
    @JsonProperty("room") LobbyStatusDto roomStatus, @JsonProperty RoomEvent event) {}
