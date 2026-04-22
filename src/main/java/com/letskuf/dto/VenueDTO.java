package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VenueDTO {
    private int no;
    private int venueId;
    private String venueNm;
    private String postcode;
    private String address;
    private String detailAddress;
    private String createdAt;
    private String keyword; // 검색용

    private int pageIndex = 1; // 현재페이지
    private int pageUnit = 10; // 페이지갯수
    private int pageSize = 10; // 페이지사이즈
    private int firstIndex = 1; // 첫페이지 인덱스
    private int lastIndex = 1; // 마지막페이지 인덱스
    private int recordCountPerPage = 10; // 페이지당 레코드 개수
    private int rowNo = 0; // 레코드 번호
}
