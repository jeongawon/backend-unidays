package com.example.unidays.team.controller;

import com.example.unidays.auth.domain.User;
import com.example.unidays.auth.dto.SessionUser;
import com.example.unidays.auth.repository.UserRepository;
import com.example.unidays.team.domain.Team;
import com.example.unidays.team.dto.TeamCreateRequest;
import com.example.unidays.team.dto.TeamInviteRequest;
import com.example.unidays.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Team>> getMyTeams(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow();
        return ResponseEntity.ok(teamService.getMyTeams(user));
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody TeamCreateRequest request, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow();
        return ResponseEntity.ok(teamService.createTeam(user, request.getName()));
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(@RequestBody TeamInviteRequest request, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow();
        teamService.invite(user, request);
        return ResponseEntity.ok().build();
    }
}
