/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.model;

import com.devathon.preguntonic.domain.Question;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

  private UUID id;
  private Question currentQuestion;
  private int currentQuestionOrdinal;
  private LocalDateTime createdAt;
  private GameStatus status;
}
