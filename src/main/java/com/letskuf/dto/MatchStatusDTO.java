package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchStatusDTO {

    private int matchId;
    private String status;
    private int minute;
    private int extraMinute;
}
