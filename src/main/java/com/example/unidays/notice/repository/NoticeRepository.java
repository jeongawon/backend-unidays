package com.example.unidays.notice.repository;

import com.example.unidays.notice.domain.Notice;
import com.example.unidays.notice.domain.NoticeType;
import com.example.unidays.subject.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByType(NoticeType type);
    List<Notice> findBySubject(Subject subject);
}
