package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class MatchDTO {
    private int matchId;
    private int leagueId;
    private String type;
    private String leagueNm;
    private int venueId;
    private String venueNm;
    private int homeTeamId;
    private String homeTeamNm;
    private int awayTeamId;
    private String awayTeamNm;
    private LocalDate matchDate;
    private String kickoffTime;
    private int homeScore;
    private int awayScore;
    private List<MatchUniformDTO> uniformList;
    private matchStatusType status;
    private String createdAt;

    private int pageIndex = 1; // 현재페이지
    private int pageUnit = 10; // 페이지갯수
    private int pageSize = 10; // 페이지사이즈
    private int firstIndex = 1; // 첫페이지 인덱스
    private int lastIndex = 1; // 마지막페이지 인덱스
    private int recordCountPerPage = 10; // 페이지당 레코드 개수
    private int rowNo = 0; // 레코드 번호
}
