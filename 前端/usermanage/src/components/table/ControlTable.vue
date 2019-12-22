<template>
    <div>
        <el-row class="searchbar">
            <el-col :span="24">
                <div class="grid-content bg-purple">
                    <el-input
                        class="searchset"
                        placeholder="搜索用户"
                        v-model="keyword"
                        @input="search(keyword)"
                    >
                        <el-button slot="append" @click="keyword = '', updateList(1)" icon="el-icon-close"></el-button>
                    </el-input>
                    <el-button icon="el-icon-download" @click="downloadUsers">导出</el-button>
                </div>
            </el-col>
        </el-row>
        <el-table :data="userList" style="width: 100%">
            <el-table-column label="id" property="userId" width="50"></el-table-column>
            <el-table-column label="用户名" property="userName" width="100"></el-table-column>
            <el-table-column label="身份" width="80">
                <template slot-scope="scope">
                    <span>{{ scope.row.status==1 ? "普通用户" : "管理员" }}</span>
                </template>
            </el-table-column>
            <el-table-column label="状态" width="80">
                <template slot-scope="scope">
                    <span>{{ ['未验证', '正常', '封号'][scope.row.available] }}</span>
                </template>
            </el-table-column>
            <el-table-column label="邮箱" property="email" width="150"></el-table-column>
            <el-table-column label="注册时间" width="200">
                <template slot-scope="scope">
                    <span>{{ new Date(scope.row.createTime).toLocaleString() }}</span>
                </template>
            </el-table-column>
            <el-table-column label="密码编辑时间" width="200">
                <template slot-scope="scope">
                    <span>{{ new Date(scope.row.lastEditTime).toLocaleString() }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button size="mini" @click="openEditPanel(scope.row)">操作</el-button>
                    <el-button size="mini" @click="viewLogin(scope.row.userId)">登录记录</el-button>
                </template>
            </el-table-column>
        </el-table>
        <opt-panel ref="optPanel"></opt-panel>
        <el-dialog title="登录记录" :visible.sync="recordVisible">
            <login-record ref="record" :userId="recUserId"></login-record>
            <div slot="footer" class="dialog-footer">
                <el-button @click="downloadLogin(recUserId)">导 出</el-button>
                <el-button type="primary" @click="recordVisible = false">确 定</el-button>
            </div>
        </el-dialog>
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="pageNum"
            :page-size="15"
            layout="total, prev, pager, next, jumper"
            :total="total"
        ></el-pagination>
    </div>
</template>

<script>
import { superAdminFetcher } from "../../js/_fetchers.js"
import CSVmaker from "../../js/csvmaker.js"
import LoginRecord from "./LoginRecord"
import OptPanel from "./OptPanel"
export default {
    data() {
        return {
            info: String,
            userList: [],
            pageNum: 0,
            pages: Number,
            total: 0,
            optPanel: false,
            recordVisible: false,
            loginRecord: [],
            recUserId: Number,
            keyword: '',
        }
    },
    created() {
        this.updateList(1);
    },
    methods: {
        downloadUsers() {
            superAdminFetcher.get("getDownload", {type: 1}).then((res) => {
                CSVmaker.download("users.csv", [
                    'id', '身份', '状态', '邮箱', '创建时间', '最后编辑时间'
                ], res.userList, (data) => [
                    data.userId,
                    data.status===1 ? '普通用户' : '管理员',
                    ['未验证', '正常', '封号'][data.available],
                    data.email,
                    new Date(data.createTime).toLocaleString(),
                    new Date(data.lastEditTime).toLocaleString()
                ].join(','));
            })
        },
        downloadLogin(userId) {
            superAdminFetcher.get("getDownload", {type: 2, userId}).then((res) => {
                CSVmaker.download('user#'+userId+'LoginRecord.csv', [
                    'ip', '时间'
                ], res.recordList, (data) => [
                    data.ip,
                    new Date(data.loginTime).toLocaleString(),
                ].join(','));
            })
        },
        updateList(num) {
            superAdminFetcher.get("listNowUser", {pageNum: num})
                .then((res) => {
                    if (~res.success) {
                        this.userList = res.userList;
                        this.pageNum = res.pageNum;
                        this.pages = res.pages;
                        this.total = res.total;
                    }
                });
        },
        openEditPanel(info) {
            this.optPanel = true;
            this.$refs.optPanel.open(info);
        },
        viewLogin(id) {
            this.recUserId = id;
            let a = this.$refs;
            setTimeout(() => {
                a.record.loadRecord(1);
            }, 5);
            this.recordVisible = true;
        },
        handleCurrentChange(val) {
            this.updateList(val);
        },
        search(userString) {
            if (!userString) {
                this.updateList(1);
            } else {
                superAdminFetcher.get("similarFind", {userString}).then((res) => {
                    if (~res.success) {
                        this.userList = res.userList;
                        this.total = this.userList.length;
                    }
                });
            }
        }
    },
    components: {
        "login-record": LoginRecord,
        "opt-panel": OptPanel,
    }
}
</script>

<style lang="less" scoped>
    .searchbar {
        &:last-child {
            margin-bottom: 0;
        }
        .bg-purple {
            background: #d3dce6;
        }
        .grid-content {
            border-radius: 4px;
            min-height: 36px;
        }
        .searchset {
            width: 50%;
            margin: 0 20%;
        }
    }
</style>
