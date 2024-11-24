<template>
  <div id="app">
    <nav>
      <router-link to="/" class="nav-link">Home</router-link>
      <router-link to="/create-post" class="nav-link">Create Post</router-link>
      <router-link to="/login" class="nav-link">Login</router-link>
      <router-link to="/register" class="nav-link">Register</router-link>
      <router-link to="/create-comment" class="nav-link">Create Comment</router-link>
      <router-link to="/logout" class="nav-link">Logout</router-link>
      <router-link to="/images" class="nav-link">ImagesList</router-link>
      <router-link to="/allpost" class="nav-link">AllPost</router-link>
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
  background-color: #333;
  padding: 10px;
  border-radius: 8px;
}

.nav-link {
  color: white !important;
  text-decoration: none;
  margin-right: 15px;
  font-weight: 500;
  padding: 8px 15px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-link:hover {
  background-color: #555;
}

.nav-link.active-link {
  background-color: #42b983;
  color: white !important;
  font-weight: bold;
}
</style>

