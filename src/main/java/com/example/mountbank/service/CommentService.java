package com.example.mountbank.service;

import com.example.mountbank.model.Comment;
import com.example.mountbank.model.Post;
import com.example.mountbank.model.User;
import com.example.mountbank.repository.CommentRepository;
import com.example.mountbank.repository.PostRepository;
import com.example.mountbank.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // 新增留言
    public Comment addComment(Long postId, String phoneNumber, String content) throws Exception {
        // 根據 phoneNumber 查找用戶
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found"); // 用戶未找到
        }
        User user = userOptional.get();

        // 根據 postId 查找發文
        Post post = postRepository.findByPostId(postId);
        if (post == null) {
            throw new Exception("Post not found"); // 發文未找到
        }

        // 創建並保存留言
        Comment comment = new Comment(user, post, content, LocalDateTime.now());
        return commentRepository.save(comment);
    }

    // 根據 Post ID 查詢留言
    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    // 根據 User ID 查詢留言
    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByUser_UserId(userId);
    }
}
