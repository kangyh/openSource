<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>风控管理</title>
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
	var name = $("#name").val().trim();
	var productCode = $("#productCode").val();
	var desc = $("#desc").val().trim();
	var status = $("#status").val();
	var type = $("#type").val();
	var category = $("#category").val();
	if(name==null || name==""){
		alertx("请填写名单名称");
		return false;
	}else if( name.length > 25 ) {
		alertx("名单名称太长");
		return false;
	}else if( !checkSafe(name) ){
		alertx("名单名称包含非法字符");
		return false;
	}else if ( desc != null && desc != "" && desc.length > 250   ){
		alertx("备注的长度太长");
		return false;
	}else if (  !confirm("确认要添加记录？")   ){
		return false;
	}else{
		loading('正在提交，请稍等...');
		$.ajax({
			url:"${ctx}/risk/riskBlackorwhite/view/save",
			type:"post",
			cache:false,
			timeout:10000,
			data:{
				"name":name,
				"productCode":productCode,
				"desc":desc,
				"status":status,
				"type":type,
				"category":category
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
				alertx("添加失败", closed);
				return ;
			}
		});
		
		//$("#inputForm").submit();
		//return ;
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
	<li><a href="#">黑白名单添加</a></li>
</ul><br/>
<form:form id="inputForm"  action="${ctx}/risk/riskBlackorwhite/view/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	
	<div class="control-group">
		<label class="control-label">名单名称：</label>
		<div class="controls">
			<input id="name" name="name" value=""  maxlength="20" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">名单类型：</label>
		<div class="controls">
		    <select id="type" name="type" class="input-xlarge required">
				<c:forEach items="${enumMap.riskBwType}" var="wStatus">
					<option value="${wStatus.value}" >${wStatus.name}</option>
				</c:forEach>
			</select> 
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">名单数据类型：</label>
		<div class="controls">
		    <select id="category" name="category" class="input-xlarge required">
				<c:forEach items="${enumMap.riskBwCategory}" var="wStatus">
						<option value="${wStatus.value}" >${wStatus.name}</option>
					</c:forEach>
			</select> 
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">产品名称：</label>
		<div class="controls">
			<select id="productCode" path="productCode" class="input-xlarge">
					<c:forEach items="${fns:getProductList()}" var="wStatus">
						<option value="${wStatus.code}">${wStatus.name}</option>
					</c:forEach>
			</select>
			
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">名单状态：</label>
		<div class="controls">
		    <select id="status" name="status" class="input-xlarge required">
				<c:forEach items="${enumMap.riskBwStatus}" var="wStatus">
						<option value="${wStatus.value}" >${wStatus.name}</option>
					</c:forEach>
			</select> 
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">名单描述：</label>
		<div class="controls">
			<input id="desc" name="desc" value="" htmlEscape="false" maxlength="10" class="required" />
			<span class="help-inline"></span>
		</div>
	</div>
	
	<div class="form-actions">
		<shiro:hasPermission name="risk:riskBlackorwhite:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保存并提交" onclick="check()"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="returnSearch()"/>
	</div>
</form:form>
<form:form id="searchForm" modelAttribute="riskBlackorwhite" action="${ctx}/risk/riskBlackorwhite/view" method="post" >
		<input id="pageNo" name="pageNo" type="hidden" value="1"/>
		<input id="pageSize" name="pageSize" type="hidden" value="10"/>
		<input id="searchFlag" name="searchFlag" type="hidden" value="1"/>
		<input type="hidden" id="blackId" name="blackId"  value="${riskBlackorwhite.blackId }"/>
		<input type="hidden" id="beginCreTime" name="beginCreTime" value="<fmt:formatDate value="${riskBlackorwhite.beginCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="endCreTime" name="endCreTime" value="<fmt:formatDate value="${riskBlackorwhite.endCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="beginUpdTime" name="beginUpdTime" value="<fmt:formatDate value="${riskBlackorwhite.beginUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="endUpdTime" name="endUpdTime" value="<fmt:formatDate value="${riskBlackorwhite.endUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="name" name="name"  value="${riskBlackorwhite.name }"/>
		<input type="hidden" id="type" name="type"  value="${riskBlackorwhite.type }"/>
		<input type="hidden" id="status" name="status"  value="${riskBlackorwhite.status }"/>
		<input type="hidden" id="productCode" name="productCode"  value="${riskBlackorwhite.productCode }"/>
		<input type="hidden" id="category"  name="category" value="${riskBlackorwhite.category }"/>
</form:form>
</body>
</html>