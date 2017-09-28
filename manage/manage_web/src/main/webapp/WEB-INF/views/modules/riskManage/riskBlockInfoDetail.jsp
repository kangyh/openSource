<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>详情</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
	//验证搜索条件
	var validateFrom = {
		validate: function(form){
			
			
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
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/risk/riskBlockInfo/">阻断风险操作详情列表</a></li>
	</ul>
	<ul class="ul-form">
		<input id="btnExport" class="btn btn-primary" type="button" onclick="window.history.go(-1);" value="返回"/></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公司名称</th>
				<th>营业执照编号</th>
				<th>法人姓名</th>
				<th>法人身份证号</th>
				<th>登陆类型</th>
				<th>Ip</th>
				<th>规则Id</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${registerAndLoginVo.companyName}</td>
				<td>${registerAndLoginVo.buziCode}</td>
				<td>${registerAndLoginVo.ownerName}</td>
				<td>${registerAndLoginVo.ownerId}</td>
				<td>${registerAndLoginVo.regLoginType}</td>
				<td>${registerAndLoginVo.ip}</td>
				<td>${registerAndLoginVo.ruleId}</td>
			</tr>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>