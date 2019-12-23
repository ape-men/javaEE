<template>
    <div></div>
</template>

<script>
import {registerFetcher} from "../../js/_fetchers.js"
export default {
    data() {
        return {
            status: '',
        }
    },
    created() {
        let check = window.location.href.split("?check=")[1];
        registerFetcher.get("checkEmail", {check}).then((res) => {
            console.log(res);
            let [title, content] = (~res.success) ? 
                ['验证成功', '点击转入登录页面'] : ['验证失败', '请重新注册']
            this.$alert(content, title, {
                confirmButtonText: '确定',
                callback: () => window.location.href = "./Login",
            });
        })
    }
}
</script>