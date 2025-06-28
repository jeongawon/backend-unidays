package com.example.unidays.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;   // Spring Security 권한 코드 (ex: ROLE_USER)
    private final String title; // 사용자 권한 명칭 (UI용 표시 예: "일반 사용자")
}
