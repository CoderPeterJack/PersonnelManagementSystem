// 定义vuex
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        //路由数组，服务器加载的菜单项都放在这里面
        routes:[]
    },
    mutations:{
        // 传递参数时可以只传递data
        initRoutes(state,data){
            state.routes=data;
        }
    },
    actions:{

    }
})