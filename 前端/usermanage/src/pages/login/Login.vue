<template>
    <el-form ref="loginForm" :model="loginForm">
        <el-form-item prop="username" :rules="[{ required: true, message: '用户名不能为空'}]">
            <el-input 
                type="input"
                v-model="loginForm.username"
                placeholder="请输入用户名"
                prefix-icon="el-icon-user-solid"
            ></el-input>
        </el-form-item>
        <el-form-item prop="password" :rules="[{ required: true, message: '密码不能为空'}]">       
            <el-input
                type="input"
                placeholder="请输入密码"
                v-model="loginForm.password"
                show-password
                prefix-icon="el-icon-edit"
            ></el-input>
        </el-form-item>
        <el-form-item ref="captchaContainer" id="captchaContainer" prop="captcha" :rules="[{ required: true, message: '验证码不能为空'}]">       
            <el-input
                id="captchaBar"
                type="input"
                placeholder="请输入验证码"
                v-model="loginForm.captcha"
                prefix-icon="el-icon-edit"
            ></el-input>
            <img id="captcha" ref="captcha" title="看不清, 换一张" @click="refreshCaptcha" src="http://www.bluemsun.work:8080/Kaptcha"/>
        </el-form-item>
        <a ref="forget" @click="forgetPassword" class="a1">忘记密码</a>
        <el-button type="primary" @click="login(loginForm)">登录</el-button>
    </el-form>
</template>

<script>
import { loginFetcher } from "../../js/_fetchers.js"
import { hex_md5 } from "../../js/md5.js"
export default {
    name: 'Login',
    data() {
        return {
            loginForm: {
                username: '',
                password: '',
                captcha: '',
            },
        }
    },
    methods: {
        refreshCaptcha() {
           this.$refs.captcha.src = "http://www.bluemsun.work:8080/Kaptcha?" + parseInt(Math.random() * 19260817);
           this.$refs.captchaContainer.resetField();
        },
        login(form) {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    loginFetcher.post("login", {
                        "userName": form.username,
                        "password": hex_md5(form.password),
                        "verifyCodeActual": form.captcha,
                    }).then((res) => {
                        console.log(res);
                        switch(res.success) {
                            case 0: {
                                this.$message({
                                    type: 'warning',
                                    message: '登录失败, 请先验证邮箱'
                                });
                            } break;
                            case -1: {
                                this.$message({
                                    type: 'warning',
                                    message: '登录失败, 用户已被封禁'
                                });
                            } break;
                            case 1: {
                                this.$message({
                                    type: 'info',
                                    message: '登录成功'
                                });
                                this.setToken(res.token);
                                location.href = "./PersonPage";
                            } break;
                            case 2: {
                                this.$prompt('请更新密码', '提示', {
                                    confirmButtonText: '确定',
                                    inputType: 'password',
                                    inputValidator: password => password.length >= 6,
                                    inputErrorMessage: '密码长度不少于6位'
                                }).then(({ value }) => this.$prompt('确认密码', '提示', {
                                        confirmButtonText: '确定',
                                        inputType: 'password',
                                        inputValidator: password => password === value,
                                        inputErrorMessage: '两次输入的密码不同'
                                    })
                                ).then(({ value }) => loginFetcher.get("updatePassword", {"newPassword": hex_md5(value)})
                                ).then((res) => {
                                    this.$message({
                                        type: 'success',
                                        message: '修改成功',
                                    })
                                    this.setToken(res.token);
                                    location.href = "./PersonPage";
                                }).catch(() => {
                                    this.$message({
                                        type: 'info',
                                        message: '必须更新密码才能登录'
                                    });
                                });
                            } break;
                            case -1000: {
                                this.$message({
                                    type: 'error',
                                    message: '用户名或密码错误',
                                });
                            } break;
                            case -1003: {
                                this.$message({
                                    type: 'error',
                                    message: '验证码错误',
                                });
                            } break;
                            default: {
                                this.$message({
                                    type: 'error',
                                    message: '未知错误'
                                });
                            }
                        }
                    });
                    this.refreshCaptcha();
                } else return false;
            });
        },
        setToken(token) {
            localStorage.setItem("token", token);
        },
        forgetPassword() {
            this.$emit("update:forget", true);
        }
    },
}
</script>

<style lang="less">
    .a1 {
        cursor: pointer;
        text-decoration: none;
        color: gray;
        line-height: 20px;
        font-size: 13px;
        margin-inline-start: auto;
    }
    button.el-button {
        display: block;
        margin: 0 auto;
        width: 100%;
    }
    #captchaContainer .el-form-item__content {
        display: flex;
        #captchaBar {
            width: 180px;
        }
        #captcha {
            cursor: pointer;
            width: 85px;
        }
    }
</style>
