package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchLineupSaveDTO {

    private int matchId;
    private int playerId;
    private int teamId;
    private String position;
    private int isStarting;
    private List<MatchLineupDTO> homePlayers;
    private List<MatchLineupDTO> awayPlayers;
    private String homeFormation;
    private String awayFormation;
}