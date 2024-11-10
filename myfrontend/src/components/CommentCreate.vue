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
import { defineComponent, ref } from 'vue';
import { Comment } from '../models/Comment'; // 引入 Comment 模型
import { createComment } from '../services/CommentService'; // 引入服务
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'CommentCreate',
  setup() {
    const router = useRouter();

    // 定义评论对象
    const comment = ref<Comment>({
      content: '',
      number: 0,  // 默认值为 0，表示没有输入
    });

    // 创建评论处理方法
    const createCommentHandler = async () => {
      try {
        await createComment(comment.value.number.toString(), comment.value); // 发送请求时传递 postId 和 comment 对象
        router.push('/'); // 提交成功后跳转到首页
      } catch (error) {
        console.error('Failed to create comment:', error);
      }
    };

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
