package com.example.unidays.subject.controller;

import com.example.unidays.auth.domain.User;
import com.example.unidays.auth.dto.SessionUser;
import com.example.unidays.subject.domain.Subject;
import com.example.unidays.subject.service.SubjectService;
import com.example.unidays.auth.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final UserRepository userRepository;

    // ✅ 로그인한 사용자의 전공 기반 과목 조회
    @GetMapping("/major")
    public ResponseEntity<List<Subject>> getMajorSubjects(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        List<Subject> subjects = subjectService.getMajorSubjects(user.getMajor());
        return ResponseEntity.ok(subjects);
    }

    // ✅ 교양 과목만 조회 (세션 필요 없음)
    @GetMapping("/culture")
    public ResponseEntity<List<Subject>> getCultureSubjects() {
        List<Subject> subjects = subjectService.getGeneralSubjects();
        return ResponseEntity.ok(subjects);
    }
}
