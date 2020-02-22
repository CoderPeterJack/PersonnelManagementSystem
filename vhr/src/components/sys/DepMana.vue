<template>
    <div style="width: 500px">
        <el-input
                placeholder="请输入部门名称进行搜索..."
                prefix-icon="el-icon-search"
                v-model="filterText">
        </el-input>

<!--        :data="deps"为要显示的数据-->
        <el-tree
                :data="deps"
                :props="defaultProps"
                :filter-node-method="filterNode"
                :expand-on-click-node="false"
                ref="tree">

<!--            node指当前元素，data当前node的json对象-->
<!--            通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据，用法参考 demo。-->
<!--            slot-scope="{ node, data }"自定义树节点的内容，参数为 { node, data }-->
            <span class="custom-tree-node" style="display: flex;justify-content: space-between;width: 100%;" slot-scope="{ node, data }">
        <span>{{ data.name }}</span>
        <span>
          <el-button
                  type="primary"
                  size="mini"
                  class="depBtn"
                  @click="() => showAddDepView(data)">
              添加部门
          </el-button>
          <el-button
                  type="danger"
                  size="mini"
                  class="depBtn"
                  @click="() => deleteDep(data)">
            删除部门
          </el-button>
        </span>
      </span>
        </el-tree>

        <el-dialog
                title="添加部门"
                :visible.sync="dialogVisible"
                width="30%"
                :before-close="handleClose">
            <div>
                <table>
                    <tr>
                        <td><el-tag>上级部门</el-tag></td>
                        <td>{{pname}}</td>
                    </tr>
                    <tr>
                        <td><el-tag>部门名称</el-tag></td>
                        <td><el-input v-model="dep.name" placeholder="请输入部门名称..."></el-input></td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddDep">确 定</el-button>
  </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "DepMana",
        data(){
            return{
                dialogVisible:false,
                //数据需要先定义
                filterText:'',
                dep:{
                    name:'',
                    parentId:-1
                },
                pname:[],
                // 接收后端传过来的json数据
                deps:[],
                defaultProps: {
                    //前端显示
                    children: 'children',
                    label: 'name'
                },



            }
        },
        //监控器,查看element-ui的tree树形控件的节点过滤
        watch: {
            //当filterText发生变化进行的监控
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
        mounted() {
            this.initDeps();
        },
        methods:{
            initDep(){
                this.dep={
                    name:'',
                    parentId:-1
                }
                this.pname='';
            },
            //动态修改元素实现刷新
            addDep2Deps(deps,dep){
                for(let i=0;i<deps.length;i++){
                    let d=deps[i];
                    if (d.id==dep.parentId){
                        d.children=d.children.concat(dep);
                        //0220更新
                        if (d.children.length>0){
                            d.parent=true;
                        }
                        return;
                    }else{
                        this.addDep2Deps(d.children,dep);
                    }
                }
            },
            doAddDep(){
                //添加的数据不能为空
                if (this.dep.name){
                    this.postRequest("/system/basic/department/",this.dep).then(resp=>{
                        if (resp){
                            //返回的resp对象加入到deps中就不会折叠了
                            this.addDep2Deps(this.deps,resp.obj);
                            this.dialogVisible=false;
                            //这是初始化变量
                            this.initDep();
                        }
                    })
                }else{
                    this.$message.error("添加失败");
                }
            },
            showAddDepView(data){
                this.pname=data.name;
                this.dep.parentId=data.id;
                this.dialogVisible=true;
            },
            removeDepFromDeps(p,deps,id){
                for (let i=0;i<deps.length;i++){
                    let d=deps[i];
                    if (d.id==id){
                        deps.splice(i,1);
                        //判断父部门的属性是否为0
                        if (deps.length==0){
                            p.parent=false;
                        }
                        return;
                    }else {
                        this.removeDepFromDeps(d,d.children,id);
                    }

                }
    },
            deleteDep(data){
                //如果是data.parent=true说明父部门下面还有子部门
                if (data.parent){
                    this.$message.error("父部门删除失败");
                }else {
                    this.$confirm('此操作将永久删除该文件【'+data.name+'】部门, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.deleteRequest("/system/basic/department/"+data.id).then(resp=>{
                            if (resp){
                                this.removeDepFromDeps(null,this.deps,data.id);
                            }
                        })
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }

            },
            initDeps(){
                this.getRequest("/system/basic/department/").then(resp=>{
                    if (resp){
                        this.deps=resp;
                    }
                })
            },
            //value为传进来的数据
            //data为json数组中的每一项
            filterNode(value, data) {
                //value不存在时，return true，当前的data留着，前端全部展开
                if (!value) return true;
                // !== -1的话有这个值，当前的data留着
                return data.name.indexOf(value) !== -1;
            }
        }
    }
</script>

<style>
    .depBtn{
        padding: 2px;
    }
</style>