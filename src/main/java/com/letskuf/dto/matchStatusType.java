package com.letskuf.dto;

public enum matchStatusType {
    SCHEDULED,   // 경기 예정
    FIRST_HALF,  // 전반 진행중
    HALF_TIME,   // 하프타임
    SECOND_HALF, // 후반 진행중
    FINISHED,    // 경기 종료
    CANCELLED,   // 경기 취소
    POSTPONED    // 경기 연기
}
