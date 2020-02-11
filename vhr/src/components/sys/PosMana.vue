<template>
    <div>
        <div>
            <el-input
                    size="small"
                    class="addPosInput"
                    placeholder="添加职位"
                    prefix-icon="el-icon-plus"
                    @keydown.enter.native="addPosition"
                    v-model="pos.name">
            </el-input>
            <el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition">添加</el-button>
        </div>
        <div class="posManaMain">
            <el-table
                    :data="positions"
                    border
                    size="small"
                    stripe
                    style="width: 70%"
                    @selection-change="handleSelectionChange">
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
                        label="职位名称"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        width="150"
                        label="创建时间">
                </el-table-column>
                <!--scope.row为那一行的json对象-->
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showEditView(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
<!--            加：变为表达式-->
            <el-button @click="deleteMany" type="danger" size="small" style="margin-top: 8px" :disabled="multipleSelection.length==0">批量删除</el-button>
        </div>

        <el-dialog
                title="修改职位"
                :visible.sync="dialogVisible"
                width="30%">
           <div>
               <el-tag>职位名称</el-tag>
               <el-input class="updatePosInput" size="small" v-model="updatePos.name"></el-input>
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
        name: "PosMana",
        // json形式上传
        data(){
            return{
                pos:{
                    name:''
                },
                //默认对话框关闭
                dialogVisible:false,
                //职位的名称提前展示处理
                updatePos:{
                  name:''
                },
                // 表格数据是json数组
                positions: [],
                //批量选择的数量
                multipleSelection: []
            }
        },
        //当组件初始化时就会执行mounted()
        mounted() {
            this.initPositions();
        },
        // 添加position
        methods:{
            deleteMany(){
                this.$confirm('此操作将永久删除这【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //批量删除的数组拼接
                    let ids='?';
                    this.multipleSelection.forEach(item=>{
                        ids+='ids='+item.id+'&';
                    })
                    //请求参数
                    this.deleteRequest("system/basic/pos/"+ids).then(resp=>{
                        if (resp){
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            addPosition(){
                //如果字段有值
                if(this.pos.name){
                    this.postRequest("system/basic/pos/",this.pos).then(resp=>{
                        if(resp){
                            this.initPositions();
                            this.pos.name='';
                        }
                    })
                }else {
                    this.$message.error('职位名称不可以为空');
                }
            },
            showEditView(index,data){
                //data为所点击的那一行的json对象
                // this.updatePos=data;这样会导致职位名称和修改名称同步变化
                //故需要采用拷贝,将data拷贝到this.updatePos
                Object.assign(this.updatePos,data);
                this.dialogVisible=true;
            },
            doUpdate(){
                //请求的接口和数据
                this.putRequest("system/basic/pos/",this.updatePos).then(resp=>{
                    if (resp){
                        this.initPositions();
                        this.updatePos.name='';
                        this.dialogVisible=false;
                    }
                })
            },
            //data为当前行的json对象
            handleDelete(index,data){
                this.$confirm('此操作将永久删除['+data.name+']职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("system/basic/pos/"+data.id).then(resp=>{
                        if(resp){
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleSelectionChange(val){
              this.multipleSelection=val;
              console.log(val);
            },
            initPositions(){
                this.getRequest("system/basic/pos/").then(resp=>{
                    if(resp){
                        this.positions=resp;
                    }
                })

            }
        }
    }
</script>

<style>
    .addPosInput{
        width: 300px;
        margin-right: 8px
    }
    .updatePosInput{
        width: 200px;
        margin-left: 8px;
    }
    .posManaMain{
        margin-top: 10px;
    }
</style>