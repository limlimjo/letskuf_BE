package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class CoachDTO {
    private int no;
    private int coachId;
    private String name;
    private LocalDate birthDate;
    private int teamId;
    private String typeGbn;
    private String teamNm;
    private String title;
    private int fileAttached;
    private List<MultipartFile> file;
    private String createdAt;

    private int pageIndex = 1; // 현재페이지
    private int pageUnit = 10; // 페이지갯수
    private int pageSize = 10; // 페이지사이즈
    private int firstIndex = 1; // 첫페이지 인덱스
    private int lastIndex = 1; // 마지막페이지 인덱스
    private int recordCountPerPage = 10; // 페이지당 레코드 개수
    private int rowNo = 0; // 레코드 번호
}
