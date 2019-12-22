<template>
    <div class="container">
        <ul class="etable" @click="changeTag">
            <tree-node 
                v-for="(node, index) of data.children"
                :key="index"
                depth="1">
                <template slot="leaf">
                    <span
                        class="comment"
                        :title="node.commentHTMLescape+'\n'+node.propname"
                        >{{ node.name }}</span>
                </template>
            </tree-node>
        </ul>
    </div>
</template>

<script>
import Container from "../Container.vue"
export default {
    props: ["data"],
    data() {
        return {
            currentTag : null,
        }
    },
    methods: {
    
    },
    components: {"": Container}
}
</script>

<style lang="less" scoped>

</style>