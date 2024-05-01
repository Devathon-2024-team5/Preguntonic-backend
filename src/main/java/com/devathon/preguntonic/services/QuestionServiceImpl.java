package com.devathon.preguntonic.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devathon.preguntonic.domain.Question;
import com.devathon.preguntonic.repositories.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
    
    @Autowired
    private QuestionRepository repository;

    @Override
    public Optional<Question> getQuestionById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Question getRandomQuestion() {
        return repository.getRandomQuestion();
    }

}
