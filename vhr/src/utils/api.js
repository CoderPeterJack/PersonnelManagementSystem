import axios from 'axios';
 //导入message信息组件
import{
     Message
 } from 'element-ui';
import router from '../router';

 //响应拦截器
 axios.interceptors.response.use(success => {
     if (success.status && success.status == 200 && success.data.status==500) {
         Message.error({message: success.data.msg})
         return;
     }
     //如果加载成功
     //如果服务端返回数据，前端就提示，例如"登录成功"，就会有提示
     if (success.data.msg){
         Message.success({message:success.data.msg})
     }
     return success.data;
 }, error => {
     if (error.response.status == 504 || error.response.status == 404) {
         Message.error({
             message: '服务器被吃了( ╯□╰ )'
         });
     } else if (error.response.status == 403) {
         Message.error({message: '权限不足，请联系管理员'});
     } else if (error.response.status == 401) {
         Message.error({message: '尚未登录，请登录'});
         //回到登录页
         router.replace('/');
     } else {
         if (error.response.data.msg) {
             Message.error({message: error.response.data.msg});
         } else {
             Message.error({message: '未知错误!'});
         }
     }
     return;
 })

 //把请求封装，base为全局变量
 let base ='';

 //Spring security默认只能用key-value传参
 export const postKeyValueRequest = (url, params) => {
     return axios({
         method:'post',
         url:`${base}${url}`,
         crossDomain: true,
         data: params,
         transformRequest: [function (data) {
             let ret = '';
             for (let it in data) {
                 ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
             }
             //测试打印
             console.log(ret);
             return ret;
         }],
         headers: {
             'Content-Type': 'application/x-www-form-urlencoded'
         }
     });
 }

export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params
    })
}
export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params
    })
}
export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params
    })
}
export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params
    })
}

