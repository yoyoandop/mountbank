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
import { createPost } from '../services/PostService'; // Import the service to handle post creation
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'PostCreate',
  setup() {
    const content = ref(''); // Post content
    const image = ref(''); // Post image URL (optional)
    const router = useRouter(); // For navigation after creating post
    const token = localStorage.getItem('jwt'); // Get the JWT token from local storage

    const handleCreatePost = async () => {
      if (token) {
        // Create the new post object without createdAt
        const newPost = {
          content: content.value,
          image: image.value,
        };

        // Call the createPost function from the service to send the data to the backend
        await createPost(newPost, token);

        // Redirect to the posts list page after creating the post
        router.push('/posts');
      }
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
