package com.example.mountbank.repository;

import com.example.mountbank.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 根據使用者的 userId 查詢所有發文
    List<Post> findByUser_UserId(Long userId);

    // 根據用戶的 phoneNumber 查詢所有發文
    List<Post> findByUser_PhoneNumber(String phoneNumber); // 假設 Post 類中的 user 屬性與 User 類存在 @ManyToOne 或 @OneToOne 關聯

    Post findByPostId(Long postId);

}
