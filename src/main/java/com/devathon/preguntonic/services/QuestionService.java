/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import com.devathon.preguntonic.domain.Question;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {

  Optional<Question> getQuestionById(UUID id);

  Question getRandomQuestion();
}
