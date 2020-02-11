import Vue from 'vue'
import App from './App.vue'
import router from './router'
// 引入store
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import {postRequest} from "./utils/api";
import {postKeyValueRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {initMenu} from "./utils/menus";
// 引用样式
import 'font-awesome/css/font-awesome.min.css'
Vue.prototype.postRequest = postRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.getRequest = getRequest;

Vue.use(ElementUI);

Vue.config.productionTip=false;
//相当于后端的拦截器
router.beforeEach((to, from,  next) => {
  //如果是登陆页面
  if (to.path == '/'){
    next();
  }else{
    //如果不是登陆页，判断是否登录
    //如果登录
    if (window.sessionStorage.getItem("user")){
      //如果去的是其他页面,初始化菜单，如果store里面有数据就不需要网络请求
      //服务端数据
      initMenu(router,store);
      next();
    }else{
      console.log(to);
      //如果没有登录
      next('/?redirect='+to.path);
    }
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
