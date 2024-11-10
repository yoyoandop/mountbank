import axios from 'axios';
import { Comment } from '../models/Comment';  // 引入 Comment 模型

const API_URL = 'http://localhost:8080/comments';

// 创建评论
export const createComment = async (postId: string, comment: Comment) => {
    // 从 localStorage 获取 JWT
    const token = localStorage.getItem('jwt'); // 假设 JWT 存储在 localStorage 中
    if (!token) {
        throw new Error('No JWT token found');
    }

    // 发送 POST 请求，创建评论
    const response = await axios.post(
        `${API_URL}/add/${postId}`,  // 使用 postId（即 number）作为 URL 部分
        { content: comment.content },  // 发送评论内容
        {
            headers: {
                Authorization: `Bearer ${token}`, // 设置 Authorization 头，Bearer 后跟 token
                'Content-Type': 'application/json',  // 设置内容类型为 JSON
            },
        }
    );
    return response.data;
};
