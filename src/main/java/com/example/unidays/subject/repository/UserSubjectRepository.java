package com.example.unidays.subject.repository;

import com.example.unidays.auth.domain.User;
import com.example.unidays.subject.domain.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {

    // 사용자가 등록한 과목 전체 조회
    List<UserSubject> findByUser(User user);

    // 중복 등록 방지
    boolean existsByUserAndSubjectId(User user, Long subjectId);
}
