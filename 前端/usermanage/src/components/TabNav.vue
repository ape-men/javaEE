<template>
    <ul @click="switchTab">
        <li v-for="(tab, index) of tablists" 
            :key="index" 
            :data-tab="index"
            :title="tab.label"
            :class="{ active: chosen == index }">
            <slot name="tab" :tab="tab">{{ tab.label }}</slot>
        </li>
        <slot name="default"></slot>
    </ul>
</template>
<script>
import listen from "@/components/TabNav.vue";
export default {
    props: ["panes"],
    data() {
        return {
            chosen: null,
            tablists: [],
        }
    },
    created() {
        this.tablists = this.$parent.$children.filter(function (item) {
            return item.$options.name.endsWith('panel') && item.$root.matches(this.panes || "*");
        });
    },
    methods: {
        switchTab(e) {
            let elm = listen.hasPass(e, "[data-tab]");
            if (!utils.isset(elm)) return;
            if (elm.dataset.tab == this.chosen) return;
            if (utils.isset(this.chosen)) {
                this.unchose(this.chosen);
            }
            this.chose(elm.dataset.tab);
        },

        chose(index) {
            this.chosen = index;
            console.log(this.tablists[index]);
            this.tablists[index].classList.add("active");
            this.tablists[index].classList.add("_active");
        },

        unchose(index) {
            console.log(this.tablists[index]);
            this.tablists[index].classList.remove("active");
        }
    }
}
</script>

    