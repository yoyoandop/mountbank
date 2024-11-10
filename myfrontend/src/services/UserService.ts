import axios from 'axios';

const API_URL = 'http://localhost:8080/api/users'; // Adjust based on your backend URL

export const UserService = {
    async register(user: { username: string; email: string; password: string; coverImage: string; biography: string; phoneNumber: string }) {
        try {
            const response = await axios.post(`${API_URL}/register`, user);
            return response.data;
        } catch (error) {
            console.error('Error registering user:', error);
            throw error;
        }
    },

    async login(user: { phoneNumber: string; password: string }) {
        try {
            const response = await axios.post(`${API_URL}/login`, user);
            return response.data;
        } catch (error) {
            console.error('Error logging in:', error);
            throw error;
        }
    },
};
