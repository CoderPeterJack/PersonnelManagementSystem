//后端component都是字符串，因此写个工具类将服务端数据
//转成对象，并且放到router中
import {getRequest} from "./api";

//数据往router里加，并且存储在store中
//什么时候调用工具类方法呢，则采用酷游导航守卫
export const initMenu=(router,store)=>{
    //说明有菜单数据，不需要加载
    if (store.state.routes.length>0){
        return;
    }
    //否则需要加载菜单数据,请求接口如下
    getRequest("/system/config/menu").then(data=>{
        if (data){
            //请求到数据后，因此定义变量及方法
            let fmtRoutes=formatRoutes(data);
            router.addRoutes(fmtRoutes);
            //store初始化
            store.commit('initRoutes',fmtRoutes);
        }
    })
}
//定义格式化数据方法
export const formatRoutes=(routes)=>{
    let fmRoutes=[];
    //服务端返回的菜单项数据
    routes.forEach(router=>{
        let{
            path,
            component,
            name,
            meta,
            iconCls,
            children
        }=router;
        //如果是一级菜单的children
        if (children&&children instanceof Array){
            //递归调用
            children=formatRoutes(children);
        }
        let fmRouter={
            path:path,
            name:name,
            iconCls:iconCls,
            meta: meta,
            children:children,
            //动态加载component
            //服务端可能返回的是home组件
            component(resolve){
                //ES5的写法,相当于导入组件
                if (component.startsWith("Home")) {
                    require(['../views/' + component +'.vue'], resolve);
                }else if (component.startsWith("Emp")){
                    require(['../views/emp/'+component+'.vue'],resolve);
                }else if (component.startsWith("Per")){
                    require(['../views/per/'+component+'.vue'],resolve);
                }else if (component.startsWith("Sal")){
                    require(['../views/sal/'+component+'.vue'],resolve);
                }else if (component.startsWith("Sta")){
                    require(['../views/sta/'+component+'.vue'],resolve);
                }else if (component.startsWith("Sys")){
                    require(['../views/sys/'+component+'.vue'],resolve);
                }
            }
        }
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}