package com.example.unidays.notice.domain;

import com.example.unidays.auth.domain.User;
import com.example.unidays.subject.domain.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDate date;

    private String imageUrl;
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private NoticeType type; // SCHOOL or SUBJECT

    private boolean isAssignment;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
}
