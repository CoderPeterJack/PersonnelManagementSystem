<template>
    <div>
        <div style="margin-top: 10px;display: flex;justify-content: center">
            <el-input v-model="keywords" placeholder="通过用户名搜索用户..."
                      prefix-icon="el-icon-search" style="width: 400px;
margin-right: 10px" @keydown.enter.native="doSearch"></el-input>
            <el-button icon="el-icon-search" type="primary" @click="doSearch">搜索</el-button>
        </div>

        <div class="hr-container">
            <el-card class="hr-card" v-for="(hr,index) in hrs" :key="index">
                <div slot="header" class="clearfix">
                    <span>{{hr.name}}</span>
                    <el-button style="float: right; padding: 3px 0;color: red" type="text" icon="el-icon-delete" @click="deleteHr(hr)"></el-button>
                </div>
                <div>
                    <div class="img-container">
<!--alt 属性是一个必需的属性，它规定在图像无法显示时的替代文本。-->
<!--假设由于一些原因（比如网速太慢、src 属性中的错误、浏览器禁用图像、用户使用的是屏幕阅读器）用户无法查看图像,
alt 属性可以为图像提供替代的信息。-->
<!--                        而title属性，是当网页上的图片被-->
<!--                        加载完成后，鼠标移动到上面去，会-->
<!--                        显示这个图片指定的属性文字，以对-->
<!--                        图片信息进行补充性说明。-->
                        <img :src="hr.userface" :alt="hr.name" :title="hr.name" class="userface-img">
                    </div>

                    <div class="userinfo-container">
                        <div>用户名：{{hr.name}}</div>
                        <div>手机号码：{{hr.phone}}</div>
                        <div>电话号码：{{hr.telephone}}</div>
                        <div>地址：{{hr.address}}</div>
                        <div>用户状态：<el-switch
                                v-model="hr.enabled"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                active-text="启用"
                                @change="enabledChange(hr)"
                                inactive-text="禁用">
                        </el-switch></div>
                        <div>用户角色:
                        <el-tag type="success" style="margin-right: 4px" v-for="(role,indexj) in hr.roles" :key="indexj">
                            {{role.nameZh}}
                        </el-tag>
<!--                            @hide="hidePop(hr)"关闭时进行更新，传到服务端去-->
                            <el-popover
                                    placement="right"
                                    title="角色列表"
                                    @show="showPop(hr)"
                                    @hide="hidePop(hr)"
                                    width="200"
                                    trigger="click">
<!--                                v-model=" selectedRoles"为选中的值-->
                                <el-select v-model=" selectedRoles" multiple placeholder="请选择">
<!--                                    allroles需要从服务端加载下来-->
                                    <el-option
                                            v-for="(r,indexk) in allroles"
                                            :key="indexk"
                                            :label="r.nameZh"
                                            :value="r.id">
                                    </el-option>
                                </el-select>
                                <el-button icon="el-icon-more" type="text" slot="reference"></el-button>
                            </el-popover>
                          </div>
                        <div>备注：{{hr.remark}}</div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysHr",
        data(){
            return{
                keywords:'',
                hrs:[],
                selectedRoles:[],
                allroles:[]
            }
        },
        mounted() {
            this.initHrs();
        },
        methods:{
            deleteHr(hr){
                this.$confirm('此操作将永久删除【'+hr.name+'】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/hr/"+hr.id).then(resp=>{
                        if (resp){
                            this.initHrs();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            doSearch(){
              this.initHrs();
            },
            hidePop(hr){
                let roles=[];
                //把hr.roles拷贝到roles；
                Object.assign(roles,hr.roles);
                let flag=false;
                if (roles.length!=this.selectedRoles.length){
                    flag=true;
                }else{
                    for (let i=0;i<roles.length;i++){
                        let role=roles[i];
                        for (let j=0;i<this.selectedRoles.length;j++){
                            let sr=this.selectedRoles[j];
                            if (role.id==sr){
                                //从i位置开始移，移动除一个
                                roles.splice(i,1);
                                i--;
                                break;
                            }
                        }
                    }
                    //roles.length!=0说明selectedRoles与roles不一样
                    if (roles.length!=0){
                        flag=true;
                    }
                }
                if (flag){
                    let url='/system/hr/role?hrid='+hr.id;
                    this.selectedRoles.forEach(sr=>{
                        url+='&rids='+sr;
                    })
                    this.putRequest(url).then(resp=>{
                        if (resp){
                            this.initHrs();
                        }
                    })
                }
            },
            showPop(hr){
                this.initAllRoles();
                let roles=hr.roles;
                this.selectedRoles=[];
                roles.forEach(r=>{
                    this.selectedRoles.push(r.id);
                })
            },
            initAllRoles(){
              this.getRequest("/system/hr/roles").then(resp=>{
                  if (resp){
                      this.allroles=resp;
                  }
              })
            },
            enabledChange(hr){
                //手动删除roles
                delete hr.roles;
                this.putRequest("/system/hr/",hr).then(resp=>{
                    if (resp){
                        this.initHrs();
                    }
                })
            },
            initHrs(){
                this.getRequest("/system/hr/?keywords="+this.keywords).then(resp=>{
                    if (resp){
                        this.hrs=resp;
                    }
                })
            }
        }
    }
</script>

<style>
    .userinfo-container{
        font-size: 12px;
        color: #21b7ff;
    }
    .userinfo-container{
        margin-top: 20px;
    }
    .img-container{
        width: 100%;
        display: flex;
        justify-content: center;
    }
    .userface-img{
        width: 72px;
        height: 72px;
        border-radius: 72px;
    }
    .hr-container{
        margin-top:10px;
        display: flex;
        /*自动换行*/
        flex-wrap: wrap;
        justify-content: space-around;
    }
    .hr-card{
        width: 350px;
        margin-bottom: 20px;
    }
</style>