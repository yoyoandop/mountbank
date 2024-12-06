import { createRouter, createWebHistory } from 'vue-router';
import PostList from '../components/PostList.vue';
import PostCreate from '../components/PostCreate.vue';
import PostEdit from '../components/PostEdit.vue';
import LoginForm from '../components/LoginForm.vue';
import RegisterForm from '../components/RegisterForm.vue';
import CommentCreate from '../components/CommentCreate.vue';
import Logout from '../components/Logout.vue';
import Images from '../components/ImagesList.vue';
import AllPost from '../components/AllPost.vue';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: PostList,  // 首页显示帖子列表
    },
    {
        path: '/create-post',
        name: 'CreatePost',
        component: PostCreate,  // 创建新帖子页面
    },
    {
        path: '/edit-post/:id',
        name: 'EditPost',
        component: PostEdit,  // 编辑帖子页面
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginForm,  // 登录页面
    },
    {
        path: '/register',
        name: 'Register',
        component: RegisterForm,  // 注册页面
    },
    {
        path: '/create-comment',
        name: 'CreateComment',
        component: CommentCreate,  // 创建评论页面
    },
    {
        path: '/logout',
        name: 'Logout',
        component: Logout,
    },
    {
        path: '/images',
        name: 'Images',
        component: Images,
    },
    {
        path: '/allpost',
        name: 'AllPost',
        component: AllPost,
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

export default router;
