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
	var blackItemValue = $("#blackItemValue").val().trim();
	var blackId = $("#blackId").val();
	if(blackItemValue==null || blackItemValue==""){
		alertx("请填写元素值");
		return false;
	}else if( blackItemValue.length > 25 ) {
		alertx("元素值太长");
		return false;
	}else if( !checkSafe(blackItemValue) ){
		alertx("元素值包含非法字符");
		return false;
	}else{
		loading('正在提交，请稍等...');
		$.ajax({
			url:"${ctx}/risk/riskBlackorwhite/view/saveItem",
			type:"post",
			cache:false,
			timeout:10000,
			data:{
				"blackItemValue":blackItemValue,
				"blackId":blackId
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
	<li><a href="###">
				<c:forEach items="${enumMap.riskBwType}" var="wStatus">
					<c:if test="${rbwInfo.type eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>-
				<c:forEach items="${enumMap.riskBwCategory}" var="wStatus">
					<c:if test="${rbwInfo.category eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>-
				${rbwInfo.name}明细-添加
	</a></li>
</ul><br/>
<form id="inputForm"  action="${ctx}/risk/riskBlackorwhite/view/saveItem" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	<input type="hidden" id="blackId" name="blackId" value="${rbwInfo.blackId }"/>
	<div class="control-group">
		<label class="control-label">元素值：</label>
		<div class="controls">
			<input id="blackItemValue" name="blackItemValue" value=""  maxlength="32" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	
	<div class="form-actions">
		<shiro:hasPermission name="risk:riskBlackorwhite:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="check()" value="保存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="returnSearch()"/>
	</div>
</form>
<form:form id="searchForm" modelAttribute="riskBlackorwhiteItem" action="${ctx}/risk/riskBlackorwhite/view/itemlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="1"/>
		<input id="pageSize" name="pageSize" type="hidden" value="10"/>
		<input id="searchFlag" name="searchFlag" type="hidden" value="1"/>
		<input type="hidden" name="blackId"  value="${riskBlackorwhiteItem.blackId }"/>
		<input type="hidden" name="status"  value="${riskBlackorwhiteItem.status }"/>
		<input type="hidden" name="blackItemValue"  value="${riskBlackorwhiteItem.blackItemValue }"/>
		<input type="hidden" id="beginCreTime" name="beginCreTime" value="<fmt:formatDate value="${riskBlackorwhiteItem.beginCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="endCreTime" name="endCreTime" value="<fmt:formatDate value="${riskBlackorwhiteItem.endCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="beginUpdTime" name="beginUpdTime" value="<fmt:formatDate value="${riskBlackorwhiteItem.beginUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="endUpdTime" name="endUpdTime" value="<fmt:formatDate value="${riskBlackorwhiteItem.endUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
</form:form>
</body>
</html>