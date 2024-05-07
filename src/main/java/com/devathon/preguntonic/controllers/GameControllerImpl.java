/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.domain.Question;
import com.devathon.preguntonic.dto.GameStatusDto;
import com.devathon.preguntonic.dto.PlayerQuestionResponseDto;
import com.devathon.preguntonic.dto.PlayerStatusDto;
import com.devathon.preguntonic.dto.QuestionDto;
import com.devathon.preguntonic.dto.QuestionResultDto;
import com.devathon.preguntonic.model.Game;
import com.devathon.preguntonic.model.GameStatus;
import com.devathon.preguntonic.model.Player;
import com.devathon.preguntonic.model.PlayerStatus;
import com.devathon.preguntonic.model.Room;
import com.devathon.preguntonic.model.RoomStatus;
import com.devathon.preguntonic.services.QuestionService;
import com.devathon.preguntonic.services.RoomService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameControllerImpl implements GameController {

  private static final int FIRST_QUESTION_ORDINAL = 1;
  private static final long MAX_MILLISECONDS_TO_ANSWER = 30000;
  private static final int QUESTION_SCORE = 100;

  private final RoomService roomService;

  private final QuestionService questionService;

  @Override
  public void gameSubscription(final String code) {

    // Check if the room exists
    Optional<Room> optRoom = roomService.getRoom(code);
    if (optRoom.isEmpty()) {
      log.error("Room {} not found", code);
      return;
    }

    log.info("Game subscription for room {}", code);
  }

  @Override
  public GameStatusDto joinGame(final String code, final UUID playerId) {

    // Check if the room exists
    Optional<Room> optRoom = roomService.getRoom(code);
    if (optRoom.isEmpty()) {
      log.error("Room {} not found", code);
      return null;
    }

    Room room = optRoom.get();

    // Check if the player is already in the room
    if (!roomService.roomContainsPlayer(code, playerId)) {
      log.error("Player {} is not in the room {}", playerId, code);
      return null;
    }

    Player player = roomService.getPlayer(code, playerId).get();

    // Check if player is already joined
    if (player.getStatus() == PlayerStatus.IN_GAME) {
      log.error("Player {} is already in the game", playerId);
      return null;
    }

    player.setStatus(PlayerStatus.IN_GAME);

    // Check if all players are ready to start the game
    boolean allReadyPlayersJoined =
        room.getPlayers().stream().allMatch(p -> p.getStatus() == PlayerStatus.IN_GAME);

    if (!allReadyPlayersJoined) {
      return null;
    }

    // Get first question
    Question question = questionService.getRandomQuestion();

    // Create game
    room.setGame(
        Game.builder()
            .id(UUID.randomUUID())
            .currentQuestion(question)
            .currentQuestionOrdinal(FIRST_QUESTION_ORDINAL)
            .status(GameStatus.IN_GAME)
            .createdAt(LocalDateTime.now())
            .build());

    log.info("All players joined the game in room {}", code);

    return GameStatusDto.from(room);
  }

  @Override
  public QuestionResultDto answerQuestion(
      final String code, final UUID playerId, final PlayerQuestionResponseDto response) {

    // Check if the room exists
    Optional<Room> optRoom = roomService.getRoom(code);
    if (optRoom.isEmpty()) {
      log.error("Room {} not found", code);
      return null;
    }

    Room room = optRoom.get();

    // Check if the player is already in the room
    if (!roomService.roomContainsPlayer(code, playerId)) {
      log.error("Player {} is not in the room {}", playerId, code);
      return null;
    }

    // Check if DTO is valid
    if (response == null
        || response.questionId() == null
        || (response.responseId() == null && !response.timeout())
        || response.milliseconds() == 0) {
      log.error("Response is not valid");
      return null;
    }

    // Check if the timestamp is valid
    if (response.milliseconds() > MAX_MILLISECONDS_TO_ANSWER || response.milliseconds() < 0) {
      log.error("Response time is not valid");
      return null;
    }

    // Check if the question exists and it is valid
    Optional<Question> optQuestion = questionService.getQuestionById(response.questionId());
    if (optQuestion.isEmpty()) {
      log.error("Question {} not found", response.questionId());
      return null;
    }
    if (!response.questionId().equals(room.getGame().getCurrentQuestion().getId())) {
      log.error("Question {} is not the current question", response.questionId());
      return null;
    }

    Question question = optQuestion.get();

    if (!response.timeout()
        && question.getAnswers().stream().noneMatch(a -> a.getId().equals(response.responseId()))) {
      log.error("Response {} not found in question {}", response.responseId(), question.getId());
      return null;
    }

    // Response
    Player player = roomService.getPlayer(code, playerId).get();
    player.response(response.responseId(), response.milliseconds(), response.timeout());

    boolean allPlayersAnswered = room.getPlayers().stream().allMatch(p -> p.isResponded());

    if (!allPlayersAnswered) {
      return null;
    }

    List<Player> players = room.getPlayers().stream().toList();
    List<Player> playersAnswered = players.stream().filter(p -> p.isResponded()).toList();
    List<Player> orderedPlayers =
        playersAnswered.stream()
            .sorted((p1, p2) -> (int) (p1.getResponseTime() - p2.getResponseTime()))
            .toList();

    int numPlayers = room.getPlayers().size();

    for (int i = 0; i < orderedPlayers.size(); i++) {

      Player currentPlayer = orderedPlayers.get(i);
      int newScore = 0;
      if (!currentPlayer.isTimeout()
          && currentPlayer.getResponseId().equals(question.getCorrectAnswer().getId())) {
        // int score = QUESTION_SCORE * ((numPlayers - i) / numPlayers) + (int)
        // (MAX_MILLISECONDS_TO_ANSWER - currentPlayer.getResponseTime());
        newScore = (int) (QUESTION_SCORE * (((float) numPlayers - i) / (float) numPlayers));
      } else {
        newScore = 0;
      }
      currentPlayer.setScore(currentPlayer.getScore() + newScore);

      orderedPlayers.get(i).resetResponse();
    }

    log.info("All players answered the question in room {}", code);
    return QuestionResultDto.builder()
        .question(QuestionDto.from(question, room.getGame().getCurrentQuestionOrdinal()))
        .players(PlayerStatusDto.from(room.getPlayers().stream().toList()))
        .correctAnswerId(question.getCorrectAnswer().getId())
        .correctAnswer(question.getCorrectAnswer().getAnswer())
        .build();
  }

  @Override
  public GameStatusDto nextQuestion(final String code, final UUID playerId) {

    // Check if the room exists
    Optional<Room> optRoom = roomService.getRoom(code);
    if (optRoom.isEmpty()) {
      log.error("Room {} not found", code);
      return null;
    }

    Room room = optRoom.get();

    // Check if the player is in the room
    if (!roomService.roomContainsPlayer(code, playerId)) {
      log.error("Player {} is not in the room {}", playerId, code);
      return null;
    }

    Player player = roomService.getPlayer(code, playerId).get();
    player.setReadyForNextQuestion(true);

    // Check if all players are ready to next question
    boolean allReadyPlayersNext =
        room.getPlayers().stream().allMatch(Player::isReadyForNextQuestion);

    if (!allReadyPlayersNext) {
      return null;
    }

    log.info("All players are ready for the next question in room {}", code);
    room.getPlayers().forEach(p -> p.setReadyForNextQuestion(false));

    // Check if there are more questions
    boolean thereAreMoreQuestions =
        room.getGame().getCurrentQuestionOrdinal() < room.getNumQuestions();

    if (thereAreMoreQuestions) {

      log.info("Sending next question in room {}", code);

      // Get next question // TODO: Comprobar que no se repitan preguntas
      Question question = questionService.getRandomQuestion();
      room.getGame().setCurrentQuestion(question);
      room.getGame().setCurrentQuestionOrdinal(room.getGame().getCurrentQuestionOrdinal() + 1);

      return GameStatusDto.builder()
          .players(PlayerStatusDto.from(room.getPlayers().stream().toList()))
          .currentQuestion(QuestionDto.from(question, room.getGame().getCurrentQuestionOrdinal()))
          .numQuestions(room.getNumQuestions())
          .status(GameStatus.IN_GAME)
          .build();
    } else {

      log.info("There are no more questions in room {}", code);

      room.getPlayers().forEach(p -> p.setStatus(PlayerStatus.IN_RESULTS));
      room.setStatus(RoomStatus.FINISHED);

      return GameStatusDto.builder()
          .players(PlayerStatusDto.from(room.getPlayers().stream().toList()))
          .currentQuestion(null)
          .numQuestions(room.getNumQuestions())
          .status(GameStatus.FINISHED)
          .build();
    }
  }

  @Override
  public GameStatusDto exitGame(final String code, final UUID playerId) {

    // Check if the room exists
    Optional<Room> optRoom = roomService.getRoom(code);
    if (optRoom.isEmpty()) {
      log.error("Room {} not found", code);
      return null;
    }

    Room room = optRoom.get();

    // Check if the player is in the room
    if (!roomService.roomContainsPlayer(code, playerId)) {
      log.error("Player {} is not in the room {}", playerId, code);
      return null;
    }

    Player player = roomService.getPlayer(code, playerId).get();

    room.removePlayer(player.getId());
    log.info("Player {} left the game in room {}", playerId, code);

    // TODO: Disconnect player from WebSocket

    return GameStatusDto.from(room);
  }

  @Override
  public String replayGame(final String code, final UUID playerId) {

    // Check if the room exists
    Optional<Room> optRoom = roomService.getRoom(code);
    if (optRoom.isEmpty()) {
      log.error("Room {} not found", code);
      return null;
    }

    Room room = optRoom.get();

    // Check if the player is in the room
    if (!roomService.roomContainsPlayer(code, playerId)) {
      log.error("Player {} is not in the room {}", playerId, code);
      return null;
    }

    Player player = roomService.getPlayer(code, playerId).get();

    // Check if the player is in the results
    if (player.getStatus() != PlayerStatus.IN_RESULTS) {
      log.error("Player {} is not in the results", playerId);
      return null;
    }

    // Reset score // TODO: Move this logic to RoomService (method: resetGame)
    player.setScore(0);
    player.setStatus(PlayerStatus.IN_LOBBY_UNREADY);
    player.resetResponse();
    player.setReadyForNextQuestion(false);

    roomService.resetGame(code);

    return "replay"; // TODO: Change to a DTO
  }
}
