<template>
  <div>
    <h1>Create Comment</h1>
    <form @submit.prevent="createComment">
      <div>
        <label for="content">Comment Content:</label>
        <textarea v-model="comment.content" id="content" required></textarea>
      </div>
      <div>
        <label for="number">Post ID (Number):</label>
        <input type="number" v-model="comment.number" id="number" required />
      </div>
      <button type="submit">Create Comment</button>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted, onUnmounted } from 'vue';
import { Comment } from '../models/Comment';
import { createComment } from '../services/CommentService';
import { useRouter } from 'vue-router';
import NotificationService from '../services/NotificationService';

export default defineComponent({
  name: 'CommentCreate',
  setup() {
    const router = useRouter();
    const comment = ref<Comment>({
      content: '',
      number: 0,
    });

    // 创建评论并提交
    const createCommentHandler = async () => {
      try {
        await createComment(comment.value.number.toString(), comment.value);
        router.push('/'); // 重定向到主页
      } catch (error) {
        console.error('Failed to create comment:', error);
      }
    };

    // 在 mounted 时连接 WebSocket
    onMounted(() => {
      NotificationService.connect();
    });

    // 在 unmounted 时断开 WebSocket
    onUnmounted(() => {
      NotificationService.disconnect();
    });

    return {
      comment,
      createComment: createCommentHandler,
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
