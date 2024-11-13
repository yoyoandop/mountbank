<template>
  <div>
    <h1>Create New Post</h1>
    <form @submit.prevent="createPost">
      <div>
        <label for="content">Content</label>
        <textarea v-model="content" id="content" required></textarea>
      </div>
      <div>
        <label for="image">Image URL (optional)</label>
        <input v-model="image" id="image" type="text" />
      </div>
      <button type="submit">Create Post</button>
    </form>
    <router-link to="/posts">Back to Posts</router-link>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { createPost } from '../services/PostService';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'PostCreate',
  setup() {
    const content = ref('');
    const image = ref('');
    const router = useRouter();

    const handleCreatePost = async () => {
      const newPost = {
        content: content.value,
        image: image.value,
      };

      await createPost(newPost);
      router.push('/posts');
    };

    return {
      content,
      image,
      createPost: handleCreatePost,
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
