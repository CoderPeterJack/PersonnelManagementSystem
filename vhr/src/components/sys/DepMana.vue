<template>
    <div style="width: 500px">
        <el-input
                placeholder="请输入部门名称进行搜索..."
                prefix-icon="el-icon-search"
                v-model="filterText">
        </el-input>

        <el-tree
                :data="deps"
                :props="defaultProps"
                :filter-node-method="filterNode"
                :expand-on-click-node="false"
                ref="tree">

<!--            node指当前元素，data当前node的json对象-->
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
                        return;
                    }else{
                        this.addDep2Deps(d.children,dep);
                    }
                }
            },
            doAddDep(){
                this.postRequest("/system/basic/department/",this.dep).then(resp=>{
                    if (resp){
                        //返回的resp对象加入到deps中就不会折叠了
                        this.addDep2Deps(this.deps,resp.obj);
                        this.dialogVisible=false;
                        //这是初始化变量
                        this.initDep();
                    }
                })
            },
            showAddDepView(data){
                this.pname=data.name;
                this.dep.parentId=data.id;
                this.dialogVisible=true;
            },
            deleteDep(data){
                console.log(data)
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