package com.example.unidays.schedule.dto;

import com.example.unidays.schedule.domain.ScheduleType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequest {

    private String title;            // ✅ 이 줄 추가!
    private Long subjectId;
    private ScheduleType type;
    private LocalDate date;
    private int alertBeforeDays;
}
