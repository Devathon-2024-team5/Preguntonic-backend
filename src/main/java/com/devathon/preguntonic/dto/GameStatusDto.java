/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.devathon.preguntonic.model.GameStatus;
import com.devathon.preguntonic.model.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record GameStatusDto(
    @JsonProperty("players") List<PlayerStatusDto> players,
    @JsonProperty("current_question") QuestionDto currentQuestion,
    @JsonProperty("num_questions") int numQuestions,
    @JsonProperty("status") GameStatus status) {

  public static GameStatusDto from(Room room) {
    return GameStatusDto.builder()
        .players(PlayerStatusDto.from(room.getPlayers()))
        .currentQuestion(
            QuestionDto.from(
                room.getGame().getCurrentQuestion(), room.getGame().getCurrentQuestionOrdinal()))
        .numQuestions(room.getNumQuestions())
        .status(room.getGame().getStatus())
        .build();
  }
}
