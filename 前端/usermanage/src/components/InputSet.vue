<template>
    <div class="inputSet">
        <input type="text" 
            v-bind="$attrs"
            v-bind:value="value"
            v-on="inputListeners"/>
        <div @click="clear" class="clearBtn" :class="{ disabled: value == '' }">
            <i class="codicon codicon-clear-all"></i>
        </div>
    </div>
</template>

<script>
export default {
    name: "input-set",
    props: ["immediate"],
    data() {
        return {
            value: '',
        }
    },
    computed: {
        inputListeners() {
            var vm = this;
            return Object.assign({}, this.$listeners, {
                input(event) {
                    vm.$emit('input', event.target.value);
                }
            });
        }
    },
    methods: {
        clear() {
            this.value = '';
            this.$emit('input', '');
        },
        oninput(e, force) {
            if (this.input.value == '') this.clear.disable();
            else this.clear.enable();
            if (this.immediate || force) action(this.input);
        },
    }
}
</script>

<style lang="less" scoped>
.inputSet {
    height: 30px;
    box-sizing: border-box;
    margin: 8px;
    input {
        height: 20px;
        width: auto;
        margin: 0px;
        padding: 5px 5px;
        background-color: #555;
        border-width: 0px;
        color: #ddd;
    }
    .staticIcon {
        display: inline-block;
        width: 12px;
        height: 12px;
        margin: -2px -6px;
        padding: 9px;
        user-select: none;
    }
}
</style>