# Mountbank Project 
需要手動創建一個mountbank的mysql資料庫
步驟 1：創建新的 MySQL 連接（如果尚未創建）
打開 MySQL Workbench。

點擊左上角的 + 按鈕來創建一個新的連接。

設置你的連接信息，通常是：

Connection Name：隨便命名，例如mountbanktest。
Connection Method：選擇 Standard (TCP/IP)。
Hostname：通常是 localhost。
Port：默認是 3306。
Username：輸入你的 MySQL 用戶名，默認是 root。
Password：點擊 "Store in Vault..."，並輸入 MySQL 密碼。密碼設置成springboot
點擊 Test Connection 確保連接成功，然後點擊 OK 完成連接設置。

步驟 2：創建資料庫 mountbank
在 MySQL Workbench 中，打開你的新連接，並連接到 MySQL 伺服器。
點擊左側的 Query 按鈕（或者直接點擊上方的 New Query Tab）。
在新的查詢窗口中輸入以下 SQL 命令來創建資料庫：
sql
複製程式碼
CREATE DATABASE mountbank;
點擊 Execute 按鈕執行命令。
這樣就會創建一個名為 mountbank 的資料庫。

進入到Query後使用以下命令
USE mountbank;
SHOW TABLES;
SELECT * FROM users


第一個是後端註冊api
POST http://localhost:8080/api/users/register
Content-Type application/json 
{
  "username": "test",
  "email": "test123@example.com",
  "password": "test123",
  "coverImage": "imageUrlOrPath",
  "biography": "I am a new cute user.",
  "phoneNumber": "+123454321"
}
第二個是後端登入api
POST http://localhost:8080/api/users/login 
Content-Type application/json 
{
  "phoneNumber": "+123454321",
  "password": "test123"
}
然後返回jwt 令牌 jwt令牌裡面有包含phoneNumber



以下四個是關於posts 用jwt訪問登陸狀態才能訪問的節點  因為jwt有包含phoneNumber所以可以鎖定用戶

第三個api 接下來介紹第三個api 這個是用來查詢該名特定用戶過往所發過的貼文
GET http://localhost:8080/posts
Authorization Bearer +jwt令牌

這樣就會返回這個特定用戶的所有的貼文像是這樣
[
{
"postId": 3,
"user":{"userId": 2, "username": "test", "email": "test123@example.com", "password": "zl2SXYjNTGy7xlyQJMgYgiPlim0W64kGSHYM7JDp5ck=",…},
"content": "新的發文內容你好啊",
"image": "http://example.com/image.jpg",
"createdAt": "2024-11-09T18:09:23.043123"
},
{
"postId": 4,
"user":{"userId": 2, "username": "test", "email": "test123@example.com", "password": "zl2SXYjNTGy7xlyQJMgYgiPlim0W64kGSHYM7JDp5ck=",…},
"content": "新的發文內容我很好",
"image": "http://example.com/image.jpg",
"createdAt": "2024-11-09T18:09:42.18867"
},
{
"postId": 5,
"user":{"userId": 2, "username": "test", "email": "test123@example.com", "password": "zl2SXYjNTGy7xlyQJMgYgiPlim0W64kGSHYM7JDp5ck=",…},
"content": "新的發文內容我很可愛",
"image": "http://example.com/image.jpg",
"createdAt": "2024-11-09T18:09:50.239654"
}
]






第四個api 是用戶發貼文 因為jwt有包含phoneNumber所以知道是哪個用戶發文
POST http://localhost:8080/posts
Authorization Bearer +jwt令牌
Content-Type  application/json
{
  "content": "新的發文內容",
  "image": "http://example.com/image.jpg"
}
如果成功會返回200類似以下格式
{
"postId": 1,
"user":{
"userId": 8,
"username": "test01",
"email": "test0001@example.com",
"password": "GngLmPTMBZtKN4MlpavtzN/VNXm/mq/vbj9XQLcVmgA=",
"coverImage": "imageUrlOrPath",
"biography": "I am a new cute user",
"phoneNumber": "0315161713"
},
"content": "新的發文內容",
"image": "http://example.com/image.jpg",
"createdAt": "2024-11-09T17:44:35.1066818"
}



第五個api 是用戶編輯過往之前的發文 請先用第三個api Get取得該名用戶過往的發文的"postId": 數字
PUT http://localhost:8080/posts/1
Authorization Bearer +jwt令牌
Content-Type  application/json
{
  "content": "新的發文內容去做修改",
  "image": "http://example.com/image.jpg"
}

第六個api 先驗證post_id 是否為該名用戶發的如果是的話就給予刪除這則posts如果不是該名用戶發的文的話就無法刪除
DELETE  GET http://localhost:8080/posts/1
Authorization Bearer +jwt令牌


以下三個是關於comments 用jwt訪問登陸狀態才能訪問的節點  因為jwt有包含phoneNumber所以可以鎖定用戶

