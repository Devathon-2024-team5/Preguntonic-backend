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
  @SendTo("/room/{code}/game")
  GameStatusDto joinGame(
      @DestinationVariable("code") String code, @DestinationVariable("playerId") UUID playerId);

  /**
   * Players send when they answer a question
   *
   * @param code Code of the room
   * @return QuestionResultDto if all players answered, null otherwise
   */
  @MessageMapping("/players/{playerId}/response")
  @SendTo("/room/{code}/questions")
  QuestionResultDto answerQuestion(
      @DestinationVariable("code") String code,
      @DestinationVariable("playerId") UUID playerId,
      @Payload PlayerQuestionResponseDto response);

  /**
   * Handle when a player is ready to start the game
   *
   * @param code Code of the room
   * @param playerId id of the player
   * @return GameStatusDto with the updated players
   */
  @MessageMapping("/players/{playerId}/next")
  @SendTo("/room/{code}/game")
  GameStatusDto nextQuestion(
      @DestinationVariable("code") String code, @DestinationVariable("playerId") UUID playerId);

  /**
   * Players send when they leave the game
   *
   * @param code Code of the room
   * @param playerId id of the player
   * @return GameStatusDto with the updated players
   */
  @MessageMapping("/players/{playerId}/exit")
  GameStatusDto exitGame(
      @DestinationVariable("code") String code, @DestinationVariable("playerId") UUID playerId);

  /**
   * Players send when they are ready to re-start the game
   *
   * @param code Code of the room
   * @param playerId id of the player
   * @return
   */
  @MessageMapping("/players/{playerId}/replay")
  String replayGame(
      @DestinationVariable("code") String code, @DestinationVariable("playerId") UUID playerId);
}
