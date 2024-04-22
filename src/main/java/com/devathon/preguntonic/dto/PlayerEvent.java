/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.dto;

import lombok.Builder;

@Builder
public record PlayerEvent(BasicPlayer player, String event) {}