第七個api 創建留言 一樣需要登入 透過jwt知道是誰要發留言 透過路徑的數字知道是要把這留言發到哪個post上面
POST http://localhost:8080/comments/add/1
Authorization Bearer +jwt令牌
Content-Type application/json
{
  "content": "這是一條留言"
}
如果成功會返回200類似以下格式
{
"commentId": 2,
"user":{
"userId": 2,
"username": "test",
"email": "test123@example.com",
"password": "zl2SXYjNTGy7xlyQJMgYgiPlim0W64kGSHYM7JDp5ck=",
"coverImage": "imageUrlOrPath",
"biography": "I am a new cute user.",
"phoneNumber": "+123454321"
},
"post":{
"postId": 1,
"user":{"userId": 8, "username": "test01", "email": "test0001@example.com", "password": "GngLmPTMBZtKN4MlpavtzN/VNXm/mq/vbj9XQLcVmgA=",…},
"content": "新的發文內容",
"image": "http://example.com/image.jpg",
"createdAt": "2024-11-09T17:44:35.106682"
},
"content": "這是一條留言",
"createdAt": "2024-11-09T19:54:26.5291539"
}



前端先創一個frontend資料夾 做
PS C:\Users\POWER USER\OneDrive\桌面\github\Mountbank> npm create vite@latest
選擇vue+typescript

進入frontend資料夾做npm install
PS C:\Users\POWER USER\OneDrive\桌面\github\Mountbank> cd myfrontend
PS C:\Users\POWER USER\OneDrive\桌面\github\Mountbank\myfrontend> npm install

然後試試看
npm run dev

然後先做 frontend資料夾下載npm install axios
PS C:\Users\POWER USER\OneDrive\桌面\github\Mountbank\myfrontend> npm install axios


PS C:\Users\POWER USER\OneDrive\桌面\github\Mountbank\myfrontend> npm run dev就可以執行前端

想要發送給後端請求記得在後端controll層加上@CrossOrigin("http://localhost:3000")

vite.config.ts 配置成這樣後要記得在前端做npm install --save-dev @types/node
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

//const backendUrl = process.env.BACKEND_URL || 'http://backend:8080'; // 使用 Docker Compose 的後端服務名稱
const backendUrl = process.env.BACKEND_URL || 'http://localhost:8080';  //使用本地端開發環境localhost
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3000, // 指定前端服务运行的端口
    proxy: {
      '/api': {
        target: backendUrl, // 使用後端服務名稱
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''), // 重写路径
      },
    },
  },
});

安裝和配置 vue-router
npm install vue-router

在主文件中加載 vue-router

// src/main.ts

import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

const app = createApp(App);

app.use(router); // 註冊路由
app.mount('#app');

使用 router-view 顯示路由內容
App.vue 中使用 <router-view> 標籤來顯示匹配到的組件：
<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

下載
npm install @types/vue-router
npm install axios

確保在 tsconfig.json 中加入以下配置：
{
  "compilerOptions": {
    "module": "ESNext",
    "moduleResolution": "Node",
    "target": "ESNext",
    "jsx": "preserve",
    "esModuleInterop": true
  }
}


myfrontend/
├── node_modules/           # 依賴的套件
├── public/                 # 公共資源（如圖片、靜態文件等）
├── src/                    # 源代碼
│   ├── assets/             # 靜態資源
│   ├── components/LoginForm.vue         # Vue 組件
│   ├── components/PostCreate.vue         # Vue 組件
│   ├── components/PostEdit.vue         # Vue 組件
│   ├── components/PostList.vue         # Vue 組件
│   ├── components/RegisterForm.vue         # Vue 組件
│   ├── models-Post.ts
│   ├── models-Usr.ts
│   ├── router/index.ts             # 路由設定
│   ├── services/PostService.ts
│   ├── services/UserService.ts
│   ├── App.vue             # 主 Vue 組件
│   ├── main.ts             # 入口文件
├── index.html              # 主 HTML 頁面
├── vite.config.ts          # Vite 配置文件
├── package.json            # 項目配置和依賴

頁面F12 Application
localStorage 有jwt key鍵儲存jwt 這可以用來發送給後端用戶就不用再手動輸入jwt了


前端的jwt要傳到後端需要經過 Spring Security配置
因為你使用了 Spring Security 並且 JWT 解析過程是在 JwtRequestFilter 中處理，這就需要正確配置 Spring Security 來允許跨域請求並支持帶有 JWT 的請求。這樣，當前端發送帶有 JWT 的請求時，後端能夠正確處理認證和授權邏輯。以下是一些重點和建議的配置方式，確保 Spring Security 能夠正常處理 JWT 請求：

1. 配置 Spring Security 的 WebSecurityConfigurerAdapter
需要確保配置允許跨域請求並正確配置 JwtRequestFilter。

. 前端 Axios 配置
確認你在前端的 Axios 請求中設置了 withCredentials: true，這樣前端會攜帶跨域的憑證（JWT）。


