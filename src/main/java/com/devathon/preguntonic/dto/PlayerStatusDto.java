package com.devathon.preguntonic.dto;

import java.util.List;
import java.util.UUID;

import com.devathon.preguntonic.model.Player;
import com.devathon.preguntonic.model.PlayerStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public class PlayerStatusDto {
        
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("is_ready")
    private boolean isReady;

    @JsonProperty("score")
    private int score;

    public static PlayerStatusDto from(Player player) {
        return PlayerStatusDto.builder()
            .id(player.getId())
            .nickname(player.getNickname())
            .avatar(player.getAvatar())
            .isReady(player.getStatus().equals(PlayerStatus.IN_LOBBY_READY))
            .score(player.getScore())
            .build();
    }

    public static List<PlayerStatusDto> from(List<Player> players) {
        return players.stream().map(PlayerStatusDto::from).toList();
    }


}
