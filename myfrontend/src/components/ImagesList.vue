<template>
  <div>
    <h1>圖片列表</h1>
    <div v-if="images.length === 0">
      <p>目前沒有圖片可顯示。</p>
    </div>
    <div v-else>
      <div v-for="image in images" :key="image.id" class="image-item">
        <h3>{{ image.bookName }}</h3>
        <img :src="image.url" :alt="image.imageName" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import ImagesService from '../services/ImagesService';
import { Images } from '../models/Images';

export default defineComponent({
  name: 'ImagesList',
  setup() {
    const images = ref<Images[]>([]);

    // 在組件加載時獲取圖片
    onMounted(async () => {
      try {
        images.value = await ImagesService.getAllImages();
      } catch (error) {
        console.error('Error loading images', error);
      }
    });

    return {
      images,
    };
  },
});
</script>

<style scoped>
.image-item {
  margin-bottom: 20px;
}

.image-item img {
  width: 100%;
  height: auto;
}
</style>
