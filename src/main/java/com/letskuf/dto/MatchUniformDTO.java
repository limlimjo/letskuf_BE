package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MatchUniformDTO {
    private int matchUniformId;
    private int matchId;

    private int teamId;
    private String topColor;
    private String bottomColor;
    private String socksColor;
}
