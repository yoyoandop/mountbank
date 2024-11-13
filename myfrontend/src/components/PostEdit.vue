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
import { Post, PostUpdate } from '../models/Post';

export default defineComponent({
  name: 'PostEdit',
  setup() {
    const content = ref('');
    const image = ref('');
    const route = useRoute();
    const router = useRouter();
    const postId = String(route.params.id);

    const fetchPost = async () => {
      const posts: Post[] = await getPosts(); // 確保 posts 被正確類型化
      const post = posts.find((p: Post) => p.postId === parseInt(postId));
      if (post) {
        content.value = post.content;
        image.value = post.image;
      }
    };

    const handleUpdatePost = async () => {
      const updatedPost: PostUpdate = {
        content: content.value,
        image: image.value,
        createdAt: new Date().toISOString(),
      };

      await updatePost(parseInt(postId), updatedPost);
      router.push('/posts');
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
