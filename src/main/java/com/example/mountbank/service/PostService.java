package com.example.mountbank.service;

import com.example.mountbank.model.Post;
import com.example.mountbank.model.User;
import com.example.mountbank.repository.PostRepository;
import com.example.mountbank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // Constructor injection
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 通過 userId 創建發文
    public Post createPost(Long userId, String content, String image) {
        // 查找使用者
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {  // 使用 isEmpty() 替代 isPresent()
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Post post = new Post(user, content, image, LocalDateTime.now());
        return postRepository.save(post);
    }

    // 通過 phoneNumber 創建發文
    public Post createPostByPhoneNumber(String phoneNumber, String content, String image) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {  // 使用 isEmpty() 替代 isPresent()
            throw new RuntimeException("User not found with phone number: " + phoneNumber);
        }

        User user = userOptional.get();
        return createPost(user.getUserId(), content, image);
    }

    // 獲取該用戶所有發文
    public List<Post> getPostsByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {  // 使用 isEmpty() 替代 isPresent()
            throw new RuntimeException("User not found with phone number: " + phoneNumber);
        }

        User user = userOptional.get();
        return postRepository.findByUser_UserId(user.getUserId());
    }

    // 更新發文
    public Post updatePost(Long postId, String content, String image, String phoneNumber) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {  // 使用 isEmpty() 替代 isPresent()
            throw new RuntimeException("Post not found");
        }

        Post post = postOptional.get();

        // 根據 phoneNumber 查找 User
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {  // 使用 isEmpty() 替代 isPresent()
            throw new RuntimeException("User not found with phone number: " + phoneNumber);
        }

        User user = userOptional.get();
        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("You are not the author of this post");
        }

        post.setContent(content);
        post.setImage(image);
        post.setCreatedAt(LocalDateTime.now()); // 可選，根據需求決定是否更新 createdAt
        return postRepository.save(post);
    }

    // 刪除發文
    public void deletePost(Long postId, String phoneNumber) {
        // 根據 phoneNumber 查找用戶
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with phone number: " + phoneNumber);
        }

        User user = userOptional.get();

        // 查找該帖子
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        Post post = postOptional.get();

        // 檢查當前用戶是否是該帖子的作者
        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("You are not the author of this post");
        }

        // 刪除帖子
        postRepository.delete(post);
    }

}
