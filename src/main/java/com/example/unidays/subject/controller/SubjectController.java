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

    @GetMapping("/major")
    public ResponseEntity<List<Subject>> getMajorSubjects(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        // ✅ SessionUser에는 major 없음 → DB에서 User를 다시 조회
        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // ✅ 실제 User에서 전공 정보 꺼냄
        List<Subject> subjects = subjectService.getMajorSubjects(user.getMajor());

        return ResponseEntity.ok(subjects);
    }
}
