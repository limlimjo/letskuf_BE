package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchEventSaveDTO {

    private Integer eventId;
    private Integer matchId;
    private Integer teamId;
    private Integer playerId;
    private Integer relatedPlayerId;
    private Integer assistPlayerId;
    private EventType eventType;
    private Integer minute;
    private Integer extraMinute;
}
