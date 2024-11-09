package com.example.mountbank.controller;

import com.example.mountbank.model.Post;
import com.example.mountbank.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // Constructor-based injection
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 新增發文
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, Authentication authentication) {
        // 從 authentication 獲取 phoneNumber
        String phoneNumber = authentication.getName(); // 假設 authentication.getName() 返回 phoneNumber

        Post createdPost = postService.createPostByPhoneNumber(phoneNumber, post.getContent(), post.getImage());
        return ResponseEntity.ok(createdPost);
    }

    // 列出該用戶所有發文
    @GetMapping
    public ResponseEntity<List<Post>> getPostsByUser(Authentication authentication) {
        // 從 authentication 獲取 phoneNumber
        String phoneNumber = authentication.getName(); // 假設 authentication.getName() 返回 phoneNumber

        List<Post> posts = postService.getPostsByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(posts);
    }

    // 編輯發文
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post, Authentication authentication) {
        // 從 Authentication 中獲取 phoneNumber
        String phoneNumber = authentication.getName();

        // 調用 PostService 層的 updatePost 方法，執行更新邏輯
        Post updatedPost = postService.updatePost(id, post.getContent(), post.getImage(), phoneNumber);

        return ResponseEntity.ok(updatedPost);
    }

    // 刪除發文
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        // 從 authentication 中獲取 phoneNumber
        String phoneNumber = authentication.getName();

        // 呼叫 PostService 刪除發文，並確保該用戶是該帖子的作者
        postService.deletePost(id, phoneNumber);

        return ResponseEntity.ok().build();
    }

}
