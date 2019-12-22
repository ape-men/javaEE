import Vue from 'vue'
import Frame from './Frame.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import loginbg from '../../js/loginbg.js'

Vue.use(ElementUI)
Vue.use(loginbg)

new Vue({
    render: h => h(Frame),
}).$mount("main");
