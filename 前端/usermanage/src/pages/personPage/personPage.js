import Vue from 'vue'
import PersonPage from './PersonPage.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)

new Vue({
    render: h => h(PersonPage),
}).$mount("main");
