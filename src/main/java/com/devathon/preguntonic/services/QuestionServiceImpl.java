/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import com.devathon.preguntonic.domain.Question;
import com.devathon.preguntonic.repositories.QuestionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository repository;

  @Override
  public Optional<Question> getQuestionById(final UUID id) {
    return repository.findById(id);
  }

  @Override
  public Question getRandomQuestion() {
    return repository.getRandomQuestion();
  }

  @Override
  public List<Question> getQuestions() {
    return repository.findAll();
  }

  @Override
  public Question createQuestion(final Question question) {
    return repository.save(question);
  }

  @Override
  public Question updateQuestion(final Question question) {
    return repository.save(question);
  }

  @Override
  public void deleteQuestion(final UUID id) {
    repository.deleteById(id);
  }
}
