<template>
    <div>
<!--        justify-content属性定义了项目在主轴上的对齐方式。-->
        <div style="display:flex;justify-content:space-between">
<!--            clearable清除输入框的关键字-->
<!--            @clear="initEmps"清除后触发initEmps方法-->
            <div >
                <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..."
                           prefix-icon="el-icon-search"
                          clearable
                          @clear="initEmps"
                          style="width:300px; margin-right:10px" v-model="keyword" @keydown.enter.native="initEmps"></el-input>
                <el-button icon="el-icon-search" type="primary" @click="initEmps"></el-button>
                <el-button type="primary">
                    <i class="fa fa-angle-double-down" aria-hidden="true"></i>高级搜索</el-button>
            </div>
            <div>
                <el-upload
                        style="display: inline-flex;margin-right: 8px"
                        :show-file-list="false"
                        :before-upload="beforeUpload"
                        :on-success="onSuccess"
                        :on-error="onError"
                        :disabled="importDataDisabled"
                action="/employee/basic/import">
                    <el-button     :disabled="importDataDisabled" type="success" :icon="importDataBtnIcon">{{importDataBtnText}}</el-button>
                </el-upload>>
                <el-button type="success" @click="exportData" icon="el-icon-download">导出数据</el-button>
                <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">添加用户</el-button>
            </div>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="emps"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="正在加载数据..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    style="width: 100%"  size="mini">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>

                <el-table-column
                        prop="name"
                        fixed
                        align="left"
                        label="姓名"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="gender"
                        align="left"
                        label="性别"
                        width="50">
                </el-table-column>

                <el-table-column
                        prop="workId"
                        label="工号"
                        align="left"
                        width="100">
                </el-table-column>

                <el-table-column
                        prop="birthday"
                        label="出生日期"
                        align="left"
                        width="120">
                </el-table-column>

                <el-table-column
                    prop="idCard"
                    label="身份证号码"
                    align="left"
                    width="180">
            </el-table-column>

                <el-table-column
                        prop="wedlock"
                        label="婚姻状况"
                        align="left"
                        width="85">
                </el-table-column>

                <el-table-column
                        prop="nation.name"
                        label="民族"
                        width="55">
                </el-table-column>

                <el-table-column
                        prop="nativePlace"
                        label="籍贯"
                        width="100">
                </el-table-column>

                <el-table-column
                        prop="politicsstatus.name"
                        label="政治面貌"
                        width="85">
                </el-table-column>

                <el-table-column
                        prop="email"
                        label="电子邮件"
                        width="180">
                </el-table-column>

                <el-table-column
                        prop="phone"
                        label="联系电话"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="address"
                        label="联系地址"
                        width="220">
                </el-table-column>

                <el-table-column
                        prop="department.name"
                        label="所属部门"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="position.name"
                        label="职位"
                        width="100">
                </el-table-column>

                <el-table-column
                        prop="jobLevel.name"
                        label="职称"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="engageForm"
                        label="聘用形式"
                        width="100">
                </el-table-column>

                <el-table-column
                        prop="tiptopDegree"
                        label="最高学历">
                </el-table-column>

                <el-table-column
                        prop="specialty"
                        width="150"
                        label="专业">
                </el-table-column>

                <el-table-column
                        prop="school"
                        width="150"
                        label="毕业院校">
                </el-table-column>


                <el-table-column
                        prop="beginDate"
                        label="入职日期"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="conversionTime"
                        label="转正日期"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="beginContract"
                        label="合同起始日期"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="endContract"
                        label="合同截至日期"
                        width="120">
                </el-table-column>

                <el-table-column
                        prop="contractTerm"
                        label="合同期限"
                        width="100">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.contractTerm}}</el-tag>年
                    </template>
                </el-table-column>


                <el-table-column
                        label="操作"
                fixed="right"
                width="200px">
<!--                    (scope.row)为获取当前行的数据-->
                    <template slot-scope="scope">
                        <el-button style="padding: 3px" size="mini" @click="showEditEmpView(scope.row)">编辑</el-button>
                        <el-button style="padding: 3px" size="mini" type="primary">查看高级资料</el-button>
                        <el-button @click="deleteEmp(scope.row)" style="padding: 3px" size="mini" type="danger">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination
                        background
                        @current-change="currentChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>

        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="80%">
            <div>
