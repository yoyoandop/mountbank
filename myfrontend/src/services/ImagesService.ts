// src/services/ImagesService.ts
import axios from 'axios';
import { Images } from '../models/Images';

const API_URL = 'https://localhost:8443/api/images';

export default class ImagesService {
    // 獲取所有圖片
    static async getAllImages(): Promise<Images[]> {
        try {
            const response = await axios.get(API_URL);
            return response.data;
        } catch (error) {
            console.error('Error fetching images', error);
            throw error;
        }
    }
}
