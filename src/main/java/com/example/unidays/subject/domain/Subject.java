package com.example.unidays.subject.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "major", "time"}) // ✅ 중복 방지
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 과목명
    private String time;        // 요일 및 시간 (예: "월 3-4, 수 2")
    private String type;        // 전공 or 교양
    private String major;       // 전공 소속 학과 (교양일 경우 "교양")
    private int grade;          // 개설 학년 (1~4)
}
