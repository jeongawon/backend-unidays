package com.example.unidays.notice.dto;

import com.example.unidays.notice.domain.NoticeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class NoticeRequest {
    private String title;
    private String content;
    private LocalDate date;
    private String imageUrl;
    private String fileUrl;
    private NoticeType type;
    private boolean isAssignment;
    private Long subjectId;
}
