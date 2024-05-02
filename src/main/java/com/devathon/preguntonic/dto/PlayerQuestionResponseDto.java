/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record PlayerQuestionResponseDto(
    @JsonProperty("question_id") UUID questionId,
    @JsonProperty("response_id") UUID responseId,
    @JsonProperty("milliseconds") long milliseconds) {}
