package com.example.mountbank.repository;

import com.example.mountbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 根據手機號碼查找用戶，返回 Optional
    Optional<User> findByPhoneNumber(String phoneNumber);

    // 檢查手機號碼是否已註冊
    boolean existsByPhoneNumber(String phoneNumber);

    // 根據 email 查找用戶，返回 Optional
    Optional<User> findByEmail(String email);

    // 檢查 email 是否已註冊
    boolean existsByEmail(String email);
}
