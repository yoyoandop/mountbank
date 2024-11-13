package com.example.mountbank.controller;

import com.example.mountbank.model.Comment;
import com.example.mountbank.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comments")
@CrossOrigin(
        origins = "http://localhost:3000",
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 新增留言
    @PostMapping("/add/{postId}")
    public ResponseEntity<?> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest,
            HttpServletRequest request) {

        // 從 Authentication 中提取用戶的 phoneNumber
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName(); // 這裡直接用 getName() 獲取用戶的 phoneNumber

        try {
            // 調用 Service 層來新增留言
            Comment comment = commentService.addComment(postId, phoneNumber, commentRequest.getContent());

            return new ResponseEntity<>(comment, HttpStatus.CREATED);

        } catch (Exception e) {
            // 根據錯誤的原因返回相應的訊息
            String errorMessage = e.getMessage();

            if ("User not found".equals(errorMessage)) {
                return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED); // 用戶未找到
            } else if ("Post not found".equals(errorMessage)) {
                return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND); // 發文未找到
            } else {
                return new ResponseEntity<>("Failed to create comment", HttpStatus.INTERNAL_SERVER_ERROR); // 其他錯誤
            }
        }
    }

    // CommentRequest 類別用來映射評論請求中的內容
    public static class CommentRequest {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

