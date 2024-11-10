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
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div>
        <label for="phoneNumber">Phone Number</label>
        <input type="text" id="phoneNumber" v-model="user.phoneNumber" required />
      </div>
      <div>
        <label for="biography">Biography</label>
        <textarea id="biography" v-model="user.biography"></textarea>
      </div>
      <div>
        <label for="coverImage">Cover Image URL</label>
        <input type="text" id="coverImage" v-model="user.coverImage" />
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
      coverImage: '',
      biography: '',
      phoneNumber: '',
    });
    const message = ref('');

    const registerUser = async () => {
      try {
        const response = await UserService.register(user.value);
        message.value = response.message;
      } catch (error) {
        message.value = 'Registration failed!';
      }
    };

    return { user, registerUser, message };
  },
};
</script>
