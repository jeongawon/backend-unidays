package com.example.unidays.schedule.repository;

import com.example.unidays.auth.domain.User;
import com.example.unidays.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUser(User user);
    List<Schedule> findAllByUserAndDateBetweenOrderByDate(User user, LocalDate start, LocalDate end);
}
