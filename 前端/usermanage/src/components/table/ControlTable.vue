<template>
    <div>
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
            <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button
                    v-if="scope.row"
                    size="mini"
                    @click="handleOpt(scope.$index, scope.row)"
                >操作</el-button>
                <el-popover
                    ref="popover4"
                    placement="left"
                    width="600"
                    trigger="click"
                >
                    <el-table :data="gridData">
                        <el-table-column width="150" property="date" label="日期"></el-table-column>
                        <el-table-column width="100" property="name" label="姓名"></el-table-column>
                        <el-table-column width="300" property="address" label="IP"></el-table-column>
                    </el-table>
                </el-popover>
                <el-button
                    size="mini"
                    type="danger"
                    @click="handleViewLogin(scope.$index, scope.row)"
                >查看登录记录</el-button>
            </template>
            </el-table-column>
        </el-table>
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
import {superAdminFetcher} from "../../js/_fetchers.js" 
export default {
    data() {
        return {
            info: String,
            userList: Array,
            pageNum: 0,
            pages: Number,
            total: Number,
        }
    },
    created() {
        this.updateList(1);
    },
    methods: {
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
        handleEdit(index, row) {
            console.log(index, row);
        },
        handleViewLogin(index, row) {

            console.log(index, row);
        },
        handleCurrentChange(val) {
            this.updateList(val);
        }
    }
}
</script>

<style lang="less" scoped>

</style>
