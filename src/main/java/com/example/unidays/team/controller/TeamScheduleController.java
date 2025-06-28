package com.example.unidays.team.controller;

import com.example.unidays.team.domain.TeamSchedule;
import com.example.unidays.team.dto.TeamScheduleRequest;
import com.example.unidays.team.service.TeamScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams/{teamId}/schedules")
public class TeamScheduleController {

    private final TeamScheduleService teamScheduleService;

    @PostMapping
    public ResponseEntity<TeamSchedule> createSchedule(@PathVariable Long teamId,
                                                       @RequestBody TeamScheduleRequest request) {
        return ResponseEntity.ok(teamScheduleService.save(teamId, request));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<TeamSchedule> updateSchedule(@PathVariable Long scheduleId,
                                                       @RequestBody TeamScheduleRequest request) {
        return ResponseEntity.ok(teamScheduleService.update(scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        teamScheduleService.delete(scheduleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/month")
    public ResponseEntity<List<TeamSchedule>> getSchedulesByMonth(@PathVariable Long teamId,
                                                                  @RequestParam int year,
                                                                  @RequestParam int month) {
        return ResponseEntity.ok(teamScheduleService.getByMonth(teamId, year, month));
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<TeamSchedule>> getUpcomingAlarms(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamScheduleService.getUpcomingAlarms(teamId));
    }
}
