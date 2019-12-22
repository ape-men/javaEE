<template>
    <el-form ref="registerForm" :rules="registerRules" :model="registerForm">
        <el-form-item prop="userName">
            <el-input 
                type="input"
                v-model="registerForm.userName"
                placeholder="请输入用户名"
                prefix-icon="el-icon-user-solid"
            ></el-input>
        </el-form-item>
        <el-form-item prop="password">       
            <el-input
                type="input"
                placeholder="请输入密码"
                v-model="registerForm.password"
                show-password
                prefix-icon="el-icon-edit"
            ></el-input>
        </el-form-item>
        <el-form-item prop="comfirmPassword">       
            <el-input
                type="password"
                placeholder="请再次输入密码"
                v-model="registerForm.comfirmPassword"
                prefix-icon="el-icon-edit"
            ></el-input>
        </el-form-item>
        <el-form-item prop="email">       
            <el-input
                type="input"
                placeholder="请输入邮箱"
                v-model="registerForm.email"
                prefix-icon="el-icon-edit"
            ></el-input>
        </el-form-item>
        <el-form-item label="安全问题" prop="securityQuestion">       
            <el-select v-model="registerForm.securityQuestion">
                <el-option
                    v-for="(item, index) in questions"
                    :key="index"
                    :label="item"
                    :value="index+1"
                ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item prop="questionAnswer">       
            <el-input
                type="input"
                placeholder="请输入安全问题的答案"
                v-model="registerForm.questionAnswer"
                prefix-icon="el-icon-edit"
            ></el-input>
        </el-form-item>
        <el-button type="primary" @click="register(registerForm)">注册</el-button>
    </el-form>
</template>

<script>
import {registerFetcher} from "../../js/_fetchers.js"
import {hex_md5} from "../../js/md5.js"
export default {
    name: 'Register',
    data() {
        return {
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
            registerForm: {
                userName: '',
                password: '',
                confirmPassword: '',
                securityQuestion: 1,
                questionAnswer: '',
            },
            registerRules: {
                userName: [
                    { validator: (rule, value, callback) => {
                        registerFetcher.checkoutUserName(value).then((res) => {
                            if (~res.success) callback();
                            else callback(new Error("用户名已被占用"));
                        });
                    }, trigger: 'blur' },
                    { required: true, message: '必须输入用户名'}
                ],
                password: [
                    { validator: (rule, value, callback) => {
                        if (value.length < 6 || value.length > 18) {
                            callback(new Error("密码长度不得少于6位"));
                        } else callback();
                    }, trigger: 'blur' },
                    { required: true, message: '必须输入密码'}
                ],
                comfirmPassword: [
                    { validator: (rule, value, callback) => {
                        if (value !== this.registerForm.password) {
                            callback(new Error('两次输入密码不一致!'));
                        } else callback();
                    }, trigger: 'blur' },
                    { required: true, message: '请再次输入密码'}
                ],
                email: [
                    { validator: (rule, value, callback) => {
                        if (/[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/.test(value)){
                            registerFetcher.checkoutEmail(value).then((res) => {
                                console.log(res);
                                if (~res.success) callback();
                                else callback(new Error("邮箱已被注册"));
                            })
                        } else callback(new Error("邮箱格式不正确"));
                    }, trigger: 'blur' },
                    { required: true, message: '必须输入邮箱'}
                ]
            }
        }
    },
    methods: {
        register(form) {
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    delete form.comfirmPassword;
                    let upload = Object.assign({}, form);
                    upload.password = hex_md5(upload.password);
                    upload.questionAnswer = hex_md5(upload.questionAnswer);
                    console.log(upload);
                    registerFetcher.post("submitRegisterInfo", upload).then((res) => {
                        if (~res.success) {
                            this.$alert('注册成功', '请去邮箱确认', {
                                confirmButtonText: '确定',
                            })
                        } else {
                            this.$alert('注册失败', '未知错误！', {
                                confirmButtonText: '确定',
                            })
                        }
                    });
                } else return false;
            });
        }
    },
}
</script>

<style lang="less">
</style>
