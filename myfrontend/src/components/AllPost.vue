<template>
  <div class="all-posts">
    <div v-for="post in posts" :key="post.postId" class="post">
      <div class="post-header">
        <h3>{{ post.phoneNumber }}</h3> <!-- 顯示作者的電話號碼 -->
        <span>{{ post.createdAt }}</span> <!-- 顯示創建時間 -->
      </div>
      <div class="post-content">
        <p>{{ post.content }}</p> <!-- 顯示文章內容 -->
        <img v-if="post.image" :src="post.image" alt="Post Image" class="post-image" /> <!-- 顯示圖片 -->
      </div>
      <div class="post-footer">
        <span>{{ post.likeCount }} Likes</span>
        <button @click="toggleLike(post)">Like</button> <!-- 點讚按鈕 -->
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Post } from '../models/Post';
import { fetchAllPosts, likePost } from '../services/AllPostService'; // 匯入服務層函數

// 用於儲存所有帖子
const posts = ref<Post[]>([]);

// 頁面加載時，調用 API 來獲取所有帖子
onMounted(async () => {
  posts.value = await fetchAllPosts();
});

// 點讚或取消點讚
const toggleLike = async (post: Post) => {
  try {
    const response = await likePost(post.postId);
    if (response === 'Post liked') {
      post.likeCount += 1;
    } else if (response === 'Post unliked') {
      post.likeCount -= 1;
    }
  } catch (error) {
    console.error('Error toggling like:', error);
  }
};
</script>

<style scoped>
.all-posts {
  padding: 20px;
}

.post {
  border: 1px solid #ddd;
  padding: 10px;
  margin-bottom: 15px;
}

.post-header {
  display: flex;
  justify-content: space-between;
}

.post-content {
  margin: 10px 0;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.post-image {
  max-width: 100%;
  height: auto;
}
</style>
