package com.example.unidays.team.repository;

import com.example.unidays.team.domain.Team;
import com.example.unidays.team.domain.TeamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TeamScheduleRepository extends JpaRepository<TeamSchedule, Long> {

    // 월별 조회용 (날짜순 정렬)
    List<TeamSchedule> findByTeamAndDateBetweenOrderByDateAsc(Team team, LocalDate start, LocalDate end);

    // 알림 필터용 (7일 이내 조회용)
    List<TeamSchedule> findByTeamAndDateBetween(Team team, LocalDate start, LocalDate end);
}
