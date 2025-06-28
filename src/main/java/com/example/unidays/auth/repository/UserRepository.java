package com.example.unidays.auth.repository;

import com.example.unidays.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일을 통해 사용자를 조회 (OAuth 사용자 식별용)
    Optional<User> findByEmail(String email);
}
