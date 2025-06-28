package com.example.unidays.team.repository;

import com.example.unidays.auth.domain.User;
import com.example.unidays.team.domain.Team;
import com.example.unidays.team.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    boolean existsByUserAndTeam(User user, Team team); // 중복 초대 방지용
}
