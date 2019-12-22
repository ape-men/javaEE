<template>
    <main>
        <el-tabs class="login" v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="登录" name="first">
                <login-pane :forget.sync="forget" v-show="!forget"></login-pane>
                <forget-password :forget.sync="forget" v-show="forget"></forget-password>
            </el-tab-pane>
            <el-tab-pane label="注册" name="second">
                <register-pane></register-pane>
            </el-tab-pane>
        </el-tabs>
    </main>
</template>

<script>
import Login from './Login.vue'
import ForgetPassword from './ForgetPassword.vue'
import Register from './Register.vue'
export default {
    data() {
        return {
            activeName: 'first',
            forget: false,
        };
    },
    methods: {
        handleClick(tab, event) {
            console.log(tab, event);
        }
    },
    components: {
        "login-pane": Login, 
        "forget-password": ForgetPassword,
        "register-pane": Register,
    },
}
</script>

<style>
    .login {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        border-radius: 4px;
        width: 300px;
        padding: 30px;
        margin: 40px auto;
        background-color: white;
    }
    form {
        display: flex;
        flex-direction: column;
    }
</style>
