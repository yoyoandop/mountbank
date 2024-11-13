<template>
  <div id="app">
    <nav>
      <!-- 这是导航栏, 你可以根据需求选择链接 -->
      <router-link to="/" class="nav-link">Home</router-link>
      <router-link to="/create-post" class="nav-link">Create Post</router-link>
      <router-link to="/login" class="nav-link">Login</router-link>
      <router-link to="/register" class="nav-link">Register</router-link>
      <router-link to="/create-comment" class="nav-link">Create Comment</router-link>  <!-- 新增：創建留言 -->
      <router-link to="/logout" class="nav-link">Logout</router-link>  <!-- 新增：創建留言 -->
    </nav>
    <router-view></router-view>
  </div>
</template>

<script lang="ts">
export default {
  name: 'App',
};
</script>

<style scoped>
/* 基本的导航栏样式 */
nav {
  margin-bottom: 20px;
  background-color: #333;  /* 设置导航栏背景颜色 */
  padding: 10px;
  border-radius: 8px;  /* 圆角效果 */
}

.nav-link {
  color: white !important;  /* 确保字体颜色为白色，并使用!important增加优先级 */
  text-decoration: none;  /* 移除默认的下划线 */
  margin-right: 15px;
  font-weight: 500;
  padding: 8px 15px;
  border-radius: 4px;  /* 圆角效果 */
  transition: background-color 0.3s;  /* 平滑过渡效果 */
}

.nav-link:hover {
  background-color: #555;  /* 悬停显示背景颜色 */
  color: white !important;  /* 确保悬停时文本颜色仍为白色 */
}

.nav-link.active-link {
  background-color: #42b983;  /* 当前页面显示绿色背景 */
  color: white !important;  /* 字体颜色变为白色 */
  font-weight: bold;  /* 当前页面字体加粗 */
}
</style>
