/**
 * Created with IDEA.
 * User: ZhangJunxin
 * Date: 2016-08-31
 * Time: 上午10:55
 *
 */
/**
 *
 * @param contentTableId   table id
 * @param parentCheckboxId  父checkbox id
 * @param sonCheckboxName   子checkbox names
 * @constructor
 */
function CheckboxUtil(contentTableId,parentCheckboxId,sonCheckboxName,checkBoxValues){
    this.contentTableId = contentTableId;
    this.parentCheckboxId = parentCheckboxId;
    this.sonCheckboxName = sonCheckboxName;
    this.init();
    this.initParentCheckBoxBindEvents();
    this.initSonCheckBoxBindEvents();

}


CheckboxUtil.prototype = {
    constructor: CheckboxUtil,

    init: function(){
    },

    /**
     * 当前上下文
     * @returns {CheckboxUtil}
     */
    currentContext:function(){
        return this;
    },

    /**
     * 初始化父Checkbox绑定事件
     */
    initParentCheckBoxBindEvents:function(){
        var myScope = this.currentContext();
        $("#"+myScope.parentCheckboxId).on("click",function(){
            if($(this).is(':checked')){
                myScope.selectOrNotAllSonCheckBoxes(true);
            }else{
                myScope.selectOrNotAllSonCheckBoxes(false);
            }
        });
    },
    /**
     * 初始化子Checkbox绑定事件
     */
    initSonCheckBoxBindEvents:function(){
        var myScope = this.currentContext();
        var sonCheckBoxes = $("#"+myScope.contentTableId).find("input[name="+myScope.sonCheckboxName+"]");
        $(sonCheckBoxes).on("click",function(){
            var ckLength = $("#"+myScope.contentTableId).find("input[name="+myScope.sonCheckboxName+"]:checked").length;
            if(ckLength == $(sonCheckBoxes).length){
                $("#"+myScope.parentCheckboxId).attr("checked","checked");
            }else{
                $("#"+myScope.parentCheckboxId).removeAttr("checked");
            }
        });
    },
    /**
     * 全选/不全选所有子CheckBox
     */
    selectOrNotAllSonCheckBoxes:function(checked){
        //var myScope = this.currentContext();
        var sonCheckBoxes = $("#"+this.contentTableId).find("input[name="+this.sonCheckboxName+"]");
        if(checked){
            $(sonCheckBoxes).attr("checked","checked");
        }else{
            $(sonCheckBoxes).removeAttr("checked");
        }
    },
    /**
     * 获取所有选中的子CheckBox的值
     */
    tackSelectedSonCheckBoxesValuese: function(){
        var sonCheckBoxes = $("#"+this.contentTableId).find("input[name="+this.sonCheckboxName+"]");
        if($(sonCheckBoxes).length > 0){
            var ckValues = "";
            $(sonCheckBoxes).each(function(){
                if($(this).is(':checked')) {
                    if(ckValues != ""){
                        ckValues = ckValues+ "," + $(this).val();
                    }else{
                        ckValues = $(this).val();
                    }
                }
            });
            return ckValues;
        }
        return "";
    }
}