package com.example.unidays.subject.repository;

import com.example.unidays.subject.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    // 전공 과목: 사용자의 학과에 맞는 전공
    List<Subject> findByTypeAndMajor(String type, String major);

    // 교양 과목
    List<Subject> findByType(String type);

    // 학년 필터
    List<Subject> findByGrade(int grade);

    // 과목명 검색
    List<Subject> findByNameContaining(String keyword);
}