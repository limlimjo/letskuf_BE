package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchLineupDTO {

    private int lineupId;
    private int matchId;
    private int playerId;
    private int teamId;
    private String position;
    private int isStarting;
    private int uniformNum;
    private String name;
}