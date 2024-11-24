package com.example.mountbank.service;

import com.example.mountbank.model.Like;
import com.example.mountbank.model.Post;
import com.example.mountbank.model.User;
import com.example.mountbank.repository.AllPostRepository;
import com.example.mountbank.repository.LikeRepository;
import com.example.mountbank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllPostService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final AllPostRepository allPostRepository;

    public AllPostService(LikeRepository likeRepository, UserRepository userRepository, AllPostRepository allPostRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.allPostRepository = allPostRepository;
    }

    // 查詢所有的貼文
    public List<Post> getAllPosts() {
        return allPostRepository.findAll();  // 使用 Spring Data JPA 查詢所有帖子
    }

    // 點讚或取消點讚
    public boolean toggleLike(Long postId, String phoneNumber) throws Exception {
        // 1. 查找用戶
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }
        User user = userOptional.get();

        // 2. 查找帖子
        Post post = allPostRepository.findByPostId(postId);
        if (post == null) {
            throw new Exception("Post not found");
        }

        // 3. 確定是否已經點讚
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            // 如果點讚過，取消點讚
            likeRepository.delete(existingLike.get());
            post.decrementLikeCount(); // 減少讚數
            allPostRepository.save(post);  // 更新帖子
            return false;  // 表示取消點讚
        } else {
            // 如果未點讚，新增點讚
            Like like = new Like(user, post);
            likeRepository.save(like);
            post.incrementLikeCount(); // 增加讚數
            allPostRepository.save(post);  // 更新帖子
            return true;  // 表示新增點讚
        }
    }
}
