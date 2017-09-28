<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商户证书密钥管理</title>
<meta name="decorator" content="default"/>
<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
<script type="text/javascript">
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			var merchantNo = $("#merchantNo").val();
			var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
			if(pattern.test(merchantNo)){
				$("#messageBox").text("名称输入不合法，请重新输入");
				return false;
			}
			
			
			var bgTime = $("#beginOperEndTime").val();
			var endTime = $("#endOperEndTime").val();
			if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
				$("#messageBox").text("请正确设置查询时间范围!");
				return ;
			}
			if( bgTime!="" && endTime!=""){
				if(compareDate(convertDateToJoinStr(bgTime),
								convertDateToJoinStr(endTime)) > 0){
					$("#messageBox").text("起始日期不能大于结束日期!");
					return ;
				}
			} 
			$("#searchForm").submit();
		}
	}
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
       	return false;
       }
	//搜索
	function onSubmit(){
		$("#pageNo").val(1);
		validateFrom.validate($("#searchForm"));
	}
	//清空
	function onClear(){
		$("#formBtn").submit();
	}
	
	function Sel2change(field){
		$("#channelName").val(field);
	}
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpds/tpdsMerchantCer">商户证书密钥管理列表</a></li>
		<shiro:hasPermission name="tpds:tpdsMerchantCer:edit">
		<li><a href="${ctx}/tpds/tpdsMerchantCer/addAndUpdate">添加商户证书密钥</a></li></shiro:hasPermission>
	</ul>
	<form action="${ctx}/tpds/tpdsMerchantCer" method="post" id="formBtn"></form> 
	
	<form:form id="searchForm" modelAttribute="tpdsMerchantCer" action="${ctx}/tpds/tpdsMerchantCer" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>商户编号：</label>
				<form:input path="merchantNo" id="merchantNo"  htmlEscape="false" maxlength="3"  class="input-medium required" placeholder="请输入商户编码"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsMerchantCer.beginOperEndTime}" pattern="yyyy-MM-dd hh:MM:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd hh:MM:ss',isShowClear:false});"/> - 
				<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${tpdsMerchantCer.endOperEndTime}" pattern="yyyy-MM-dd hh:MM:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd hh:MM:ss',isShowClear:false});"/>
			</li>
	     <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
	     <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
		</ul>  
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编号</th>
			<th>公钥</th>
			<th>私钥</th>
			<th>证书文件存储路径</th>
			<th>注册时间</th>
			<th>修改时间</th>
			<th>更新人</th>
			<th>备注</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpdsMerchantCer" varStatus="i">
				<tr>
					<td>${tpdsMerchantCer.merchantNo}</td>
					<td>${tpdsMerchantCer.pubKey}</td>
					<td>${tpdsMerchantCer.priKey}</td>
				    <td>
				    	<a href="${tpdsMerchantCer.cerPath}">点击查看</a>
				    </td>
					<td><fmt:formatDate value="${tpdsMerchantCer.createTime}" type="both" pattern="yyyy-MM-dd hh:MM:ss"/></td>
					<td><fmt:formatDate value="${tpdsMerchantCer.updateTime}" type="both" pattern="yyyy-MM-dd hh:MM:ss"/></td>
					<td>${tpdsMerchantCer.updateUser}</td>
					<td>${tpdsMerchantCer.note}</td>
					<td>
					  <a href="${ctx}/tpds/tpdsMerchantCer/delete/${tpdsMerchantCer.tpdsMerchantCerId}">删除</a>
					| <a href="${ctx}/tpds/tpdsMerchantCer/addAndUpdate?tpdsMerchantCerId=${tpdsMerchantCer.tpdsMerchantCerId}">修改</a>
				   </td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>