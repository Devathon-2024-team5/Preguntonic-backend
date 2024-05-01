package com.devathon.preguntonic.services;

import java.util.Optional;
import java.util.UUID;

import com.devathon.preguntonic.domain.Question;

public interface QuestionService {
    
    Optional<Question> getQuestionById(UUID id);

    Question getRandomQuestion();

}
