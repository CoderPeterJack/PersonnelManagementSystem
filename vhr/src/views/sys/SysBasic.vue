<template>
    <div>
        <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="部门管理" name="first"><DepMana></DepMana></el-tab-pane>
            <el-tab-pane label="职位管理" name="second"><PosMana></PosMana></el-tab-pane>
            <el-tab-pane label="职称管理" name="third"><JobLevelMana></JobLevelMana></el-tab-pane>
             <el-tab-pane label="奖惩规则" name="fourth"><EcMana></EcMana></el-tab-pane>
            <el-tab-pane label="权限组" name="fifth"><PermissMana></PermissMana></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    // 1、引入组件
    import DepMana from "../../components/sys/DepMana";
    import EcMana from "../../components/sys/EcMana";
    import JobLevelMana from "../../components/sys/JobLevelMana";
    import PermissMana from "../../components/sys/PermissMana";
    import PosMana from "../../components/sys/PosMana";
    export default {
        name: "SysBasic",
        data(){
            return{
                activeName:'second'
            }
        },
        // 2、注册组件
        components: {
            //'DepMana': DepMana,
            // 如果key和value是一样就只写一个
            //下面相对于代替了html标签
            DepMana,
            PosMana,
            PermissMana,
            EcMana,
            JobLevelMana
        }
    }
</script>

<style scoped>

</style>