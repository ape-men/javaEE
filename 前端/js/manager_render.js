//JavaScript代码区域
layui.use('element', function(){
    var element = layui.element;
    
});

var init = function() {
    layui.use('table', function(){
        var table = layui.table;
        
        table.render({
            elem: '#manage',
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                limit: 15,
                groups: 5,
                first: true,
                last: true,
            },
            cols: [[
                {field:'id', width:80, title: 'ID', sort: true},
                {field:'username', width:100, title: '用户名'},
                {field:'email', width:100, title: '邮箱'},
                {field:'status', width:100, title: '用户状态'}
            ]]
            
        });
    });
    //监听单元格编辑
    table.on('edit(test3)', function(obj) {
        var value = obj.value,
            data = obj.data,
            field = obj.field;
        layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
    });
    
}