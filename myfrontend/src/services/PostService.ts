import axios from 'axios';

// 假設後端運行在 localhost:8080
const API_URL = 'http://localhost:8080/posts';

// 取得所有的帖子
export const getPosts = async (token: string) => {
    const response = await axios.get(API_URL, {
        headers: {
            Authorization: `Bearer ${token}`, // 發送 JWT token 用於認證
        },
        withCredentials: true, // 添加此行以確保請求攜帶跨域憑證
    });
    return response.data;
};

// 創建新的帖子
export const createPost = async (
    post: { content: string; image?: string },
    token: string
) => {
    const response = await axios.post(API_URL, post, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
        withCredentials: true, // 添加此行以確保請求攜帶跨域憑證
    });
    return response.data;
};

// 更新已存在的帖子
export const updatePost = async (
    postId: number,
    post: { content: string; image?: string },
    token: string
) => {
    const response = await axios.put(`${API_URL}/${postId}`, post, {
        headers: {
            Authorization: `Bearer ${token}`, // 發送 JWT token 用於認證
        },
        withCredentials: true, // 添加此行以確保請求攜帶跨域憑證
    });
    return response.data;
};

// 刪除帖子
export const deletePost = async (postId: number, token: string) => {
    await axios.delete(`${API_URL}/${postId}`, {
        headers: {
            Authorization: `Bearer ${token}`, // 發送 JWT token 用於認證
        },
        withCredentials: true, // 添加此行以確保請求攜帶跨域憑證
    });
};
