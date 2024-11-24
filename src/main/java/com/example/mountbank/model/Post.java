package com.example.mountbank.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0; // 新增讚數屬性，默認為 0

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Like> likes;
    // Default constructor
    public Post() {}

    // All arguments constructor
    public Post(User user, String content, String image, LocalDateTime createdAt) {
        this.user = user;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.likeCount = 0; // 默認初始化
    }

    // Getter and Setter methods

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount--;
    }
}
