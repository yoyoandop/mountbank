<template>
  <div>
    <h2>Register</h2>
    <form @submit.prevent="registerUser">
      <div>
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required />
      </div>
      <div>
        <label for="email">Email</label>
        <input type="email" id="email" v-model="user.email" required />
      </div>
      <div>
        <label for="phoneNumber">Phone Number</label>
        <input type="text" id="phoneNumber" v-model="user.phoneNumber" required />
      </div>
      <div>
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div>
        <label for="biography">Biography</label>
        <textarea id="biography" v-model="user.biography" placeholder="Tell us about yourself" required></textarea>
      </div>
      <div>
        <label for="coverImage">Cover Image URL</label>
        <input type="text" id="coverImage" v-model="user.coverImage" placeholder="URL of your cover image" />
      </div>
      <button type="submit">Register</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { UserService } from '../services/UserService';

export default {
  setup() {
    const user = ref({
      username: '',
      email: '',
      password: '',
      phoneNumber: '',
      biography: '',
      coverImage: '', // 新增的字段
    });
    const message = ref('');

    const registerUser = async () => {
      try {
        // 註冊時傳送包括 biography 和 coverImage 的 user 對象
        await UserService.register(user.value);
        message.value = 'Registration successful!';
      } catch (error) {
        message.value = 'Registration failed!';
      }
    };

    return { user, registerUser, message };
  },
};
</script>
