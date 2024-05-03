/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Builder;

@Builder
public record BasicPlayer(
    @JsonProperty("playerId") UUID id,
    @JsonProperty("playerName") String name,
    @JsonProperty String avatar) {}
