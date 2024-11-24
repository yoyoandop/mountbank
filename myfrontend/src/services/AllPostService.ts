import axios from 'axios';
import { Post } from '../models/Post';

// 假設後端運行在 https://localhost:8443/api/posts
const API_URL = 'https://localhost:8443/api/posts';

// 獲取所有帖子
export const fetchAllPosts = async (): Promise<Post[]> => {
    try {
        const response = await axios.get(API_URL, {
            withCredentials: true, // 確保請求攜帶跨域憑證
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching posts:', error);
        throw error; // 必須處理錯誤
    }
};

// 點讚或取消點讚
export const likePost = async (postId: number): Promise<string> => {
    try {
        // 發送請求至 `/api/posts/{postId}/like` 路徑
        const response = await axios.post(`${API_URL}/${postId}/like`, {}, {
            withCredentials: true, // 確保請求攜帶跨域憑證
        });
        return response.data; // 返回後端返回的訊息（如 "Post liked" 或 "Post unliked"）
    } catch (error) {
        console.error('Error liking/unliking post:', error);
        throw error; // 必須處理錯誤
    }
};
