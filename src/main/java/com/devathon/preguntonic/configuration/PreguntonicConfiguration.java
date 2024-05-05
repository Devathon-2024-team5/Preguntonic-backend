/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.configuration;

import com.devathon.preguntonic.namegenerator.PrefixedRandomRoomCodeGenerator;
import com.devathon.preguntonic.namegenerator.RoomCodeGenerator;
import com.devathon.preguntonic.storage.MemoryRoomStorage;
import com.devathon.preguntonic.storage.RoomStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PreguntonicConfiguration {

  @Bean
  public RoomStorage memoryRoomStorage() {
    return MemoryRoomStorage.builder().build();
  }

  @Bean
  public RoomCodeGenerator roomCodeGenerator() {
    return new PrefixedRandomRoomCodeGenerator("ROOM");
  }
}
