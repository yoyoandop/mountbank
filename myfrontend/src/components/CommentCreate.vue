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
import { Comment } from '../models/Comment';
import { createComment } from '../services/CommentService';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'CommentCreate',
  setup() {
    const router = useRouter();
    const comment = ref<Comment>({
      content: '',
      number: 0,
    });

    const createCommentHandler = async () => {
      try {
        await createComment(comment.value.number.toString(), comment.value);
        router.push('/');
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
