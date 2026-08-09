package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MatchSearchDTO {
    private Integer leagueId;
    private String keyword;
    private String status;
    private LeagueType type;
    private LocalDate startDate;
    private LocalDate endDate;

    private int pageIndex = 1; // 현재페이지
    private int pageUnit = 10; // 페이지갯수
    private int pageSize = 10; // 페이지사이즈
    private int firstIndex = 1; // 첫페이지 인덱스
    private int lastIndex = 1; // 마지막페이지 인덱스
    private int recordCountPerPage = 10; // 페이지당 레코드 개수
    private int rowNo = 0; // 레코드 번호

}
