/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.model;

public enum PlayerStatus {
  CONNECTING, // Connecting to the game server (not in a room)
  IN_LOBBY_UNREADY, // Waiting in the lobby
  IN_LOBBY_READY, // Waiting in the lobby and ready
  IN_GAME, // Playing the game
  IN_RESULTS, // In the results screen
  DISCONNECTED; // Disconnected from the game
}
