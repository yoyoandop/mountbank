// 修改 Post 類型，創建一個專門用於更新的 PostUpdate 類型
export interface Post {
    postId: number;
    content: string;
    image: string;
    createdAt?: string;
    phoneNumber: string; // 這裡會有 phoneNumber 屬性
}

// 用於更新時不需要 phoneNumber
export interface PostUpdate {
    content: string;
    image: string;
    createdAt?: string;
}
