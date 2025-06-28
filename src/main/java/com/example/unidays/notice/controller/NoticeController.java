package com.example.unidays.notice.controller;

import com.example.unidays.auth.domain.User;
import com.example.unidays.auth.dto.SessionUser;
import com.example.unidays.auth.repository.UserRepository;
import com.example.unidays.notice.domain.Notice;
import com.example.unidays.notice.dto.NoticeRequest;
import com.example.unidays.notice.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;
    private final UserRepository userRepository;

    // ✅ 공지 생성
    @PostMapping
    public ResponseEntity<Notice> create(@RequestBody NoticeRequest request) {
        return ResponseEntity.ok(noticeService.createNotice(request));
    }

    // ✅ 학교 공지 목록 조회
    @GetMapping("/school")
    public ResponseEntity<List<Notice>> getSchoolNotices() {
        return ResponseEntity.ok(noticeService.getSchoolNotices());
    }

    // ✅ 과목 공지 목록 조회
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<Notice>> getSubjectNotices(@PathVariable Long subjectId) {
        return ResponseEntity.ok(noticeService.getSubjectNotices(subjectId));
    }

    // ✅ 공지 수정
    @PutMapping("/{id}")
    public ResponseEntity<Notice> update(@PathVariable Long id, @RequestBody NoticeRequest request) {
        return ResponseEntity.ok(noticeService.updateNotice(id, request));
    }

    // ✅ 공지 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }

    // ✅ 저장 버튼 클릭 시: 첨부파일 OCR 분석 → 일정 자동 등록
    @PostMapping("/{noticeId}/save")
    public ResponseEntity<String> saveNoticeToCalendar(@PathVariable Long noticeId,
                                                       HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow();

        String message = noticeService.ocrAndRegisterScheduleFromNotice(noticeId, user);
        return ResponseEntity.ok(message);
    }

    // ✅ 공지 등록 시 파일 업로드용 엔드포인트 (절대 경로 + 자동 생성)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String uploadDir = new File("uploads").getAbsolutePath(); // ✅ 절대 경로 사용
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); // ✅ 폴더 자동 생성

            File dest = new File(uploadDir + File.separator + filename);
            file.transferTo(dest);

            return ResponseEntity.ok("/uploads/" + filename);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("업로드 실패: " + e.getMessage());
        }
    }
}
