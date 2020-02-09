import Vue from 'vue'
import Router from 'vue-router'
import Login from "../views/Login";
import Home from "../views/Home";
Vue.use(Router);

export default new Router({
    routes:[
        {
        path: '/',
        name: 'Login',
        component: Login,
            hidden:true
    },  {
            path: '/home',
            name: 'Home',
            component: Home,
            hidden:true
    }
    ]
})

// 菜单栏采用数据中的加载