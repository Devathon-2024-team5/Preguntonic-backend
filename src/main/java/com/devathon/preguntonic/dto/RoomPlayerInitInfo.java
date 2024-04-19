/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomPlayerInitInfo(
    @JsonProperty("max_players") int maxPlayers,
    @JsonProperty("num_of_question") int numberOfQuestions,
    @JsonProperty("player_name") String playerName,
    @JsonProperty("avatar_id") String playerAvatarId) {}
