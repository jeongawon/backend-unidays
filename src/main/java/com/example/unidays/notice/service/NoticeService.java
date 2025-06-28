package com.example.unidays.notice.service;

import com.example.unidays.auth.domain.User;
import com.example.unidays.auth.dto.SessionUser;
import com.example.unidays.auth.repository.UserRepository;
import com.example.unidays.notice.domain.Notice;
import com.example.unidays.notice.domain.NoticeType;
import com.example.unidays.notice.dto.NoticeRequest;
import com.example.unidays.notice.repository.NoticeRepository;
import com.example.unidays.schedule.domain.Schedule;
import com.example.unidays.schedule.domain.ScheduleType;
import com.example.unidays.schedule.repository.ScheduleRepository;
import com.example.unidays.subject.domain.Subject;
import com.example.unidays.subject.repository.SubjectRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final HttpSession session;

    // ✅ 공지 생성 (이제는 일정 자동 등록 안함)
    public Notice createNotice(NoticeRequest request) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow();

        Subject subject = null;
        if (request.getSubjectId() != null) {
            subject = subjectRepository.findById(request.getSubjectId()).orElseThrow();
        }

        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .date(request.getDate())
                .imageUrl(request.getImageUrl())
                .fileUrl(request.getFileUrl())
                .type(request.getType())
                .isAssignment(request.isAssignment())
                .writer(user)
                .subject(subject)
                .build();

        return noticeRepository.save(notice);
    }

    // ✅ 학교 공지 목록
    public List<Notice> getSchoolNotices() {
        return noticeRepository.findByType(NoticeType.SCHOOL);
    }

    // ✅ 과목 공지 목록
    public List<Notice> getSubjectNotices(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow();
        return noticeRepository.findBySubject(subject);
    }

    // ✅ 공지 삭제
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // ✅ 공지 수정
    public Notice updateNotice(Long id, NoticeRequest request) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setDate(request.getDate());
        notice.setImageUrl(request.getImageUrl());
        notice.setFileUrl(request.getFileUrl());
        notice.setAssignment(request.isAssignment());
        return noticeRepository.save(notice);
    }

    // ✅ 저장 버튼 클릭 시: 첨부파일 OCR 분석 → 일정 등록
    public String ocrAndRegisterScheduleFromNotice(Long noticeId, User user) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지를 찾을 수 없습니다."));

        String fileUrl = notice.getFileUrl();
        if (fileUrl == null || fileUrl.isEmpty()) {
            return "첨부된 파일이 없습니다.";
        }

        File file = new File(fileUrl.startsWith("/") ? fileUrl.substring(1) : fileUrl);

        try {
            String text = extractTextFromFile(file);
            String title = text.split("\n")[0].trim();
            LocalDate date = extractDateFromText(text);
            ScheduleType type = extractScheduleTypeFromText(text);

            Schedule schedule = Schedule.builder()
                    .title(title)
                    .date(date)
                    .alertBeforeDays(1)
                    .type(type)
                    .user(user)
                    .subject(notice.getSubject())
                    .build();
            scheduleRepository.save(schedule);

            return "공지 첨부 파일 분석 완료: 일정 등록 성공";
        } catch (Exception e) {
            return "OCR 처리 실패: " + e.getMessage();
        }
    }

    // ✅ OCR 텍스트 추출 (PDF 또는 이미지)
    private String extractTextFromFile(File file) throws Exception {
        String filename = file.getName().toLowerCase();

        if (filename.endsWith(".pdf")) {
            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        } else {
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("kor");
            BufferedImage image = ImageIO.read(file);
            return tesseract.doOCR(image);
        }
    }

    // ✅ 텍스트에서 날짜 추출
    private LocalDate extractDateFromText(String text) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return LocalDate.parse(matcher.group());
        } else {
            return LocalDate.now();
        }
    }

    // ✅ 텍스트에서 일정 유형 추출
    private ScheduleType extractScheduleTypeFromText(String text) {
        String lower = text.toLowerCase();

        if (lower.contains("시험") || lower.contains("test") || lower.contains("중간") || lower.contains("기말")) {
            return ScheduleType.EXAM;
        }

        if (lower.contains("과제") || lower.contains("제출") || lower.contains("report") || lower.contains("과제물")) {
            return ScheduleType.TASK;
        }

        return ScheduleType.ETC;
    }
}
