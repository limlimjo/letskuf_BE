package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CoachFileDTO {
    private int fileId;
    private int coachId;
    private String originalFileName;
    private String storedFileName;
}
