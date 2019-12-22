<template>
    <li @click="setFold">
        <slot name="[nodeType]" :node="node">
            <i :class="['codicon', 'codicon-chevron-' + ( isFold ? 'right' : 'down' )]"></i>
            <span>{{ node.comment }}</span>
        </slot>
        <ul v-if="nodeType == 'gap'" v-show="!isFold">
            <tree-node 
                v-for="(child, index) of node.children"
                :key="index"
                :node="child"
                :depth="depth+1">
                <template v-if="$slots.gap" #gap>
                    <slot name="gap"></slot>
                </template>
                <template>
                    <slot #leaf></slot>
                </template>
            </tree-node>
        </ul>
    </li>
</template>

<script>
export default {
    props: ["node", "depth"],
    data() {
        return {
            isFold: false,
        }
    },
    computed: {
        nodeType() {
            return node.children.length ? "gap" : "leaf";
        }
    },
    method: {
        /** 折叠函数 @param {Boolean} order 为true时折叠, false时不折叠 */
        setFold(order) {
            if (!utils.isset(order)) order = !this.isFold;
            this.isFold = order;
        }
    }
}
</script>