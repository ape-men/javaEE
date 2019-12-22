<template>
    <el-form ref="forgetForm" :model="forgetForm">
        <el-steps id="forgetSteps" :active="process" simple>
            <el-step title="选择账号" icon="el-icon-search"></el-step>
            <el-step title="安全验证" icon="el-icon-message"></el-step>
        </el-steps>
        <el-form-item v-show="process===0" prop="username" :rules="[{ required: true, message: '用户名不能为空'}]">
            <el-input 
                type="input"
                v-model="forgetForm.username"
                placeholder="请输入用户名或邮箱"
                prefix-icon="el-icon-user-solid"
            ></el-input>
        </el-form-item>
        <el-form-item v-show="process===1" :label="forgetForm.useEmail ? '邮箱验证':'密保验证'" prop="useEmail">
            <el-switch v-model="forgetForm.useEmail"></el-switch>
        </el-form-item>
        <el-form-item v-show="process===1 && forgetForm.useEmail" prop="checkString">
            <el-input 
                placeholder="验证码"
                v-model="forgetForm.checkString"
            >
                <el-button @click="sendCheck" slot="append" icon="el-icon-message">发送</el-button>
            </el-input>
        </el-form-item>
        <el-form-item 
            v-show="process===1 && !forgetForm.useEmail"
            prop="questionAnswer"
            :label="securityQuestion"
            label-position="top"
        >
            <el-input 
                type="input"
                v-model="forgetForm.questionAnswer"
                placeholder="请回答密保问题"
                prefix-icon="el-icon-user-solid"
            ></el-input>
        </el-form-item>
        <el-form-item
            v-show="process===1"
            prop="newPassword"
            :rules="[{ required: true, message: '必须填写新密码'}]"
        >
            <el-input 
                v-model="forgetForm.newPassword"
                placeholder="新密码"
                show-password
                prefix-icon="el-icon-edit"
            ></el-input>
        </el-form-item>
        <a ref="forget" @click="backToLogin" class="a1">返回</a>
        <el-button type="primary" @click="next(forgetForm)">{{ actionComments[process] }}</el-button>
    </el-form>
</template>

<script>
import {loginFetcher, passwordFetcher} from "../../js/_fetchers.js"
import {hex_md5} from "../../js/md5.js"
export default {
    data() {
        return {
            forgetForm: {
                username: '',
                useEmail: true,
                checkString: '',
                questionAnswer: '',
                newPassword: '',
            },
            process: 0,
            actionComments: ['确认', '验证', '完成'],
            securityQuestion: '',
            questions: [
                "我的小学班主任叫什么名字",
                "我现在在哪里上学",
                "我的父亲叫什么名字",
                "我的母亲是谁",
                "我最好的朋友叫什么名字",
                "我是哪一天出生的",
                "我的理想是什么",
                "我最喜欢的东西是什么",
                "我最看重什么东西",
                "我小时候的梦想是什么",
            ],
        }
    },
    methods: {
        backToLogin() {
            this.$refs.forgetForm.resetFields();
            this.$emit("update:forget", false);
        },
        next(form) {
            switch(this.process) {
                case 0: {
                    loginFetcher.post("findPassword", {
                        userName: form.username
                    }).then((res) => {
                        switch (res.success) {
                            case -1: {
                                this.$message({
                                    type: 'error',
                                    message: '用户不存在'
                                });
                            } break;
                            case -1000: {
                                this.$message({
                                    type: 'warning',
                                    message: '该用户未验证邮箱, 请先进行验证'
                                });
                            } break;
                            case 1: {
                                this.securityQuestion = this.questions[res.serQuestion];
                                this.process++;
                            } break;
                        }
                    })
                } break;
                case 1: {
                    passwordFetcher.post("findPassword", {
                        userName: form.username,
                        mode: form.useEmail ? 1 : 2,
                        checkString: form.checkString,
                        questionAnswer: hex_md5(form.questionAnswer),
                        newPassword: hex_md5(form.newPassword),
                    }).then((res) => {
                        if (~res.success) {
                            this.process++;
                        }
                    })
                } break;
                case 2: {
                    backToLogin();
                } break;
            }
        },
        sendCheck() {
            passwordFetcher.get("sendCheckString").catch((res) => {
                this.$notify('发送失败');
            })
        }
    }
}
</script>

<style lang="less">
    #forgetSteps {
        margin-bottom: 15px;
    }
</style>
