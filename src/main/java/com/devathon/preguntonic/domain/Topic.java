/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Builder
@Entity
@Table(
    name = "topics",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uq_topic",
          columnNames = {"topic"})
    })
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

  @Id @UuidGenerator private UUID id;

  @Column(name = "topic", nullable = false, unique = true)
  private String topic;
}
