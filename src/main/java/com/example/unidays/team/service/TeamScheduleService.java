package com.example.unidays.team.service;

import com.example.unidays.team.domain.Team;
import com.example.unidays.team.domain.TeamSchedule;
import com.example.unidays.team.dto.TeamScheduleRequest;
import com.example.unidays.team.repository.TeamRepository;
import com.example.unidays.team.repository.TeamScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamScheduleService {

    private final TeamRepository teamRepository;
    private final TeamScheduleRepository teamScheduleRepository;

    public TeamSchedule save(Long teamId, TeamScheduleRequest request) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀 없음"));

        TeamSchedule schedule = TeamSchedule.builder()
                .team(team)
                .title(request.getTitle())
                .type(request.getType())
                .date(request.getDate())
                .notifyBefore(request.getNotifyBefore())
                .build();

        return teamScheduleRepository.save(schedule);
    }

    public TeamSchedule update(Long id, TeamScheduleRequest request) {
        TeamSchedule schedule = teamScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정 없음"));

        schedule.setTitle(request.getTitle());
        schedule.setType(request.getType());
        schedule.setDate(request.getDate());
        schedule.setNotifyBefore(request.getNotifyBefore());

        return teamScheduleRepository.save(schedule);
    }

    public void delete(Long id) {
        TeamSchedule schedule = teamScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정 없음"));
        teamScheduleRepository.delete(schedule);
    }

    public List<TeamSchedule> getByMonth(Long teamId, int year, int month) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀 없음"));
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return teamScheduleRepository.findByTeamAndDateBetweenOrderByDateAsc(team, start, end);
    }

    public List<TeamSchedule> getUpcomingAlarms(Long teamId) {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(7);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀 없음"));

        return teamScheduleRepository.findByTeamAndDateBetween(team, today, end).stream()
                .filter(s -> ChronoUnit.DAYS.between(today, s.getDate()) == s.getNotifyBefore())
                .sorted(Comparator.comparing(TeamSchedule::getDate))
                .toList();
    }
}
