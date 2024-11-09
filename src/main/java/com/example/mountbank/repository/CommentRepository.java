package com.example.mountbank.repository;

import com.example.mountbank.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 根據 Post ID 查詢留言
    List<Comment> findByPost_PostId(Long postId);

    // 根據 User ID 查詢留言
    List<Comment> findByUser_UserId(Long userId);
}
