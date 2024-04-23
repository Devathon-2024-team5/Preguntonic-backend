package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.dto.PlayerResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/rooms")
public interface GameController {

  @GetMapping("/{roomId}/games/{gameId}")
  ResponseEntity<String> getGameStatus(@PathVariable String roomId, @PathVariable String gameId);

  @GetMapping("/{roomId}/games/{gameId}/questions")
  ResponseEntity<List<String>> getGameQuestions(@PathVariable String roomId, @PathVariable String gameId);

  @MessageMapping("/rooms/{roomId}/games.response/{gameId}/questions/{questionId}")
  ResponseEntity<String> handleQuestion(
      @DestinationVariable String roomId,
      @DestinationVariable String gameId,
      @DestinationVariable String questionId,
      PlayerResponse playerResponse);
}
