package com.example.unidays.team.repository;

import com.example.unidays.auth.domain.User;
import com.example.unidays.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByMembersUser(User user); // 사용자가 속한 모든 팀
}
