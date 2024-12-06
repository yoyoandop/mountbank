package com.example.mountbank.service;

import com.example.mountbank.model.Post;
import com.example.mountbank.model.User;
import com.example.mountbank.repository.PostRepository;
import com.example.mountbank.repository.UserRepository;
import com.example.mountbank.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository,LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;

    }

    public Post createPostByPhoneNumber(String phoneNumber, String content, String image) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with phone number: " + phoneNumber);
        }
        User user = userOptional.get();
        Post post = new Post(user, content, image, LocalDateTime.now());
        return postRepository.save(post);
    }

    //看該用戶所有發文
    public List<Post> getPostsByPhoneNumber(String phoneNumber) {
        return postRepository.findAllByUserPhoneNumber(phoneNumber);
    }

    // 更新發文因為我要一次操作兩張表所以要使用@Transactional 如果rowsUpdated == 0就要回滾
    //同時使用兩張表時 要馬全成功要馬全失敗
    @Transactional
    public void updatePost(Long postId, String content, String image) {
        int rowsUpdated = postRepository.updatePostContentAndImage(postId, content, image, LocalDateTime.now());
        if (rowsUpdated == 0) {
            throw new RuntimeException("Failed to update post or post not found");
        }
    }

    // 刪除發文
    @Transactional
    public void deletePost(Long postId) {

        int rowsDeleted = postRepository.deletePostById(postId);
        if (rowsDeleted == 0) {
            throw new RuntimeException("Post not found or you are not the author");
        }
    }

}
