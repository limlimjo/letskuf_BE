package com.letskuf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LeagueVenueDTO {
    private int leagueVenueId;
    private int leagueId;
    private int venueId;
    private String name;
    private String address;
}
