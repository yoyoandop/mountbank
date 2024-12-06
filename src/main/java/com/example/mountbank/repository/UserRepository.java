package com.example.mountbank.repository;

import com.example.mountbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {

    // 根據手機號碼查找用戶，返回 Optional
    Optional<User> findByPhoneNumber(String phoneNumber);

    // 檢查手機號碼是否已註冊
    boolean existsByPhoneNumber(String phoneNumber);


    // 檢查 email 是否已註冊
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber AND u.password = :password")
    User findByPhoneNumberAndPassword(String phoneNumber,String password);

}
