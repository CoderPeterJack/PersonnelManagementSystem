<template>
    <div>
        <div>
            <el-input size="small" style="width: 300px" placeholder="请输入角色英文名称" v-model="role.name">
                <template slot="prepend" size="small">ROLE_</template>
            </el-input>
            <el-input size="small" style="width: 300px;margin-left: 5px" placeholder="请输入角色中文名称" v-model="role.nameZh" @keydown.enter.native="doAddRole"></el-input>
            <el-button type="primary" size="small" style="margin-left: 5px" @click="doAddRole">添加角色</el-button>
        </div>
        <div class="permissManaMain">
            <el-collapse accordion @change="change" v-model="activeName">
<!--                v-for="(r,index) in roles"用于遍历roles-->
                <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r,index) in roles" :key="index">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>可访问的资源</span>
                            <el-button style="float: right; padding: 3px 0;color: red;" icon="el-icon-delete" type="text" @click="deleteRole(r)"></el-button>
                        </div>
<!--                        所有的菜单项-->
                        <div>
<!--                            需要注意的是，此时必须设置node-key，其值为节点数据中的一个字段名，该字段在整棵树中是唯一的。-->
<!--                            default-checked-keys设置默认选中的节点。-->
<!--                            ref="tree"为这个取了个名字-->
<!--                            :key="index"用于区分每一个tree不一样-->
                            <el-tree
                                    show-checkbox
                                    node-key="id"
                                    ref="tree"
                                    :key="index"
                                    :default-checked-keys="selectedMenus"
                                    :data="allMenus" :props="defaultProps"></el-tree>
                            <div style="display: flex; justify-content: flex-end">
                                <el-button size="mini" @click="cancelUpdate">取消修改</el-button>
                                <el-button size="mini" type="primary" @click="doUpdate(r.id,index)">确认修改</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PermissMana",
        //数据定义
        data(){
            return{
                role:{
                    name:'',
                    nameZh:''
                },
                activeName:-1,
                //roles用于接收来自后端的数据
                roles:[

                ],
                allMenus:[

                ],
                selectedMenus:[

                ],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }

        },
        mounted() {
            this.initRoles();
        },
        //方法
        methods:{
            deleteRole(role){
                this.$confirm('此操作将永久删除【'+role.nameZh+'】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/permiss/role/"+role.id).then(resp=>{
                        if (resp){
                            this.initRoles();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            doAddRole(){
                if (this.role.name&&this.role.nameZh){
                    this.postRequest("/system/basic/permiss/role",this.role).then(resp=>{
                        if (resp){
                            this.role.nameZh='';
                            this.role.name='';
                            this.initRoles();
                        }
                    })
                }else{
                    this.$message.error("数据不可以为空");
                }
            },
            cancelUpdate(){
                this.activeName=-1;
            },
            //index为获取第几个tree
            //角色只跟二级菜单绑定，即menu_role指定role与二级菜单绑定
            //例如员工资料(一级)分为基本资料和高级资料（二级），
            doUpdate(rid,index){
                //console.log(rid);
                //获取第几个tree
                let tree=this.$refs.tree[index];
                //getCheckedKeys方法 若节点可被选择
                // （即 show-checkbox 为 true），则返
                // 回目前被选中的节点的 key 所组成的数组。

                //getCheckedKeys(true)：(leafOnly) 接收一个 boolean
                // 类型的参数，若为 true 则仅返回被选中的叶子节点的 keys，默认值为 false
                let selectedKeys=tree.getCheckedKeys(true);
                //请求参数的整理
                let url='/system/basic/permiss/?rid='+rid;
                selectedKeys.forEach(key=>{
                    url+='&mids='+key;
                })
                this.putRequest(url).then(resp=>{
                    if (resp){
                        this.activeName=-1;
                    }
                })
    },
            change(rid){
                //如果打开它,打开他时name有数据，关闭时没有数据
                if(rid){
                    this.initAllMenus();
                    this.initSelectedMenus(rid);
                }
            },
            initSelectedMenus(rid){
                this.getRequest("system/basic/permiss/mids/"+rid).then(resp=>{
                    if (resp){
                        this.selectedMenus=resp;
                    }
                })
            },
            initAllMenus(){
                this.getRequest("system/basic/permiss/menus").then(resp=>{
                    if (resp){
                        this.allMenus=resp;
                    }
                })
            },
            initRoles(){
                this.getRequest("system/basic/permiss/").then(resp=>{
                    if (resp){
                        this.roles=resp;
                    }
                })
            }
        }
    }
</script>

<style>
    .permissManaMain{
        margin-top: 8px;
        width: 700px;
    }
</style>