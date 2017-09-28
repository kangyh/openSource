<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>销售单生成</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<style>
       #main {
           margin: 50px;
       }
   </style>
<script type="text/javascript">
String.prototype.trim = function() {
	  return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
	}
function check() {
	var reg=/^[A-Za-z0-9]+$/;
	var orderBatch = $("#orderBatch").val().trim();
	var remark = $("#remark").val().trim();

	if(orderBatch==null || orderBatch==""){
		alertx("请填写订单批次号");
		return false;
	}else if( !checkSafe(orderBatch) ){
		alertx("订单批次号包含非法字符");
		return false;
	}else if ( remark != null && remark != "" && remark.length > 50   ){
		alertx("备注的长度太长");
		return false;
	}else if (  !confirm("确认要生成销售单？")   ){
		return false;
	}else{
		loading('正在提交，请稍等...');
		$.ajax({
			url:"${ctx}/prom/promSaleOrder/view/saveGenSaleOrder",
			type:"post",
			cache:false,
			timeout:10000,
			data:{
				"orderBatch":orderBatch,
				"remark":remark 
			},
			success:function(msg){
				closeLoading();
				alertx(msg);
				if( msg.indexOf("成功")>-1 ){
					$("#btnSubmit").remove();
				}
				return ;
			},
			error:function(){
				closeLoading();
				alertx("生成销售单失败", closed);
				return ;
			}
		});
	}
}
	function checkSafe(a){
		if ( a.indexOf("script")>-1 || a.indexOf("\\")>-1 ){
			return false;
		}
		fibdn = new Array ("'",">","<","*","%","#","$","}","{","~","`","!","￥","/","?","&","^","(",")","=",";");
		i=fibdn.length;
		j=a.length;
		for (ii=0; ii<i; ii++) {
			for (jj=0; jj<j; jj++) {
				temp1=a.charAt(jj);
				temp2=fibdn[ii];
				if (temp1==temp2){
				return false;
				}
			}
		}
		
		return true;
	}
	function returnSearch(){
		$("#searchForm").submit();
	}
</script>
</head>
<body>
<ul class="nav nav-tabs">
     <li ><a href="${ctx}/prom/promSaleOrder/view">销售单查询</a></li>
	<li class="active"><a href="#">销售单生成</a></li>
</ul><br/>
<form:form id="inputForm"  action="${ctx}/prom/promSaleOrder/view/saveGenSaleOrder" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	
	<div class="control-group">
		<label class="control-label">订单批次号：</label>
		<div class="controls">
			<input id="orderBatch" name="orderBatch" value=""  maxlength="32" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">备注：</label>
		<div class="controls">
			<input id="remark" name="remark" value="" htmlEscape="false" maxlength="50"  />
			<span class="help-inline"></span>
		</div>
	</div>
	
	<div class="form-actions">
		<shiro:hasPermission name="prom:promSaleOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保存并提交" onclick="check()"/>&nbsp;</shiro:hasPermission>
	</div>
</form:form>

</body>
</html>