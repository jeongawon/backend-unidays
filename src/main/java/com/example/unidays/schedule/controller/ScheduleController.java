package com.example.unidays.schedule.controller;

import com.example.unidays.schedule.domain.Schedule;
import com.example.unidays.schedule.dto.ScheduleRequest;
import com.example.unidays.schedule.service.ScheduleService;
import com.example.unidays.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final HttpSession session;

    private String getEmail() {
        SessionUser user = (SessionUser) session.getAttribute("user");
        return user.getEmail();
    }

    @PostMapping
    public Schedule create(@RequestBody ScheduleRequest request) {
        return scheduleService.create(request, getEmail());
    }

    @PutMapping("/{id}")
    public Schedule update(@PathVariable Long id, @RequestBody ScheduleRequest request) {
        return scheduleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        scheduleService.delete(id);
    }

    @GetMapping("/month")
    public List<Schedule> getMonthly(@RequestParam int year, @RequestParam int month) {
        return scheduleService.getMonthlySchedules(getEmail(), year, month);
    }

    @GetMapping
    public List<Schedule> getAll() {
        return scheduleService.getAllSchedules(getEmail());
    }
}
