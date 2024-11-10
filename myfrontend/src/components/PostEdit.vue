<template>
  <div>
    <h1>Edit Post</h1>
    <form @submit.prevent="updatePost">
      <div>
        <label for="content">Content</label>
        <textarea v-model="content" id="content" required></textarea>
      </div>
      <div>
        <label for="image">Image URL (optional)</label>
        <input v-model="image" id="image" type="text" />
      </div>
      <button type="submit">Update Post</button>
    </form>
    <router-link to="/posts">Back to Posts</router-link>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getPosts, updatePost } from '../services/PostService';
import { PostUpdate } from '../models/Post'; // 使用 PostUpdate 類型

export default defineComponent({
  name: 'PostEdit',
  setup() {
    const content = ref('');
    const image = ref('');
    const route = useRoute();
    const router = useRouter();
    const token = localStorage.getItem('jwt');
    const postId = String(route.params.id); // 確保 postId 為字符串

    // 取得要編輯的帖子
    const fetchPost = async () => {
      if (token && postId) {
        const posts = await getPosts(token);
        const post = posts.find((p) => p.postId === parseInt(postId)); // 比對 postId
        if (post) {
          content.value = post.content;
          image.value = post.image || '';
        }
      }
    };

    // 更新帖子
    const handleUpdatePost = async () => {
      if (token && postId) {
        const updatedPost: PostUpdate = {
          content: content.value,
          image: image.value,
          createdAt: new Date().toISOString(), // 以當前時間為創建時間
        };

        await updatePost(parseInt(postId), updatedPost, token); // 更新帖子
        router.push('/posts'); // 更新後返回帖子列表頁
      }
    };

    onMounted(fetchPost);

    return {
      content,
      image,
      updatePost: handleUpdatePost,
    };
  },
});
</script>

<style scoped>
form {
  max-width: 400px;
  margin: 0 auto;
}

button {
  margin-top: 10px;
}
</style>
