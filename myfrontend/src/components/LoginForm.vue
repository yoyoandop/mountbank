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
    let socket: WebSocket | null = null;

    const connectWebSocket = () => {
      // 创建 WebSocket 连接
      socket = new WebSocket('wss://localhost:8443/notifications'); // 端点与后端配置一致
      socket.onopen = () => {
        console.log('WebSocket connection established');
      };
      socket.onmessage = (event) => {
        console.log('Received message:', event.data);
      };
      socket.onerror = (error) => {
        console.error('WebSocket error:', error);
      };
      socket.onclose = () => {
        console.log('WebSocket connection closed');
      };
    };

    const loginUser = async () => {
      try {
        const response = await UserService.login(user.value);
        message.value = 'Login successful!';
        connectWebSocket(); // 登录成功后建立 WebSocket 连接
      } catch (error) {
        message.value = 'Login failed!';
      }
    };

    return { user, loginUser, message };
  },
};
</script>
