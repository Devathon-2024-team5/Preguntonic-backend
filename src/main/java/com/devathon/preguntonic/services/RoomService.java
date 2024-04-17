/*
 *
 * Copyright (c) 2024 Devathon., All Rights Reserved.
 *
 */
package com.devathon.preguntonic.services;

import java.util.List;
import java.util.Optional;

/** Service to manage rooms */
public interface RoomService {

  String createRoom();

  List<String> getRooms();

  Optional<String> getRoom(String roomId);

  void joinRoom(String roomId, String username);
}
