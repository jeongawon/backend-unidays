package com.example.unidays.auth.dto;

import com.example.unidays.auth.domain.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 세션에 저장될 사용자 정보를 담는 클래스 (직렬화 필수)
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
