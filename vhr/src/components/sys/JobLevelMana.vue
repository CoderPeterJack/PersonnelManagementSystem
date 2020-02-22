<template>
    <div>
        <div>
            <el-input
                    size="small"
            prefix-icon="el-icon-plus"
                    placeholder="添加职称名称..."
            style="width: 300px"
                    @keydown.enter.native="addJobLevel"
            v-model="jl.name">
                添加
            </el-input>

            <el-select style="margin-left: 5px" size="small" v-model="jl.titleLevel" placeholder="职称等级">
                <el-option
                        v-for="item in titleLevels"
                        :key="item"
                        :label="item"
                        :value="item">
                </el-option>
            </el-select>

            <el-button size="small" icon="el-icon-plus" type="primary" style="margin-left: 5px" @click="addJobLevel">添加</el-button>
        </div>
        <div style="margin-top: 10px">
<!--            :data="jls"为表格接收存放的数据源-->
            <el-table
                    :data="jls"
                    border
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    stripe
                    size="small"
                    @selection-change="handleSelectionChange"
                    style="width: 80%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职称名称"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="titleLevel"
                        label="职称级别">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间">
                </el-table-column>
                //添加是否启用
                <el-table-column
                        prop="enabled"
                        label="是否启用">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
                        <el-tag type="danger" v-else>未启用</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="showEditView(scope.row)">编辑</el-button>
<!--                        scope.row为当前行的数据-->
                        <el-button size="small" type="danger" @click="deleteHandler(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
<!--            :disabled="multipleSelection.length==0"表示当没有选择时就禁用-->
            <el-button type="danger" size="small" style="margin-top: 10px" :disabled="multipleSelection
            .length==0" @click="deleteMany">批量删除</el-button>
        </div>

        <el-dialog
                title="修改职称"
                :visible.sync="dialogVisible"
                width="30%">
           <div>
               <table>
                   <tr>
                       <td>
                           <el-tag>职称名称</el-tag>
                       </td>
                       <td>
                           <el-input size="small" v-model="updateJl.name"></el-input>
                       </td>
                   </tr>

                   <tr>
                       <td>
                           <el-tag>职称级别</el-tag>
                       </td>
                       <td>
                           <el-select style="margin-left: 5px" size="small" v-model="updateJl.titleLevel" placeholder="职称等级">
                               <el-option
                                       v-for="item in titleLevels"
                                       :key="item"
                                       :label="item"
                                       :value="item">
                               </el-option>
                           </el-select>
                       </td>
                   </tr>

                   <tr>
                       <td>
                           <el-tag>是否启用</el-tag>
                       </td>
                       <td>
                           <el-switch
                                   v-model="updateJl.enabled"
                                   active-text="启用"
                                   inactive-text="未启用">
                           </el-switch>
                       </td>
                   </tr>
               </table>
           </div>
            <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
        </el-dialog>

    </div>

</template>

<script>
    export default {
        name: "JobLevelMana",

        data(){
            return{
                updateJl:{
                    name:'',
                    titleLevel:'',
                    enabled:false
                },
                loading:false,
                dialogVisible:false,
                jl:{
                    name:'',
                    titleLevel:''
                },
                titleLevels:[
                    '正高级',
                    '副高级',
                    '中级',
                    '初级',
                    '原级',
                ],
                // jls用于存放接收来自后端的数据源，因为后端传过来的是一个json数组
                jls:[
                ],
                multipleSelection: [
                ]
            }
        },
        //当组件初始化时就会执行mounted()
        mounted() {
            this.initJobLevels();
        },

        methods:{
            deleteMany(){
                this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //批量删除的数组拼接
                    let ids='?';
                    this.multipleSelection.forEach(item=>{
                        ids+='ids='+item.id+'&';
                    })
                    this.deleteRequest("/system/basic/joblevel/"+ids).then(resp=>{
                        if (resp){
                            this.initJobLevels();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            //确定编辑
            doUpdate(){
                this.putRequest("system/basic/joblevel/",this.updateJl).then(resp=>{
                    if (resp){
                        this.initJobLevels();
                        this.dialogVisible=false;
                    }
                })
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            showEditView(data){
                Object.assign(this.updateJl,data);
                this.dialogVisible=true;
            },
            deleteHandler(data){
                this.$confirm('此操作将永久删除【'+data.name+'】这一职称名称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("system/basic/joblevel/"+data.id).then(resp=>{
                        if (resp){
                            this.initJobLevels();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            addJobLevel(){
                if (this.jl.name && this.jl.titleLevel){
                    this.postRequest("system/basic/joblevel/",this.jl).then(resp=>{
                        if(resp){
                            this.initJobLevels();
                        }
                    })
                }else{
                    this.$message.error("添加字段不可以为空!");
                }

            },
            updateJobLevelById(){

            },
            //职称管理初始化
            initJobLevels(){
                this.loading=true;
                this.getRequest("system/basic/joblevel/").then(resp=>{
                    this.loading=false;
                    if(resp){
                        this.jls=resp;
                        this.jl={
                            name:'',
                            titleLevel:''
                        }
                    }
                })

            }
        }
    }
</script>

<style scoped>

</style>