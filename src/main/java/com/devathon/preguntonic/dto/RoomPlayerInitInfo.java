/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomPlayerInitInfo(
    @JsonProperty int maxPlayers,
    @JsonProperty int numberOfQuestions,
    @JsonProperty("playerName") String playerName,
    @JsonProperty("avatar") String playerAvatarId) {}
