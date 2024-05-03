/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

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

  @OneToMany(mappedBy = "question")
  private List<Answer> answers;

  @ManyToOne
  @JoinColumn(name = "topic_id", nullable = false)
  private Topic topic;
}
