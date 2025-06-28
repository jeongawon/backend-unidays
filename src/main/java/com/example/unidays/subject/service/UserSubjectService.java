package com.example.unidays.subject.service;

import com.example.unidays.auth.domain.User;
import com.example.unidays.subject.domain.Subject;
import com.example.unidays.subject.domain.UserSubject;
import com.example.unidays.subject.repository.SubjectRepository;
import com.example.unidays.subject.repository.UserSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSubjectService {

    private final UserSubjectRepository userSubjectRepository;
    private final SubjectRepository subjectRepository;

    // 과목 등록
    public UserSubject register(User user, Long subjectId) {
        if (userSubjectRepository.existsByUserAndSubjectId(user, subjectId)) {
            throw new IllegalArgumentException("이미 등록한 과목입니다.");
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("과목이 존재하지 않습니다."));

        UserSubject userSubject = UserSubject.builder()
                .user(user)
                .subject(subject)
                .build();

        return userSubjectRepository.save(userSubject);
    }

    // 과목 조회
    public List<UserSubject> getMySubjects(User user) {
        return userSubjectRepository.findByUser(user);
    }

    // 과목 삭제
    public void delete(User user, Long id) {
        UserSubject userSubject = userSubjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 존재하지 않습니다."));

        if (!userSubject.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        userSubjectRepository.delete(userSubject);
    }
}
