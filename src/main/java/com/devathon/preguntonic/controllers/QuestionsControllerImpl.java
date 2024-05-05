/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.domain.Question;
import com.devathon.preguntonic.services.QuestionService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionsControllerImpl implements QuestionsController {

  private final QuestionService questionService;

  @Override
  public ResponseEntity<Question> createQuestion(final Question question) {
    return ResponseEntity.ok(questionService.createQuestion(question));
  }

  @Override
  public ResponseEntity<List<Question>> getQuestions() {
    return ResponseEntity.ok(questionService.getQuestions());
  }

  @Override
  public ResponseEntity<Question> getQuestionById(final UUID id) {
    return ResponseEntity.ok(questionService.getQuestionById(id).orElse(null));
  }

  @Override
  public ResponseEntity<Question> updateQuestion(Question question) {
    return ResponseEntity.ok(questionService.updateQuestion(question));
  }

  @Override
  public ResponseEntity<Void> deleteQuestion(final UUID id) {
    questionService.deleteQuestion(id);
    return ResponseEntity.noContent().build();
  }
}
