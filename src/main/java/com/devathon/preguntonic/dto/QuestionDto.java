/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import com.devathon.preguntonic.domain.Question;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("question")
  private String question;

  @JsonProperty("answers")
  private List<AnswerDto> answers;

  @JsonProperty("ordinal")
  private int ordinal;

  public static QuestionDto from(Question question, int ordinal) {
    return QuestionDto.builder()
        .id(question.getId())
        .question(question.getQuestion())
        .answers(AnswerDto.from(question.getAnswers()))
        .ordinal(ordinal)
        .build();
  }
}
