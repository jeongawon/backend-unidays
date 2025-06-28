package com.example.unidays.team.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class TeamScheduleRequest {
    private String title;
    private String type;
    private LocalDate date;
    private int notifyBefore;
}
