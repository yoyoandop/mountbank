import axios from 'axios';

const API_URL = 'https://localhost:8443/api/users'; // 根據您的後端 URL 進行調整

export const UserService = {
    // 註冊功能
    async register(user: { username: string; email: string; password: string; coverImage: string; biography: string; phoneNumber: string }) {
        try {
            const response = await axios.post(`${API_URL}/register`, user, {
                withCredentials: false,  // 確保發送 cookie
            });
            return response.data;
        } catch (error) {
            console.error('Error registering user:', error);
            throw error;
        }
    },

    // 登錄功能
    async login(user: { phoneNumber: string; password: string }) {
        try {
            const response = await axios.post(`${API_URL}/login`, user, {
                withCredentials: true,  // 確保發送和接收 cookie
            });
            return response.data;
        } catch (error) {
            console.error('Error logging in:', error);
            throw error;
        }
    },

    // 登出功能
    async logout() {
        try {
            // 發送登出請求到後端
            const response = await axios.post(`${API_URL}/logout`, {}, {
                withCredentials: true  // 確保發送 cookie
            });
            return response.data; // 返回後端的回應
        } catch (error) {
            console.error('Error logging out:', error);
            throw error;
        }
    }
};
