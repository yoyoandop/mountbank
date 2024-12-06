import axios from 'axios';

// 假設後端運行在 https://localhost:8443
const API_URL = 'https://localhost:8443/posts';

// 取得所有的帖子
export const getPosts = async () => {
    const response = await axios.get(API_URL, {
        withCredentials: true,
    });
    return response.data;
};

// 創建新的帖子
export const createPost = async (
    post: { content: string; image?: string }
) => {
    const response = await axios.post(API_URL, post, {
        withCredentials: true,
    });
    return response.data;
};

// 更新已存在的帖子
export const updatePost = async (
    postId: number,
    post: { content: string; image?: string }
) => {
    const response = await axios.put(`${API_URL}/${postId}`, post, {
        withCredentials: true,
    });
    return response.data;
};

// 刪除帖子
export const deletePost = async (postId: number) => {
    await axios.delete(`${API_URL}/${postId}`, {
        withCredentials: true,
    });
};
