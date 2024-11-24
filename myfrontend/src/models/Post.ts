export interface Post {
    postId: number;       // 文章 ID
    content: string;      // 文章內容
    image: string;        // 圖片 URL
    createdAt?: string;   // 創建時間
    phoneNumber: string;  // 作者電話號碼
    likeCount: number;    // 讚數
}

export interface PostUpdate {
    content: string;
    image: string;
    createdAt?: string;
}
