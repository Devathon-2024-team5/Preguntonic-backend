/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record QuestionResultDto(
    @JsonProperty("players") List<PlayerStatusDto> players,
    @JsonProperty("question") QuestionDto question,
    @JsonProperty("correct_answer_id") UUID correctAnswerId,
    @JsonProperty("correct_answer") String correctAnswer) {}
