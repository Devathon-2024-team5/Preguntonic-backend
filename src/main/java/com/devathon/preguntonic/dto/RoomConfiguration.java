/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public record RoomConfiguration(
    @JsonProperty("max_players") int maxPlayers,
    @JsonProperty("num_of_question") int numberOfQuestions) {}