<!--                表单中声明model为定义的数据和rules为定义的规则进行数据校验
                        ref="empForm"为表单取名字-->
                <el-form :model="emp" :rules="rules" ref="empForm">
<!--                    第一行-->
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="姓名:" prop="name">
                                <el-input style="width: 150px" v-model="emp.name"
                                          placeholder="请输入员工姓名"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="5">
                            <el-form-item label="性别:" prop="gender">
                                <el-radio-group v-model="emp.gender">
                                    <el-radio label="男">男</el-radio>
                                    <el-radio label="女" style="margin-left: 0px">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="出生日期:" prop="birthday">
                                <el-date-picker v-model="emp.birthday"
                                        type="date"
                                                style="width: 150px"
                                                value-format="yyyy-MM-dd"
                                        placeholder="出生日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                        <el-col :span="7">
                            <el-form-item label="政治面貌:" prop="politicId">
<!--                                value与v-model="emp.politicId"绑定-->
                                <el-select v-model="emp.politicId" placeholder="政治面貌" style="width: 150px">
                                    <el-option
                                            v-for="item in politicsstatus"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>

<!--                    第二行-->
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="民族:" prop="nationId">
                                <el-select v-model="emp.nationId" placeholder="请选择民族" style="width: 150px">
                                    <el-option
                                            v-for="item in nations"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="5">
                            <el-form-item label="籍贯:" prop="nativePlace">
                                <el-input style="width: 120px" v-model="emp.nativePlace"
                                          placeholder="请输入籍贯"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="电子邮箱:" prop="email">
                                <el-input style="width: 150px" v-model="emp.email"
                                          placeholder="请输入邮箱"
                                          prefix-icon="el-icon-message">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="7">
                            <el-form-item label="联系地址:" prop="address">
                                <el-input style="width: 200px" v-model="emp.address"
                                          placeholder="请输入联系地址"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>
                        </el-row>

<!--                        第三行-->
                        <el-row>
                            <el-col :span="6">
                            <el-form-item label="职位:" prop="posId">
                                <el-select v-model="emp.posId" placeholder="请选择职位" style="width: 100px">
                                    <el-option
                                            v-for="item in positions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            </el-col>

                            <el-col :span="5">
                                <el-form-item label="职称:" prop="jobLevelId">
                                    <el-select v-model="emp.jobLevelId" placeholder="职称" style="width: 100px">
                                        <el-option
                                                v-for="item in joblevels"
                                                :key="item.id"
                                                :label="item.name"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>

                            <el-col :span="6">
                                <el-form-item label="所属部门:" prop="departmentId">
                                    <el-popover
                                            placement="right"
                                            title="请选择部门"
                                            width="200"
                                            trigger="manual"
                                            v-model="popvisible">
                                            <el-tree
                                                    default-expand-all
                                                    :data="allDeps" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
                                        <div slot="reference"  style="width: 150px;display: inline-flex; font-size: 13px;border: 1px solid #dedede;
height: 26px;border-radius: 5px; cursor:pointer; align-items:center; padding-left:8px; box-sizing: border-box"  @click="showDepView">{{inputDepName}}</div>
                                    </el-popover>
                                </el-form-item>
                            </el-col>

                            <el-col :span="7">
                                <el-form-item label="联系电话:" prop="phone">
                                    <el-input style="width: 200px" v-model="emp.phone"
                                              placeholder="请输入联系电话"
                                              prefix-icon="el-icon-phone">
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

<!--                    第四行-->
                    <el-row>
