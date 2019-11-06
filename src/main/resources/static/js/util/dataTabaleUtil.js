var DataTableUtile;
var dataTableUtileHandle=function () {
    
}
dataTableUtileHandle.prototype={
    //初始化table列表
    initTable:function (id,url,columns,aoColumnDefs,param) {
        id.dataTable( {
            aaSorting: [[ 1, "desc" ]],//默认第几个排序
            bStateSave: true,//状态保存
            fixedHeader: true,　//这个是用来固定头部
            fixedColumns:{ //这个是用来固定列的
                leftColumns: 0,
                rightColumns: 1
            },
            aoColumnDefs: aoColumnDefs,
            ajax:{
                url: url,     // 异步请求地址，没啥好说的
                type: 'post', // 请求方式，因为是取数据，所以我选择了 get
                data: param     // 额外请求参数，一般是不需要的
            },
            //列表表头字段
            columns: columns,
        } );

        $('table th input:checkbox').on('click' , function(){
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                .each(function(){
                    this.checked = that.checked;
                    $(this).closest('tr').toggleClass('selected');
                });
        });
        $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});

        function    tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('table')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            var w2 = $source.width();
            if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
            return 'left';
        }
    }
}
DataTableUtile=new dataTableUtileHandle();