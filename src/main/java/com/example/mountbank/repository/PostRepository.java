package com.example.mountbank.repository;

import com.example.mountbank.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {



    // 根據用戶的 phoneNumber 查詢所有發文
    List<Post> findByUser_PhoneNumber(String phoneNumber); // 假設 Post 類中的 user 屬性與 User 類存在 @ManyToOne 或 @OneToOne 關聯
    Post findByPostId(Long postId);

    @Query("SELECT p FROM Post p WHERE p.user.phoneNumber = :phoneNumber")
    List<Post> findAllByUserPhoneNumber(String phoneNumber);

    @Modifying
    @Query("UPDATE Post p SET p.content = :content, p.image = :image, p.createdAt = :createdAt WHERE p.postId = :postId")
    int updatePostContentAndImage(Long postId,String content,String image,LocalDateTime createdAt);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.postId = :postId")
    int deletePostById(Long postId);
}
