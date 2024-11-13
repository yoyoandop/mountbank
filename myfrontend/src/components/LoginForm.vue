<template>
  <div>
    <h2>Login</h2>
    <form @submit.prevent="loginUser">
      <div>
        <label for="phoneNumber">Phone Number</label>
        <input type="text" id="phoneNumber" v-model="user.phoneNumber" required />
      </div>
      <div>
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Login</button>
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
      phoneNumber: '',
      password: '',
    });
    const message = ref('');

    const loginUser = async () => {
      try {
        const response = await UserService.login(user.value);
        message.value = 'Login successful!';
      } catch (error) {
        message.value = 'Login failed!';
      }
    };

    return { user, loginUser, message };
  },
};
</script>
