/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoomEvent {
  JOIN,
  LEAVE,
  READY,
  UNREADY,
  START_GAME,
}
