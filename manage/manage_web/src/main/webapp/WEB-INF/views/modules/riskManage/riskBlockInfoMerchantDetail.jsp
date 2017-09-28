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
				<th>商户Id</th>
				<th>账户类型</th>
				<th>银行卡类型</th>
				<th>产品类型</th>
				<th>商户公司</th>
				<th>银行卡号</th>
				<th>银行卡预留手机号</th>
				<th>银行卡户主身份证号</th>
				<th>交易类型</th>
				<th>请求Ip</th>
				<th>规则Id</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${merchantRiskVo.merchantId}</td>
				<td>${merchantRiskVo.accountType}</td>
				<td>${merchantRiskVo.bankCardType}</td>
				<td>${merchantRiskVo.productCode}</td>
				<td>${merchantRiskVo.merchantCompany}</td>
				<td>${merchantRiskVo.bankCardNo}</td>
				<td>${merchantRiskVo.bankCardOwnerMobile}</td>
				<td>${merchantRiskVo.bankCardOwnerIdCard}</td>
				<td>${merchantRiskVo.trans_type}</td>
				<td>${merchantRiskVo.tradeIp}</td>
				<td>${merchantRiskVo.ruleId}</td>
			</tr>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>