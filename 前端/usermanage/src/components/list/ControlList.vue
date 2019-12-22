<template>
    <div class="container">
        <ul class="etable">
            <tree-node 
                v-for="(node, index) of data.children"
                :key="index"
                depth="1">
                <template slot="leaf">
                <span
                    class="comment"
                    :title="node.commentHTMLescape+'\n'+node.propname"
                    >{{ node.name }}</span>
                    <components is="'list-' + node.type" node="node"></components>
                </template>
            </tree-node>
        </ul>
    </div>
</template>

<script>
export default {
    props: ["data"],
    components: listControls,
}
</script>