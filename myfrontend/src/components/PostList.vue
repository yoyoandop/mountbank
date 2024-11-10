<template>
  <div>
    <h1>Your Posts</h1>
    <div v-if="posts.length">
      <div v-for="post in posts" :key="post.postId" class="post-card">
        <p>{{ post.content }}</p>
        <p>Posted on: {{ new Date(post.createdAt).toLocaleString() }}</p>
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
import { Post } from '../models/Post';  // Import the Post model

export default defineComponent({
  name: 'PostList',
  setup() {
    const posts = ref<Post[]>([]);  // Define the posts as an array of Post objects
    const token = localStorage.getItem('jwt'); // Assuming JWT is saved in local storage

    // Fetch posts on mount
    const fetchPosts = async () => {
      if (token) {
        posts.value = await getPosts(token);
      }
    };

    // Delete a post
    const handleDelete = async (postId: number) => {
      if (token) {
        await deletePost(postId, token);
        fetchPosts(); // Refresh the posts list
      }
    };

    onMounted(fetchPosts);

    return {
      posts,
      deletePost: handleDelete,
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