<!--                        从数据库中加载最大的工号再加1-->
<!--                        工号为自动增长的数据,所有禁止修改-->
<!--                        disable为禁用-->
                        <el-col :span="6">
                            <el-form-item label="工号:" prop="workId">
                                <el-input style="width: 150px" v-model="emp.workId"
                                          placeholder="工号"
                                          disabled
                                          prefix-icon="el-icon-message">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="5">
                            <el-form-item label="学历:" prop="tiptopDegree">
                                <el-select v-model="emp.tiptopDegree" placeholder="学历" style="width: 100px">
                                    <el-option
                                            v-for="item in tiptopDegrees"
                                            :key="item"
                                            :label="item"
                                            :value="item">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="毕业院校:" prop="school">
                                <el-input style="width: 150px" v-model="emp.school"
                                          placeholder="毕业院校名称"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="7">
                            <el-form-item label="专业名称:" prop="specialty">
                                <el-input style="width: 150px" v-model="emp.specialty"
                                          placeholder="请输入专业名称"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

<!--                    第五行-->
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="入职日期:" prop="beginDate">
                                <el-date-picker v-model="emp.beginDate"
                                                type="date"
                                                style="width: 150px"
                                                value-format="yyyy-MM-dd"
                                                placeholder="入职日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="转正日期:" prop="conversionTime">
                                <el-date-picker v-model="emp.conversionTime"
                                                type="date"
                                                style="width: 130px"
                                                value-format="yyyy-MM-dd"
                                                placeholder="转正日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="合同起始日期:" prop="beginContract">
                                <el-date-picker v-model="emp.beginContract"
                                                type="date"
                                                style="width: 130px"
                                                value-format="yyyy-MM-dd"
                                                placeholder="合同起始日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>

                        <el-col :span="6">
                            <el-form-item label="合同截至日期:" prop="endContract">
                                <el-date-picker v-model="emp.endContract"
                                                type="date"
                                                style="width: 130px"
                                                value-format="yyyy-MM-dd"
                                                placeholder="合同截至日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="身份证号码:" prop="idCard">
                                <el-input style="width: 180px" v-model="emp.idCard"
                                          placeholder="请输入身份证号码"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="8">
                            <el-form-item label="聘用形式:" prop="engageForm">
                                <el-radio-group v-model="emp.engageForm">
                                    <el-radio label="劳动合同">劳动合同</el-radio>
                                    <el-radio label="劳务合同" style="margin-left: 0px">劳务合同</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>


                        <el-col :span="8">
                            <el-form-item label="婚姻状况:" prop="wedlock">
                                <el-radio-group v-model="emp.wedlock">
                                    <el-radio label="已婚">已婚</el-radio>
                                    <el-radio label="未婚" style="margin-left: 0px">未婚</el-radio>
                                    <el-radio label="离异" style="margin-left: 0px">离异</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>

            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddEmp">确 定</el-button>
  </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "EmpBasic",
        data(){
            return{
                importDataBtnText:'导入数据',
                importDataBtnIcon:'el-icon-upload2',
                importDataDisabled:false,
                title:'',
                allDeps:[],
                popvisible:false,
                dialogVisible:false,
                emps:[],
                loading:false,
                total:0,
                page:1,
                size:10,
                keyword:'',
                joblevels:[],
                nations:[],
                positions:[],
                politicsstatus:[],
                inputDepName:'',
                tiptopDegrees:['本科','硕士','博士','高中','初中'],
                emp:{
                    name:"",
                    gender:"",
                    birthday:"",
                    idCard:"",
                    wedlock:"",
                    nationId:'',
                    nativePlace:"",
                    politicId:'',
                    email:"",
                    phone:"",
                    address:"",
                    departmentId:null,
                    jobLevelId:'',
                    posId:'',
                    engageForm:"",
                    tiptopDegree:"",
                    specialty:"",
                    school:"",
                    beginDate:"",
                    workState:"",
                    workId:"",
                    contractTerm:'',
                    conversionTime:"",
                    notWorkDate:null,
                    beginContract:"",
                    endContract:"",
                    workAge:null
                },
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                rules:{
                    name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
                    gender: [{required: true, message: '请输入性别', trigger: 'blur'}],
                    birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
                    idCard: [{required: true, message: '请输入身份证号码', trigger: 'blur'},{
                        pattern:/(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                        message:'身份证号码格式不正确',
                        trigger: 'blur'
                    }],
                    wedlock: [{required: true, message: '请输入婚姻状况', trigger: 'blur'}],
                    nationId: [{required: true, message: '请输入民族', trigger: 'blur'}],
                    nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    politicId: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
                    email: [{required: true, message: '请输入邮箱', trigger: 'blur'},{
                        type:'email',
                        message: '邮箱格式不正确',
                        trigger: 'blur'
                    }],
                    phone: [{required: true, message: '请输入联系电话', trigger: 'blur'}],
                    address: [{required: true, message: '请输入地址', trigger: 'blur'}],
                    departmentId: [{required: true, message: '请输入部门', trigger: 'blur'}],
                    jobLevelId: [{required: true, message: '请输入职称', trigger: 'blur'}],
                    posId: [{required: true, message: '请输入职位', trigger: 'blur'}],
                    engageForm: [{required: true, message: '请输入聘用形式', trigger: 'blur'}],
                    tiptopDegree: [{required: true, message: '请输入学历', trigger: 'blur'}],
                    specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
                    school:[{required: true, message: '请输入学校', trigger: 'blur'}],
                    beginDate:[{required: true, message: '请输入入职日期', trigger: 'blur'}],
                    workState:[{required: true, message: '请输入工作状态', trigger: 'blur'}],
                    workId:[{required: true, message: '请输入工号', trigger: 'blur'}],
                    contractTerm:[{required: true, message: '请输入合同期限', trigger: 'blur'}],
                    conversionTime:[{required: true, message: '请输入转正日期', trigger: 'blur'}],
                    notWorkDate:[{required: true, message: '请输入离职日期', trigger: 'blur'}],
                    beginContract:[{required: true, message: '请输入合同起始日期', trigger: 'blur'}],
                    endContract:[{required: true, message: '请输入合同截至日期', trigger: 'blur'}],
                    workAge:[{required: true, message: '请输入工龄', trigger: 'blur'}],
                }
            }
        },
        mounted() {
            this.initEmps();
            this.initData();
        },
        methods:{
            onError(err, file, fileList){
                this.importDataBtnText='导入数据';
                this.importDataBtnIcon='el-icon-upload2';
                this.importDataDisabled=false;
            },
            onSuccess(response, file, fileList){
                this.importDataBtnText='导入数据';
                this.importDataBtnIcon='el-icon-upload2';
                this.importDataDisabled=false;
            },
            beforeUpload(){
                this.importDataBtnText='正在导入';
                this.importDataBtnIcon='el-icon-loading';
                this.importDataDisabled=true;
            },
            exportData(){
                window.open('/employee/basic/export','_parent');
            },
            emptyEmp(){
                this.emp={
                        name:"",
                        gender:"",
                        birthday:"",
                        idCard:"",
                        wedlock:"",
                        nationId:'',
                        nativePlace:"",
                        politicId:'',
                        email:"",
                        phone:"",
                        address:"",
                        departmentId:null,
                        jobLevelId:'',
                        posId:'',
                        engageForm:"",
                        tiptopDegree:"",
                        specialty:"",
                        school:"",
                        beginDate:"",
                        // workState:"",因为后端只判断了workState非空，所有workState为空后端异常
                        workId:"",
                        contractTerm:'',
                        conversionTime:"",
                        notWorkDate:null,
                        beginContract:"",
                        endContract:"",
                        workAge:null
                    // name: "江南一点雨",
                    // gender: "男",
                    // birthday: "1990-01-01",
                    // idCard: "610122199001011256",
                    // wedlock: "已婚",
                    // nationId: 1,
                    // nativePlace: "陕西",
                    // politicId: 13,
                    // email: "laowang@qq.com",
                    // phone: "18565558897",
                    // address: "深圳市南山区",
                    // departmentId: 5,
                    // jobLevelId: 9,
                    // posId: 29,
                    // engageForm: "劳务合同",
                    // tiptopDegree: "本科",
                    // specialty: "信息管理与信息系统",
                    // school: "深圳大学",
                    // beginDate: "2018-01-01",
                    // workState: "在职",
                    // workId: "00000001",
                    // contractTerm: 2.0,
                    // conversionTime: "2018-04-91",
                    // notWorkDate: null,
                    // beginContract: "2018-01-01",
                    // endContract: "2020-01-01",
                    // workAge: null,
                };
                    this.inputDepName='';
            },
            deleteEmp(data){
                this.$confirm('此操作将永久删除该【'+data.name+'】员工, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("employee/basic/"+data.id).then(resp=>{
                        if (resp){
                            this.initEmps();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            doAddEmp(){
                //如果存在id就是更新，否则是添加
                if (this.emp.id){
                    //先校验再提交添加的数据
                    this.$refs['empForm'].validate(valid=>{
                        if (valid){
                            this.putRequest("employee/basic/",this.emp).then(resp=>{
                                if (resp){
                                    this.dialogVisible=false;
                                    //刷新页面
                                    this.initEmps();
                                }
                            })
                        }
                    })
                }else{
                    //先校验再提交添加的数据
                    this.$refs['empForm'].validate(valid=>{
                        if (valid){
                            this.postRequest("employee/basic/",this.emp).then(resp=>{
                                if (resp){
                                    this.dialogVisible=false;
                                    //刷新页面
                                    this.initEmps();
                                }
                            })
                        }
                    })
                }


            },
            //点击部门时关闭,data为点击那一项
            handleNodeClick(data) {
                this.inputDepName=data.name;
                this.emp.departmentId=data.id;
                this.popvisible=!this.popvisible;
            },
            showDepView(){
                this.popvisible=!this.popvisible;
            },
            initPositions(){
                if (!window.sessionStorage.getItem("positions")){
                    this.getRequest("employee/basic/positions").then(resp=>{
                        this.positions=resp;
                    })
                }
            },
            getMaxWorkID(){
                    this.getRequest("employee/basic/maxWorkID").then(resp=>{
                        if (resp){
                            this.emp.workId=resp.obj;
                        }

                    })
            },
            initData(){
                //如果不存在nations
              if (!window.sessionStorage.getItem("nations")){
                  this.getRequest("employee/basic/nations").then(resp=>{
                      this.nations=resp;
                      //保存nations
                      window.sessionStorage.setItem("nations",JSON.stringify(resp));
                  })
              }else{
                  //如果存在
                  this.nations=JSON.parse(window.sessionStorage.getItem("nations"));
              }

                if (!window.sessionStorage.getItem("joblevels")){
                    this.getRequest("employee/basic/joblevels").then(resp=>{
                        this.joblevels=resp;
                        window.sessionStorage.setItem("joblevels",JSON.stringify(resp));
                    })
                }else{
                    //如果存在
                    this.joblevels=JSON.parse(window.sessionStorage.getItem("joblevels"));
                }

                if (!window.sessionStorage.getItem("politicsstatus")){
                    this.getRequest("employee/basic/politicsstatus").then(resp=>{
                        this.politicsstatus=resp;
                        window.sessionStorage.setItem("politicsstatus",JSON.stringify(resp));
                    })
                }else{
                    //如果存在
                    this.politicsstatus=JSON.parse(window.sessionStorage.getItem("politicsstatus"));
                }

                if (!window.sessionStorage.getItem("deps")){
                    this.getRequest("employee/basic/deps").then(resp=>{
                        this.allDeps=resp;
                        window.sessionStorage.setItem("deps",JSON.stringify(resp));
                    })
                }else{
                    //如果存在
                    this.allDeps=JSON.parse(window.sessionStorage.getItem("deps"));
                }
            },
            showEditEmpView(data){
                this.title='修改员工信息';
                this.emp=data;
                this.inputDepName=data.department.name;
                this.dialogVisible=true;
            },
            showAddEmpView(){
                //添加时，emp置空
                this.emptyEmp();
                this.title='添加员工';
                //因为职位经常变化，所以点展示的时候就更新
                this.initPositions();
                this.getMaxWorkID();
              this.dialogVisible=true;
            },
            currentChange(currentPage){
                this.page=currentPage;
                this.initEmps();
    },
            initEmps(){
                this.loading=true;
                this.getRequest("employee/basic/?page="+this.page+"&size="+this.size+"&keyword="+this.keyword).then(resp=>{
                    this.loading=false;
                    if (resp){
                        //为后端的resp的data，resp包括total和data
                        this.emps=resp.data;
                        this.total=resp.total;
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>