package com.devathon.preguntonic.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    
    private UUID id;
    private boolean isAdmin;
    private String ipAddress;
    private String avatar; // TODO: Change to enum if needed
    private String nickname;
    private PlayerStatus status;
    private int score;

    // Transient (lobby)
    private boolean isReady;

    // Transient (game)
    private boolean responded;
    private long responseTime;
    private UUID responseId;
    private boolean readyForNextQuestion;

    public void response(UUID responseId, long responseTime) {
        this.responseId = responseId;
        this.responseTime = responseTime;
        this.responded = true;
    }

    public void resetResponse() {
        this.responseId = null;
        this.responseTime = -1;
        this.responded = false;
    }

}
