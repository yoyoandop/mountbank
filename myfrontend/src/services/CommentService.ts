import axios from 'axios';
import { Comment } from '../models/Comment';  // 引入 Comment 模型

const API_URL = 'https://localhost:8443/comments';

// 创建评论
export const createComment = async (postId: string, comment: Comment) => {
    // 確保從瀏覽器的 cookies 中發送請求，JWT token 會自動處理
    const response = await axios.post(
        `${API_URL}/add/${postId}`,  // 使用 postId 作為 URL 部分
        { content: comment.content },  // 发送评论内容
        {
            withCredentials: true,  // 確保請求攜帶跨域憑證
            headers: {
                'Content-Type': 'application/json',  // 設置內容類型為 JSON
            },
        }
    );
    return response.data;
};
