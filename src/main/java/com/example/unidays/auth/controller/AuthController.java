package com.example.unidays.auth.controller;

import com.example.unidays.auth.domain.User;
import com.example.unidays.auth.dto.AdditionalInfoRequest;
import com.example.unidays.auth.dto.SessionUser;
import com.example.unidays.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    /**
     * 1. 로그인 여부 및 유저 정보 확인
     */
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Optional<User> userOpt = userRepository.findByEmail(sessionUser.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getStudentId() == null || user.getMajor() == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("추가 정보 입력 필요");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("studentId", user.getStudentId());
            response.put("major", user.getMajor());
            response.put("role", user.getRole());
            response.put("picture", user.getPicture());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보를 찾을 수 없습니다.");
    }

    /**
     * 2. 추가 정보 입력 (학번, 전공)
     */
    @PostMapping("/additional-info")
    public ResponseEntity<?> submitAdditionalInfo(
            @RequestBody AdditionalInfoRequest request,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Optional<User> userOpt = userRepository.findByEmail(sessionUser.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // ❌ 자동 담기 제거: studentId, major만 저장
            user.updateAdditionalInfo(request.getStudentId(), request.getMajor());
            userRepository.save(user);
            return ResponseEntity.ok("추가 정보 입력 완료");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보를 찾을 수 없습니다.");
    }
}
