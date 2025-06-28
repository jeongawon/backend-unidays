package com.example.unidays.team.service;

import com.example.unidays.auth.domain.User;
import com.example.unidays.team.domain.Team;
import com.example.unidays.team.domain.TeamMember;
import com.example.unidays.team.dto.TeamInviteRequest;
import com.example.unidays.team.repository.TeamRepository;
import com.example.unidays.team.repository.TeamMemberRepository;
import com.example.unidays.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    public Team createTeam(User user, String name) {
        Team team = Team.builder().name(name).build();
        teamRepository.save(team);

        teamMemberRepository.save(
                TeamMember.builder().user(user).team(team).build()
        );

        return team;
    }

    public void invite(User inviter, TeamInviteRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));
        User invitee = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        if (teamMemberRepository.existsByUserAndTeam(invitee, team)) {
            throw new IllegalStateException("이미 팀원입니다.");
        }

        teamMemberRepository.save(
                TeamMember.builder().user(invitee).team(team).build()
        );
    }

    public List<Team> getMyTeams(User user) {
        return teamRepository.findByMembersUser(user);
    }
}
