/**
 *  异步导出Excel
 * @param values
 * @param url
 * @constructor
 */
function AjaxExportExcel(frameId,exportButtonId,exportVals,exportUrl){
    this.frameId = frameId;
    this.exportButtonId = exportButtonId;
    this.exportVals = exportVals;
    this.exportUrl = exportUrl;
    this.init();
    this.initExportButtonBindEvents();
}

AjaxExportExcel.prototype = {
    constructor: AjaxExportExcel,

    /**
     * 页面动态添加iFrame
     */
    init: function(){
        $('body').append("<iframe id='"+this.currentContext().frameId+"' style='display:none'></iframe>");
    },

    /**
     * 导出按钮绑定事件
     */
    initExportButtonBindEvents:function(){
        var myScope = this.currentContext();
        $("#"+this.currentContext().exportButtonId).on("click",function(){
            myScope.excuetExport();
        });
    },
    /**
     * 当前上下文
     * @returns {CheckboxUtil}
     */
    currentContext:function(){
        return this;
    },

    /**
     * 导出excel
     */
    excuetExport: function(){
        var url = this.currentContext().exportUrl + "?exportIds="+this.currentContext().exportVals;
        alert(url);
        $('#'+this.currentContext().frameId).attr('src', url);
    }

}