//点击怎么校验_=>表对校核+message消息
//网络请求工具：axios，之前用Ajax，util包的api封装网络请求时的显示信息
///35集
<template>
    <div>
        <el-form :rules="rules"
                 ref="loginForm"
                 :model="loginForm"
                 class="loginContainer">
            <h3 class="loginTitle">系统登录</h3>
            <el-form-item prop="username">
                <el-input type="text" v-model="loginForm.username"
                          auto-complete="off" placeholder="请输入用户名"></el-input>
            </el-form-item>
<!--            @keydown.enter.native="submitLogin即为按enter键进行登入-->
            <el-form-item prop="password">
                <el-input type="password" v-model="loginForm.password" auto-complete="off"
                          placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-checkbox class="loginRemember" v-model="checked"></el-checkbox>
            <el-button type="primary" style="width: 100%" @click="submitLogin">登录</el-button>
        </el-form>
    </div>
</template>

<script>
    import {postKeyValueRequest} from "../utils/api";

    export default {
        name: "Login",
        data() {
            return {
                loginForm: {
                    username:'admin',
                    password:'123',
                },
                checked: true,
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}]
                }
            }
        },
        methods:{
            // 点击登入+校验
            submitLogin(){
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                       postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                                if (resp){
                                    //如果成功打印信息
                                    window.sessionStorage.setItem("user",JSON.stringify(resp.obj));
                                    ////页面跳转，replace将会替换当前页面
                                    ////push将会新建一个页面
                                    this.$router.replace('/home');
                                }
                        })
                        //alert('submit!');
                    } else {
                        this.$message.error('请输入所有字段');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style>
    .loginContainer{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }
    .loginTitle{
        margin: 15px auto 20px auto;
        text-align: center;
        color:#505458;
    }
    .loginRemember{
        text-align: left;
        margin: 0px 0px 15px 0px;
    }
</style>