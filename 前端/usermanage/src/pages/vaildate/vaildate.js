import Vue from 'vue'
import Vaildate from './Vaildate.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)

new Vue({
    render: h => h(Vaildate),
}).$mount("main");
