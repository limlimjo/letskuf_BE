package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchEventDTO {

    private int eventId;
    private int matchId;
    private int teamId;
    private String teamNm;
    private int playerId;
    private String playerNm;
    private int uniformNum;
    private int relatedPlayerId;
    private String eventType;
    private int minute;
    private int extraMinute;
    private int homeScore;
    private int awayScore;
}
