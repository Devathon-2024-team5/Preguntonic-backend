/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.dto.GameStatusDto;
import com.devathon.preguntonic.dto.PlayerQuestionResponseDto;
import com.devathon.preguntonic.dto.QuestionResultDto;
import java.util.UUID;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;

@MessageMapping("/rooms/{code}/game")
public interface GameController {

  /**
   * Handle players subscription to the game
   *
   * @param code Code of the room
   */
  @SubscribeMapping
  void gameSubscription(@DestinationVariable String code);

  /**
   * Players send when they join the game
   *
   * @param code Code of the room
   * @return GameStatusDto with the updated players
   */
  @MessageMapping("/players/{playerId}/join")
  @SendTo("/preguntonic/rooms/{code}/game")
  public GameStatusDto joinGame(
      @DestinationVariable("code") String code, @DestinationVariable("playerId") UUID playerId);

  /**
   * Players send when they answer a question
   *
   * @param code Code of the room
   * @return QuestionResultDto if all players answered, null otherwise
   */
  @MessageMapping("/players/{playerId}/response")
  @SendTo("/preguntonic/rooms/{code}/game")
  public QuestionResultDto answerQuestion(
      @DestinationVariable("code") String code,
      @DestinationVariable("playerId") UUID playerId,
      @Payload PlayerQuestionResponseDto response);

  /**
   * Handle when a player is ready to start the game
   *
   * @param code Code of the room
   * @param playerId Id of the player
   * @return GameStatusDto with the updated players
   */
  @MessageMapping("/players/{playerId}/next")
  @SendTo("/preguntonic/rooms/{code}/game")
  public GameStatusDto nextQuestion(
      @DestinationVariable String code, @DestinationVariable UUID playerId);

  /**
   * Players send when they leave the game
   *
   * @param code Code of the room
   * @param playerId Id of the player
   * @return
   */
  @MessageMapping("/players/{playerId}/exit")
  public String exitGame(@DestinationVariable String code, @DestinationVariable String playerId);

  /**
   * Players send when they are ready to re-start the game
   *
   * @param code Code of the room
   * @param playerId Id of the player
   * @return
   */
  @MessageMapping("/players/{playerId}/replay")
  public String replayGame(@DestinationVariable String code, @DestinationVariable String playerId);
}
