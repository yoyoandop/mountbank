import { createRouter, createWebHistory } from 'vue-router';
import PostList from '../components/PostList.vue'; // 首页显示的帖子列表
import PostCreate from '../components/PostCreate.vue'; // 创建帖子页面
import PostEdit from '../components/PostEdit.vue'; // 编辑帖子页面
import LoginForm from '../components/LoginForm.vue'; // 登录页面
import RegisterForm from '../components/RegisterForm.vue'; // 注册页面

// 路由配置
const routes = [
    {
        path: '/',
        name: 'Home', // 首页路由
        component: PostList, // 渲染 PostList 组件
    },
    {
        path: '/create-post',
        name: 'PostCreate', // 创建帖子页面路由
        component: PostCreate, // 渲染 PostCreate 组件
    },
    {
        path: '/edit-post/:id',
        name: 'PostEdit', // 编辑帖子页面路由
        component: PostEdit, // 渲染 PostEdit 组件
    },
    {
        path: '/login',
        name: 'Login', // 登录页面路由
        component: LoginForm, // 渲染 LoginForm 组件
    },
    {
        path: '/register',
        name: 'Register', // 注册页面路由
        component: RegisterForm, // 渲染 RegisterForm 组件
    },
];

// 创建路由实例
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes, // 使用配置好的路由
});

export default router; // 导出路由实例
