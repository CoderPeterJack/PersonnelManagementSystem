<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">
                    微人事
                </div>
                <el-dropdown class="userInfo" @command="commandHandler">
  <span class="el-dropdown-link">
<!--      :为v:mode的简写-->
    {{user.name}}<i><img :src="user.userface" alt=""> </i>
  </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="userInfo">个人中心</el-dropdown-item>
                        <el-dropdown-item command="setting">设置</el-dropdown-item>
                        <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>
            <el-container>
<!--                vue-router模式就不用写点击事件-->
                <el-aside width="200px">
                    <el-menu router unique-opened>
<!--                    这里面拿到的就是routes数组，动态渲染到左边菜单-->
                    <!--                            为了将router-view的name与这里对应，统一-->
                    <!--                            需要v-for进行遍历-->
<!--                    v-if什么时候渲染-->
<!--                        routes用于显示store里面的数据，指的就是下面computed的routes-->
<!--                        遍历store的state里面的routes-->
<!--                        :index="index+''其保证只展开一个-->
                    <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
                        <template slot="title">
<!--                            一级菜单从数据库中加载图标，参考官网font-awesome，但是得先安装图标库-->
                            <i style="color: #409eff;margin-right:15px" :class="item.iconCls"></i>
                            <span>{{item.name}}</span>
                        </template>
<!--                        要跳转的路径-->
<!--                        因为是变量加:-->
                            <el-menu-item :index="child.path"
                                          v-for="(child,indexj) in item.children" :key="indexj">{{child.name}}
                            </el-menu-item>
                    </el-submenu>
                </el-menu></el-aside>
<!--                element-ui的面包屑-->
                <el-main>
<!--                    现在有两个route-view，你怎么决定打开谁，
一个是app中，则需要重新组织数据结构-->
                    <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
                        欢迎来到微人事！
                    </div>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

//用户信息存在sessionStore里
<script>
    export default {
        name: "Home",
        data(){
            return{
                //将字符串转成json对象
                user:JSON.parse(window.sessionStorage.getItem("user"))
            }
        },
        //采用computed属性列出store的数据
        computed:{
            routes(){
                return this.$store.state.routes;
            }
        },
            methods:{
            commandHandler(cmd){
                if (cmd=='logout'){
                    this.$confirm('此操作将注销登录, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        //调用封装的方法，注销登录
                        this.getRequest("logout");
                        //登录数据清空，跳转到登录页面
                        window.sessionStorage.removeItem("user");
                        //注销登入时必须清空store里面的数据，目的时保证别人登入不会重新之前别人的store数据,方法是给routes一个[]
                        this.$store.commit('initRoutes',[]);
                        this.$router.replace("/");
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消操作'
                        });
                    });
                }
            }
        }
    }
</script>

<style>
    .homeWelcome{
        text-align: center;
        font-size: 60px;
        font-family: 华文行楷;
        color: #409eff;
        padding-top: 50px;
    }
.homeHeader{
    background-color: #409eff;
    display: flex;
    align-content: center;
    justify-content: space-between;
    padding: 0px 15px;
    box-sizing:border-box;
}
.homeHeader .title{
    font-family: 华文行楷;
    font-size: 30px;
    color: #ffffff;
}

    .homeHeader .userInfo{
        /*//手指*/
        cursor: pointer;
    }
    .el-dropdown-link img{
        width: 48px;
        height: 48px;
        border-radius:24px;
        margin-left:8px;
    }
    /*设置文本居中*/
    .el-dropdown-link{
        display: flex;
        align-items: center;
    }
</style>