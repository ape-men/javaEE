<template>
    <div class="main">
        <el-container>    
            <el-header style="text-align: right; font-size: 12px">
                <el-dropdown>
                    <span>
                        <img v-if="personInfo.status==1" class="faceicon" src="@/assets/xgg.gif"/>
                        <img v-if="personInfo.status==2" class="faceicon" src="@/assets/zt.jpg"/>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>
                            <span @click="logout">退出登录</span>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>
        </el-container>
        <el-container>
            <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
                <el-menu
                    default-active="1"
                    class="el-menu-vertical-demo"
                    @select="handleSelect"
                >
                    <el-menu-item index="1">
                        <i class="el-icon-menu"></i>
                        <span slot="title">个人信息</span>
                    </el-menu-item>
                    <el-menu-item index="2" v-if="personInfo.status===2">
                        <i class="el-icon-menu"></i>
                        <span slot="title">用户管理</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <component :is="routenow" :info="personInfo"></component>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import {
    loginFetcher, personPageFetcher, superAdminFetcher
} from "../../js/_fetchers.js"
import ControlTable from "../../components/table/ControlTable"
import PersonInfo from "./PersonInfo"
export default {
    data() {
        return {
            personInfo: {
                email: String,
                status: Number,
                userId: Number,
                userName: String,
            },
            routes: ['', 'person-info', 'control-table'],
            routenow: 'person-info',
        }
    },
    created() {
        personPageFetcher.get("personInfo").then((res) => {
            this.personInfo = res.user;
        });
    },
    methods: {
        handleSelect(key, keyPath) {
            this.routenow = this.routes[key];
            console.log(this.routenow)
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            this.routenow = '';
            console.log(key, keyPath);
        },
        logout() {
            loginFetcher.get("logout");
            localStorage.removeItem('token');
            location.href = "./login"
        }
    },
    components: {
        "control-table": ControlTable,
        "person-info": PersonInfo,
    }
}
</script>

<style lang="less">
    html, body, .main {
        margin: 0;
        padding: 0;
        width: 100%;
        height: 100%;
    }
    .faceicon {
        margin-top: 10px;
        width: 40px;
        height: 40px;
    }
</style>