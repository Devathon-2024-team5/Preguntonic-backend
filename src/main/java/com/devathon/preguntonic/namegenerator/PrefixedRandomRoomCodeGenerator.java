/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.namegenerator;

import java.util.Random;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PrefixedRandomRoomCodeGenerator implements RoomCodeGenerator {
  private static final Random random = new Random();
  private final String prefix;

  @Override
  public String generateCode() {
    return prefix + random.nextInt(1000);
  }
}
