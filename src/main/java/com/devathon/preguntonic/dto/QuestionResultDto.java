package com.devathon.preguntonic.dto;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record QuestionResultDto(
    @JsonProperty("players") List<PlayerStatusDto> players,
    @JsonProperty("question") QuestionDto question,
    @JsonProperty("correct_answer_id") UUID correctAnswerId
) {}
