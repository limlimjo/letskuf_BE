package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlayerFileDTO {
    private int fileId;
    private int playerId;
    private String originalFileName;
    private String storedFileName;
}
