<template>
    <div>
        <el-table :data="recordList">
            <el-table-column label="日期" width="200">
                <template slot-scope="scope">
                    <span>{{ new Date(scope.row.loginTime).toLocaleString() }}</span>
                </template>
            </el-table-column>
            <el-table-column property="ip" label="ip" width="150"></el-table-column>
        </el-table>
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="pageNum"
            :page-size="10"
            layout="total, prev, pager, next, jumper"
            :total="total"
        ></el-pagination>
    </div>
</template>

<script>
import { superAdminFetcher } from "../../js/_fetchers.js"
export default {
    props: ["userId"],
    data() {
        return {
            info: String,
            recordList: [],
            pageNum: 0,
            pages: Number,
            total: 0,
        }
    },
    methods: {
        loadRecord(pageNum) {
            this.loginRecord = [];
            superAdminFetcher.get("getLoginRecord", {"userId": this.userId, pageNum}).then((res) => {
                if (~res.success) {
                    this.recordList = res.recordList;
                    console.log(res.recordList);
                    this.pageNum = res.pageNum;
                    this.pages = res.pages;
                    this.total = res.total;
                    this.loadRecord(this.pageNum);
                }
            });
        },
        handleCurrentChange(val) {
            this.loadRecord(val);
        }
    }
}
</script>

<style>

</style>