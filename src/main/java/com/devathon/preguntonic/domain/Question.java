/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Builder
@Entity
@Table(
    name = "questions",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uq_question",
          columnNames = {"question"})
    })
@NoArgsConstructor
@AllArgsConstructor
public class Question {

  @Id @UuidGenerator private UUID id;

  @Column(name = "question", nullable = false, unique = true)
  private String question;

  @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
  private List<Answer> answers;

  @ManyToOne
  @JoinColumn(name = "topic_id", nullable = false)
  private Topic topic;

  public Answer getCorrectAnswer() {
    return answers.stream().filter(Answer::isCorrect).findFirst().orElseThrow(() -> new RuntimeException("No correct answer found"));
  }

}
