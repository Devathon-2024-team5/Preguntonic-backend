package com.devathon.preguntonic.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devathon.preguntonic.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query(value = "SELECT * FROM questions ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Question getRandomQuestion();

}
