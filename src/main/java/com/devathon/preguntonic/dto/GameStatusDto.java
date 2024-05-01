package com.devathon.preguntonic.dto;

import java.util.List;

import com.devathon.preguntonic.model.GameStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record GameStatusDto(
    @JsonProperty("players") List<PlayerStatusDto> players,
    @JsonProperty("current_question") QuestionDto currentQuestion,
    @JsonProperty("num_questions") int numQuestions,
    @JsonProperty("status") GameStatus status
) {}
