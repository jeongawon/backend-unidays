package com.example.unidays.schedule.domain;

import com.example.unidays.auth.domain.User;
import com.example.unidays.subject.domain.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // ← 🔥 여기에 title 필드 추가!

    private LocalDate date;

    private int alertBeforeDays;

    @Enumerated(EnumType.STRING)
    private ScheduleType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
