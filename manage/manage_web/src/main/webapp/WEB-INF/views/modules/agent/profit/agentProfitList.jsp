<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生成分润结算表管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
        //验证查询条件
        var validateFrom = {
            validate: function(form){
                var agentCode = $("#agentCode").val();
                if(isNaN(agentCode)){
                    $("#messageBox").text("代理商编号请输入数字类型");
                    return false;
                }

                var beginTime = $("#settleBeginDate").val();
                var endTime = $("#settleEndDate").val();
                if(beginTime=="" && endTime!="" || beginTime!="" && endTime=="" ){
                    $("#messageBox").text("请正确设置查询时间范围!");
                    return ;
                }
                if( beginTime!="" && endTime!=""){
                    if(compareDate(convertDateToJoinStr(beginTime),
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
        //查询
        function onSubmit(){
            $("#pageNo").val(1);
            validateFrom.validate($("#searchForm"));
        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li class="active"><a href="${ctx}/agent/profit/agentProfit/">生成分润结算表列表</a></li>--%>
		<%--<shiro:hasPermission name="agent:profit:agentProfit:edit"><li><a href="${ctx}/agent/profit/agentProfit/form">生成分润结算表添加</a></li></shiro:hasPermission>--%>
	<%--</ul>--%>
	<%--form begin--%>
	<form:form id="searchForm" modelAttribute="agentProfit" action="${ctx}/agent/profit/agentProfit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>代理商编号：</label>
				<form:input path="agentCode" id="agentCode" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>代理商名称：</label>
				<form:input path="agentName" id="agentName" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>结算日期：</label>
				<input  id="settleBeginDate" name="profitDateBegin" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					   value="${agentProfit.profitDateBegin}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input  id="settleEndDate" name="profitDateEnd" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
					   value="${agentProfit.profitDateEnd}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/>
			</li>
			<li class="clearfix"></li>
		</ul>
		<%--消息提示begin--%>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		<%--消息提示end--%>
	</form:form>
	<%--form end--%>

	<sys:message content="${message}"/>

	<%--分润列表页begin--%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>代理商编号</th>
				<th>代理商名称</th>
				<th>结算日期</th>
				<th>交易总笔数</th>
				<th>交易总金额</th>
				<th>分润总额</th>
				<th>已申请分润金额</th>
				<th>已领分润金额</th>
				<th>可申请分润金额</th>
				<th>备注</th>
				<%--<shiro:hasPermission name="agent:profit:agentProfit:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentProfit">
			<tr>
				<td>
					${agentProfit.agentCode}
				</td>
				<td>
					${agentProfit.agentName}
				</td>
				<td>
				    ${agentProfit.settleBeginDate}
				</td>
				<td>
					${agentProfit.transTotalCount}
				</td>
				<td>
					${agentProfit.transTotalAmount}
				</td>
				<td>
					${agentProfit.profitTotalAmount}
				</td>
				<td>
					${agentProfit.applyAmount}
				</td>
				<td>
					${agentProfit.recvAmount}
				</td>
				<td>
					${agentProfit.avaiApplyAmount}
				</td>
				<td>
					${agentProfit.remark}
				</td>
				<%--<shiro:hasPermission name="agent:profit:agentProfit:edit"><td>
    				<a href="${ctx}/agent/profit/agentProfit/form?id=${agentProfit.id}">修改</a>
					<a href="${ctx}/agent/profit/agentProfit/delete?id=${agentProfit.id}" onclick="return confirmx('确认要删除该生成分润结算表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%--分润列表页end--%>

	<%--页码begin--%>
	<div class="pagination">${page}</div>
    <%--页码end--%>
</body>
</html>