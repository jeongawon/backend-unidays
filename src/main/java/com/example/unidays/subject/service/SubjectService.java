package com.example.unidays.subject.service;

import com.example.unidays.subject.domain.Subject;
import com.example.unidays.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    // 전공 과목 조회 (사용자 전공 기준)
    public List<Subject> getMajorSubjects(String major) {
        return subjectRepository.findByTypeAndMajor("전공", major);
    }

    // 교양 과목 조회
    public List<Subject> getGeneralSubjects() {
        return subjectRepository.findByType("교양");
    }

    // 학년 필터
    public List<Subject> getSubjectsByGrade(int grade) {
        return subjectRepository.findByGrade(grade);
    }

    // 과목명 검색
    public List<Subject> searchSubjects(String keyword) {
        return subjectRepository.findByNameContaining(keyword);
    }
}