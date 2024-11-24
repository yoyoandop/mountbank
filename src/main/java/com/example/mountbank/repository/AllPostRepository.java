package com.example.mountbank.repository;

import com.example.mountbank.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllPostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);  // 根據 postId 查找帖子
}
