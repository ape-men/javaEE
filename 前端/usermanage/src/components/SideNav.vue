<script>
import TabNav from "@/components/TabNav.vue";
export default {
    extends: TabNav,
    data() {
        return {
            tucked: false,
        }
    },
    methods: {
        switchTab(e) {
            let elm = listen.hasPass(e, "[data-tab]");
            if (!utils.isset(elm)) return;
            if (elm.dataset.tab == this.chosen) {
                this.toggle();
            } else {
                if (utils.isset(this.chosen)) {
                    this.unchose(this.chosen);
                }
                this.chose(elm.dataset.tab);
                this.toggle(false);
            }
        },
        toggle(code) {
            if (!utils.isset(code)) code = !this.tucked;
            this.tucked = code;
        }
    }
}
</script>
