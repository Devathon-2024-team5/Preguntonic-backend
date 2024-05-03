/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record BasicPlayer(
    @JsonProperty("playerId") Integer id,
    @JsonProperty("playerName") String name,
    @JsonProperty String avatar) {}
