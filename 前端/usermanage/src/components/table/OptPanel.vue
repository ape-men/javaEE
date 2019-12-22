<template>
    <el-dialog title="修改用户信息" :visible.sync="visible">
        <el-switch
            class="opts"
            v-model="info.status"
            :active-value="2"
            :inactive-value="1"
            inactive-text="管理员">
        </el-switch>
        <el-switch
            class="opts"
            style="margin-left: 30px;"
            v-model="info.available"
            :active-value="2"
            :inactive-value="1"
            inactive-text="封禁">
        </el-switch>
        <div slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="confirmEdit">确定修改</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { superAdminFetcher } from "../../js/_fetchers.js" 
export default {
    data() {
        return {
            info: {
                status: Number,
                available: Number,
            },
            _info: {
                status: Number,
                available: Number,
            },
            visible: false,
        }
    },
    methods: {
        open(info) {
            this._info = info;
            Object.assign(this.info, this._info);
            console.log(this.info);
            this.visible = true;
        },
        confirmEdit() {
            Object.assign(this._info, this.info);
            superAdminFetcher.get("updateUserStatusAndAvailable", this._info).then((res) => {
                if (~res.success) this.$message("修改成功");
                else this.$message.error("修改失败");
                this.visible = false;
            })
        }
    }
}
</script>

<style lang="less">
    .opts .el-switch__label {
        color: #409EFF !important;
        &.is-active {
            color: #303133 !important;
        }
    }
</style>