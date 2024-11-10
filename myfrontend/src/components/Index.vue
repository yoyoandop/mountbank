<template>
  <div>
    <h1>Welcome to the App</h1>
    <p>Select an option below:</p>
    <ul>
      <li><router-link to="/posts">View Posts</router-link></li>
      <li><router-link to="/create-post">Create a Post</router-link></li>
      <li><router-link to="/edit-post/1">Edit a Post</router-link></li> <!-- Example edit link -->
      <li><router-link to="/register">Register</router-link></li>
      <li><router-link to="/login">Login</router-link></li>
    </ul>
  </div>
</template>

<script lang="ts">
export default {
  name: 'Index',
};
</script>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin-bottom: 10px;
}
</style>
