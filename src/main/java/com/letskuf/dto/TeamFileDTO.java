package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamFileDTO {
    private int fileId;
    private int teamId;
    private String originalFileName;
    private String storedFileName;
}
