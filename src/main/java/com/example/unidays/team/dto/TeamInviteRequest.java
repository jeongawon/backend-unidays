package com.example.unidays.team.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamInviteRequest {
    private Long teamId;
    private String email;
}
