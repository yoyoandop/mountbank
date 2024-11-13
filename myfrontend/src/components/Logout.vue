<template>
  <div>
    <button @click="logoutUser">Log Out</button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useRouter } from 'vue-router';
import { UserService } from '../services/UserService';

export default defineComponent({
  name: 'Logout',
  setup() {
    const router = useRouter();

    const logoutUser = async () => {
      try {
        await UserService.logout();
        router.push('/login');
      } catch (error) {
        console.error('Logout failed', error);
      }
    };

    return {
      logoutUser,
    };
  }
});
</script>
