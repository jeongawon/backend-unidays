package com.example.unidays.subject.controller;

import com.example.unidays.auth.domain.User;
import com.example.unidays.auth.dto.SessionUser;
import com.example.unidays.auth.repository.UserRepository;
import com.example.unidays.subject.domain.UserSubject;
import com.example.unidays.subject.dto.SubjectRegisterRequest;
import com.example.unidays.subject.service.UserSubjectService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usersubjects")
public class UserSubjectController {

    private final UserSubjectService userSubjectService;
    private final UserRepository userRepository;

    // ✅ 과목 등록
    @PostMapping
    public ResponseEntity<UserSubject> registerSubject(@RequestBody SubjectRegisterRequest request,
                                                       HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        UserSubject registered = userSubjectService.register(user, request.getSubjectId());
        return ResponseEntity.ok(registered);
    }

    // ✅ 내가 등록한 과목 전체 조회
    @GetMapping
    public ResponseEntity<List<UserSubject>> getMySubjects(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        return ResponseEntity.ok(userSubjectService.getMySubjects(user));
    }

    // ✅ 등록한 과목 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        userSubjectService.delete(user, id);
        return ResponseEntity.ok().build();
    }

    // ✅ 요청 DTO 클래스
    @Data
    public static class RegisterRequest {
        private Long subjectId;
    }
}
