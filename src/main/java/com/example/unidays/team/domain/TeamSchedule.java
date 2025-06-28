package com.example.unidays.team.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type;
    private LocalDate date;
    private int notifyBefore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore  // 🔥 무한 참조 방지
    private Team team;
}
