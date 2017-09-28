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
		
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				
				var merchantLoginNameId = $("#merchantLoginNameId").val();
				
				var pattern = new RegExp("[`~!#$^&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(merchantLoginNameId)){
					$("#messageBox").text("商户账号输入不合法，请重新输入");
					return false;
				}
				
				var merchantCodeId = $("#merchantCodeId").val();
				if(isNaN(merchantCodeId)){
					$("#messageBox").text("商户编码请输入数字类型");
					return false;
				}
				
				var freezeNoId = $("#freezeNoId").val();
				if(isNaN(freezeNoId)){
					$("#messageBox").text("冻结单号请输入数字类型");
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
				form.submit();
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
		
		//文件导出
		function onExport(){
			var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"/export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}
		function Sel2change(field){
			$("#channelName").val(field);
		}
		
	</script> 
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/riskManage/RiskMerchantFreezeQuery/view">冻结解冻查询列表</a></li>
		<%-- <shiro:hasPermission name="settleDic:settleDicItem:edit"><li><a href="${ctx}/riskManage/RiskMerchantFreezeQuery/add">商户冻结规则添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form action="${ctx}/riskManage/RiskMerchantFreezeQuery/view" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="riskMerchantFreeze" action="${ctx}/riskManage/RiskMerchantFreezeQuery/view" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<li><label>操作状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
					<form:option value="" label="-选择冻结状态-"/>
					<c:forEach items="${riskStatus}" var="riskStatusControl">
							<form:option value="${riskStatusControl.value}" label="${riskStatusControl.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			
			
			<li><label>商户账号：</label>
					<form:input path="merchantName" id="merchantLoginNameId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入商户账号" style="width:256px;"/>
			</li> 
			
			<li><label>冻结单号：</label>
					<form:input path="freezeNo" id="freezeNoId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入14位数字" style="width:256px;"/>
			</li> 
			</ul>
			<ul class="ul-form">
			<li><label>冻结申请备注1：</label>
					<form:input path="remark1" id="remark1" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入查询内容" style="width:256px;"/>
			</li> 
			<li><label>解冻单号：</label>
					<form:input path="defreezeNo" id="freezeNoId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入14位数字" style="width:256px;"/>
			</li> 
			<li><label>商户编码：</label>
					<form:input path="merchantCode" id="merchantCodeId" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入6位数字" style="width:256px;"/>
			</li> 
			</ul>
			<ul class="ul-form">
			<li><label>冻结申请时间：</label>
						<input id="beginOperEndTime" name="beginOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
						value="<fmt:formatDate value="${riskMerchantFreeze.beginOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
					<input id="endOperEndTime" name="endOperEndTime" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
						value="<fmt:formatDate value="${riskMerchantFreeze.endOperEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>商户编码</th>
			<th>商户账号</th>
			<th>冻结单号</th>
			<th>解冻单号</th>
			<th>申请时间</th>
			<th>更新时间</th>
			
			<th>冻结金额</th>
			
			<th>申请人</th>
			<th>更新人</th>
			<th>操作状态</th>
			<th>冻结理由</th>
			<th>冻结申请备注1</th>
			<th>冻结申请备注2</th>
			<th>冻结申请备注3</th>
			<th>冻结申请备注4</th>
			<th>冻结审核备注</th>
			<th>解冻申请备注</th>
			<th>解冻审核备注</th>
			
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="riskMerchantFreeze">
		
			<tr>
				<td>${riskMerchantFreeze.merchantCode}</td>
				<td>${riskMerchantFreeze.merchantName}</td>
				<td>${riskMerchantFreeze.freezeNo}</td>
				<td>${riskMerchantFreeze.defreezeNo}</td>
				<td>
					<fmt:formatDate value="${riskMerchantFreeze.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${riskMerchantFreeze.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<td>
					<fmt:formatNumber value="${riskMerchantFreeze.transAmount}" pattern="￥0.0000" />
				</td>
				
				<td>${riskMerchantFreeze.createAuthor}</td>
				<td>${riskMerchantFreeze.updateAuthor}</td>
				<td>${riskMerchantFreeze.status}</td>
				<td>${riskMerchantFreeze.freezeRemark}</td>
				<td>${riskMerchantFreeze.remark1}</td>
				<td>${riskMerchantFreeze.remark2}</td>
				<td>${riskMerchantFreeze.remark3}</td>
				<td>${riskMerchantFreeze.remark4}</td>
				<td>${riskMerchantFreeze.freezeMessage}</td>
				<td>${riskMerchantFreeze.thawLog}</td>
				<td>${riskMerchantFreeze.thawMessages}</td>
			</tr>	
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>