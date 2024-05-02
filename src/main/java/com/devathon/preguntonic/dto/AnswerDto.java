/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.devathon.preguntonic.domain.Answer;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public class AnswerDto {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("answer")
  private String answer;

  public static AnswerDto from(Answer answer) {
    return AnswerDto.builder().id(answer.getId()).answer(answer.getAnswer()).build();
  }

  public static List<AnswerDto> from(List<Answer> answers) {
    return answers.stream().map(AnswerDto::from).toList();
  }
}
