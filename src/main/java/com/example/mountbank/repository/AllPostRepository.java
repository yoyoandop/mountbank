package com.example.mountbank.repository;

import com.example.mountbank.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.query.Procedure;
public interface AllPostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);  // 根據 postId 查找帖子

    @Procedure(name = "GetAllPosts")
    List<Post> getAllPosts();
}
