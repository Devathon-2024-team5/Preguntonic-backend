package com.devathon.preguntonic.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlayerQuestionResponseDto(
    @JsonProperty("question_id") UUID questionId,
    @JsonProperty("response_id") UUID responseId,
    @JsonProperty("milliseconds") long milliseconds
) {}
