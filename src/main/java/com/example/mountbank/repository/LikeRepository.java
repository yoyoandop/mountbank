package com.example.mountbank.repository;

import com.example.mountbank.model.Like;
import com.example.mountbank.model.Post;
import com.example.mountbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);  // 查找特定用户對特定帖子的點讚
    public void deleteByPost(Post post);
}
