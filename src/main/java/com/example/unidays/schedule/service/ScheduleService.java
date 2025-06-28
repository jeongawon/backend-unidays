package com.example.unidays.schedule.service;

import com.example.unidays.auth.domain.User;
import com.example.unidays.auth.repository.UserRepository;
import com.example.unidays.schedule.domain.Schedule;
import com.example.unidays.schedule.domain.ScheduleType;
import com.example.unidays.schedule.dto.ScheduleRequest;
import com.example.unidays.schedule.repository.ScheduleRepository;
import com.example.unidays.subject.domain.Subject;
import com.example.unidays.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    // ✅ 일정 생성
    public Schedule create(ScheduleRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow();

        Schedule schedule = Schedule.builder()
                .title(request.getTitle()) // ✅ title 추가
                .user(user)
                .subject(subject)
                .type(request.getType())
                .date(request.getDate())
                .alertBeforeDays(request.getAlertBeforeDays())
                .build();

        return scheduleRepository.save(schedule);
    }

    // ✅ 일정 수정
    public Schedule update(Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow();

        schedule.setTitle(request.getTitle()); // ✅ 수정 시에도 title 반영
        schedule.setSubject(subject);
        schedule.setType(request.getType());
        schedule.setDate(request.getDate());
        schedule.setAlertBeforeDays(request.getAlertBeforeDays());

        return scheduleRepository.save(schedule);
    }

    // ✅ 일정 삭제
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    // ✅ 월별 일정 조회
    public List<Schedule> getMonthlySchedules(String email, int year, int month) {
        User user = userRepository.findByEmail(email).orElseThrow();
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return scheduleRepository.findAllByUserAndDateBetweenOrderByDate(user, start, end);
    }

    // ✅ 전체 일정 조회
    public List<Schedule> getAllSchedules(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return scheduleRepository.findAllByUser(user);
    }
}
