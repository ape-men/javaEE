<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span>{{ info.userName }}</span>
            <el-button class="opt" type="text" @click="dialogVisible = true">修改密码</el-button>
            <el-dialog
                title="更改密码"
                :visible.sync="dialogVisible"
                width="30%"
            >
                <el-form ref="resetForm" :rules="resetRules" :model="resetForm">
                    <el-form-item prop="oldPassword">
                        <el-input
                            type="input"
                            placeholder="请输入旧密码"
                            v-model="resetForm.oldPassword"
                            show-password
                            prefix-icon="el-icon-edit"
                        ></el-input>
                    </el-form-item>
                    <el-form-item prop="newPassword">
                        <el-input
                            type="input"
                            placeholder="请输入新密码"
                            v-model="resetForm.newPassword"
                            show-password
                            prefix-icon="el-icon-edit"
                        ></el-input>
                    </el-form-item>
                    <el-form-item prop="comfirmPassword">
                        <el-input
                            type="password"
                            placeholder="请再次输入新密码"
                            v-model="resetForm.comfirmPassword"
                            prefix-icon="el-icon-edit"
                        ></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="changePassword(resetForm)">确 定</el-button>
                </span>
            </el-dialog>
        </div>
        <div class="text item">用户类型: {{ info.status===1 ? "普通用户":"管理员" }}</div>
        <div v-if="info.status===2" class="text item">{{ info.createTime }}</div>
        <div class="text item">邮箱: {{ info.email }}</div>
    </el-card>
</template>

<script>
import { personPageFetcher } from "../../js/_fetchers.js"
import {hex_md5} from "../../js/md5.js"
export default {
    props: ['info'],
    data() {
        return {
            resetForm: {
                oldPassword: '',
                newPassword: '',
                comfirmPassword: '',
            },
            dialogVisible: false,
            resetRules: {
                oldPassword: [
                    { required: true, message: '必须输入密码'}
                ],
                newPassword: [
                    { validator: (rule, value, callback) => {
                        if (value.length < 6 || value.length > 18) {
                            callback(new Error("密码长度不得少于6位"));
                        } else callback();
                    }, trigger: 'blur' },
                    { required: true, message: '必须输入密码'}
                ],
                comfirmPassword: [
                    { validator: (rule, value, callback) => {
                        if (value !== this.resetForm.newPassword) {
                            callback(new Error('两次输入密码不一致!'));
                        } else callback();
                    }, trigger: 'blur' },
                    { required: true, message: '请再次输入密码'}
                ],
            }
        }
    },
    methods: {
        changePassword(form) {
            this.$refs.resetForm.validate((valid) => {
                if (valid) {
                    delete form.comfirmPassword;
                    let upload = Object.assign({}, form);
                    upload.password = hex_md5(upload.password);
                    upload.questionAnswer = hex_md5(upload.questionAnswer);
                    personPageFetcher.get("submitRegisterInfo", upload).then((res) => {
                        if (~res.success) {
                            this.$message('更改成功');
                        } else {
                            this.$message.error('未知错误, 更改失败');
                        }
                    });
                } else return false;
            });
        }
    }
}
</script>

<style>
    .opt.el-button {
        float: right;
        padding: 3px 0;
    }
    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 480px;
    }
</style>
