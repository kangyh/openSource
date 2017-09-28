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
		<li class="active"><a href="${ctx}/risk/riskBlockInfo/">交易风控管理详情列表</a></li>
	</ul>
	<ul class="ul-form">
		<input id="btnExport" class="btn btn-primary" type="button" onclick="window.history.go(-1);" value="返回"/></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>银行卡类型</th>
				<th>银行卡号</th>
				<th>银行卡所有人姓名</th>
				<th>银行卡所有人身份证</th>
				<th>银行卡所有者手机号</th>
				<th>通道交易类型</th>
				<th>手续费类型</th>
				<th>银行卡所有者类型</th>
				<th>手续费</th>
				<th>请求Ip</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${riskTransOrderVo.bankCardType}</td>
				<td>${riskTransOrderVo.bankCardNo}</td>
				<td>${riskTransOrderVo.bankCardOwnerName}</td>
				<td>${riskTransOrderVo.bankCardOwnerIdCard}</td>
				<td>${riskTransOrderVo.bankCardOwnerMobile}</td>
				<td>${riskTransOrderVo.channelTransType}</td>
				<td>${riskTransOrderVo.feeType}</td>
				<td>${riskTransOrderVo.bankCardOwnerType}</td>
				<td>${riskTransOrderVo.feeAmount}</td>
				<td>${riskTransOrderVo.reqIp}</td>
			</tr>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>