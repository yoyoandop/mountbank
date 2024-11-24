package com.example.mountbank.controller;

import com.example.mountbank.model.Post;
import com.example.mountbank.service.AllPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(
        origins = "http://localhost:3000",
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class AllPostController {

    private final AllPostService allPostService;

    @Autowired
    public AllPostController(AllPostService allPostService) {
        this.allPostService = allPostService;
    }

    // 查詢所有的帖子
    @GetMapping
    public List<Post> getAllPosts() {
        return allPostService.getAllPosts();  // 使用服務層方法獲取所有帖子
    }

    // 點讚或取消點讚
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long postId) {
        // 取得用戶的 phoneNumber
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();

        try {
            boolean isLiked = allPostService.toggleLike(postId, phoneNumber);
            String message = isLiked ? "Post liked" : "Post unliked";
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
