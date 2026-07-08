# 📅 캠퍼스 캘린더 (Campus Calendar · Unidays) — Backend

> **흩어진 대학 일정을 하나로**
> 과제·시험·발표·팀플 일정부터 교내외 행사·공모전까지, 대학생활 전체를 한곳에서 통합 관리하는 대학생 맞춤형 캘린더 서비스의 백엔드 서버입니다.

<p>
  <img src="https://img.shields.io/badge/멋쟁이사자처럼-세션_미니프로젝트_(3팀)-1E7D3C?style=flat-square" />
  <img src="https://img.shields.io/badge/Java-21-007396?style=flat-square&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.0-6DB33F?style=flat-square&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white" />
</p>

---

## 📖 프로젝트 소개

대학생은 매 학기 여러 과목의 과제·시험을 수행하지만 교수마다 플랫폼이 달라(LMS·구글클래스룸·단톡방·드라이브) 일정 확인이 번거롭고 놓치기 쉽습니다. 캠퍼스 캘린더는 **과제·시험·팀플·교내 행사·대외활동까지 학생의 전체 대학생활을 하나의 캘린더에 집약**하고, 팀플 협업 일정 공유와 마감일 알림을 제공합니다.

특히 **강의계획서 PDF나 시간표 이미지를 업로드하면, 텍스트 추출(PDF)·OCR(이미지)로 일정을 자동 인식**해 캘린더에 등록하는 기능을 백엔드에서 지원합니다.

## ✨ 주요 기능

| 기능 | 설명 |
|------|------|
| 🔐 소셜 로그인 | OAuth2 기반 로그인 (Spring Security OAuth2 Client) |
| 📅 일정 CRUD | 강의별 과제·시험·발표 일정 등록/조회/수정/삭제 (JPA) |
| 📄 PDF 일정 추출 | 강의계획서 등 PDF에서 텍스트 추출 (Apache PDFBox) |
| 🖼️ 이미지 OCR 추출 | 시간표·공지 이미지에서 일정 텍스트 인식 (Tess4J / Tesseract) |
| 🔔 마감일 알림 | 마감 기준 D-day 리마인드 |
| 👥 팀 일정 공유 | 팀 단위로 공동 일정 열람/수정 (팀플 최적화) |
| 🎉 행사/공모전 큐레이션 | 교내 행사·학과 활동·외부 공모전 정보 열람 및 내 일정 추가 |

## 🧱 기술 스택

| 영역 | 기술 |
|------|------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.0 (Spring Web, Data JPA, Security, OAuth2 Client) |
| Database | MySQL |
| 문서·이미지 처리 | Apache PDFBox 2.0.27 (PDF 텍스트 추출), Tess4J 5.4.0 (이미지 OCR) |
| 기타 | Lombok, JUnit 5 |
| Build | Gradle (Gradle Wrapper 포함) |

## 🚀 시작하기 (Getting Started)

### Prerequisites
- JDK 21
- MySQL 8.x
- **Tesseract OCR** (이미지 OCR 기능 사용 시) — Tess4J가 사용하는 네이티브 엔진 및 언어 데이터(`tessdata`, 한국어 `kor` 권장) 설치 필요
  ```bash
  # macOS
  brew install tesseract tesseract-lang
  # Ubuntu
  sudo apt-get install tesseract-ocr tesseract-ocr-kor
  ```

### 실행

```bash
# 1. 클론
git clone https://github.com/jeongawon/backend-unidays.git
cd backend-unidays

# 2. 빌드
./gradlew build

# 3. 실행
./gradlew bootRun
```

### 환경 설정

`src/main/resources/application.yml` (또는 `application.properties`)에 DB·OAuth2 정보를 설정하세요.

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/unidays
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
  security:
    oauth2:
      client:
        registration:
          # 예: google / kakao / naver 등 사용하는 provider 설정
          google:
            client-id: your_client_id
            client-secret: your_client_secret
```

> Tesseract 설치 경로 및 `tessdata` 위치는 OCR 설정에 맞게 지정하세요. OAuth2 provider 키는 환경변수로 주입하는 것을 권장합니다.

## 📁 프로젝트 구조

```
backend-unidays/
├── gradle/wrapper/        # Gradle Wrapper
├── src/
│   ├── main/
│   │   ├── java/          # 애플리케이션 소스 (Spring Boot)
│   │   └── resources/     # 설정 파일 (application.yml 등)
│   └── test/              # 테스트 코드 (JUnit 5)
├── build.gradle           # 의존성 및 빌드 설정
├── settings.gradle
└── gradlew / gradlew.bat  # Gradle Wrapper 실행 스크립트
```

## 👤 팀 / 역할

**멋쟁이사자처럼 세션 미니 프로젝트 (3팀)**

- **원정아** — 팀장 / 백엔드 총괄 · 서비스 기획
  - 회원 관리·일정 CRUD REST API, MySQL 설계·연동
  - 개인/팀 일정 공유, 행사 큐레이션, 마감일 알림 로직 개발
  - PDF·이미지 기반 일정 자동 추출 연동, 프론트 연동·테스트, 발표 주도
