/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.controllers;

import com.devathon.preguntonic.domain.Question;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/questions")
public interface QuestionsController {

  @PostMapping
  ResponseEntity<Question> createQuestion(@RequestBody Question question);

  @GetMapping
  ResponseEntity<List<Question>> getQuestions();

  @GetMapping("/{id}")
  ResponseEntity<Question> getQuestionById(@PathVariable UUID id);

  @PutMapping("/{id}")
  ResponseEntity<Question> updateQuestion(@RequestBody Question question);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteQuestion(@PathVariable UUID id);
}
