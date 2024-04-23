package com.devathon.preguntonic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlayerResponse (
    @JsonProperty("player_id") String playerId,
    @JsonProperty("question_id") String questionId,
    @JsonProperty("answer_is") String answerId,
    @JsonProperty("time") String time
) { }
