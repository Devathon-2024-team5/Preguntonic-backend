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
    @JsonProperty("player_id") Integer id,
    @JsonProperty("player_name") String name,
    @JsonProperty("avatar_id") String avatarId) {}
