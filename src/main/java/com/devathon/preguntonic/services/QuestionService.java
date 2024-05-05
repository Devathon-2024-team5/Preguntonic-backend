/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import com.devathon.preguntonic.domain.Question;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {

  Optional<Question> getQuestionById(UUID id);

  Question getRandomQuestion();

  List<Question> getQuestions();

  Question createQuestion(Question question);

  Question updateQuestion(Question question);

  void deleteQuestion(UUID id);
}
