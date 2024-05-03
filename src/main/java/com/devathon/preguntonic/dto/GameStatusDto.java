/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.devathon.preguntonic.model.GameStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record GameStatusDto(
    @JsonProperty("players") List<PlayerStatusDto> players,
    @JsonProperty("current_question") QuestionDto currentQuestion,
    @JsonProperty("num_questions") int numQuestions,
    @JsonProperty("status") GameStatus status) {}
