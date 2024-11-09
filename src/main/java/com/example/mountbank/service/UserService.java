package com.example.mountbank.service;

import com.example.mountbank.model.User;
import com.example.mountbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection for UserRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 註冊新使用者
    public Map<String, Object> registerUser(String username, String email, String password, String coverImage, String biography, String phoneNumber) {
        Map<String, Object> response = new HashMap<>();

        // 檢查手機號碼是否已被註冊
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            response.put("success", false);
            response.put("message", "手機號碼已被註冊");
            return response;
        }

        // 檢查 email 是否已被註冊
        if (userRepository.existsByEmail(email)) {
            response.put("success", false);
            response.put("message", "信箱已被註冊");
            return response;
        }

        // 創建並儲存使用者
        User user = new User(username, email, password, coverImage, biography, phoneNumber);
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "註冊成功");
        response.put("user", user);
        return response;
    }

    // 驗證登入
    public User validateLogin(String phoneNumber, String password) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("使用者不存在");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(user.hashPassword(password))) {
            throw new RuntimeException("密碼錯誤");
        }

        return user;
    }
}
