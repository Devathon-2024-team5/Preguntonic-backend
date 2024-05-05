/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import com.devathon.preguntonic.domain.Question;
import com.devathon.preguntonic.repositories.QuestionRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository repository;

  @Override
  public Optional<Question> getQuestionById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public Question getRandomQuestion() {
    return repository.getRandomQuestion();
  }
}
