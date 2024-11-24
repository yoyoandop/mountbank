<template>
  <div>
    <h1>Your Posts</h1>
    <div v-if="posts.length">
      <div v-for="post in posts" :key="post.postId" class="post-card">
        <p>{{ post.content }}</p>
        <!-- 顯示讚數 -->
        <p>Likes: {{ post.likeCount }}</p>
        <!-- 修正日期格式問題 -->
        <p>Posted on: {{ formatDate(post.createdAt) }}</p>
        <button @click="deletePost(post.postId)">Delete</button>
        <router-link :to="`/edit-post/${post.postId}`">Edit</router-link>
      </div>
    </div>
    <div v-else>
      <p>You have no posts yet.</p>
    </div>
    <router-link to="/create-post">Create a New Post</router-link>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { getPosts, deletePost } from '../services/PostService';
import { Post } from '../models/Post';

export default defineComponent({
  name: 'PostList',
  setup() {
    const posts = ref<Post[]>([]);

    const fetchPosts = async () => {
      posts.value = await getPosts();
    };

    const handleDelete = async (postId: number) => {
      await deletePost(postId);
      fetchPosts();
    };

    // 格式化日期的函數，處理 undefined 的情況
    const formatDate = (date: string | number | Date | undefined) => {
      if (date === undefined) {
        return 'No date available'; // 當日期為 undefined 時返回預設文本
      }
      const d = new Date(date);
      if (isNaN(d.getTime())) {
        return 'Invalid date'; // 如果日期無效，顯示“無效日期”
      }
      return d.toLocaleString(); // 轉換為可讀的日期字符串
    };

    onMounted(fetchPosts);

    return {
      posts,
      deletePost: handleDelete,
      formatDate,
    };
  },
});
</script>

<style scoped>
.post-card {
  border: 1px solid #ccc;
  padding: 16px;
  margin: 8px 0;
}
</style>
