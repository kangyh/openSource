<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function closeModal(modalID,textId){
            $('#'+modalID).modal('hide');
            $("#"+textId).val('');
        }
        function showModal(id,type){
            $("#titleName").html("分润审核驳回");
            $("#modalID").bind("click",function(){
                var text = $("#returnText").val();
                if (text == '') {
                    top.$.jBox.tip("请输入备注说明。", 'error');
                    return false;
                }else if(text.length>50){
                    top.$.jBox.tip("备注说明最多可以输入50个字符。", 'error');
                    return false;
                }
                location = '${ctx}/profit/profit/agentProfitLog/verify?id='+id+'&status='+type+'&remark=' + encodeURIComponent(text);
            });
        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li class="active"><a href="${ctx}/profit/profit/agentProfitLog/verifyList">分润审核列表</a></li>--%>
	<%--</ul>--%>
	<form:form id="searchForm" modelAttribute="agentProfitLog" action="${ctx}/profit/profit/agentProfitLog/verifyList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>结算日期：</label>
				<input  id="settleBeginDate" name="settleBeginDate" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
						value="${agentProfitLog.settleBeginDate}"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input  id="settleEndDate" name="settleEndDate" type="text" readonly="readonly" maxlength="20" style="width:150px;" class="input-medium Wdate"
						value="${agentProfitLog.settleEndDate}"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>分润序号</label>
				<form:input path="agentProfitId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input name="applyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${agentProfitLog.applyTime}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>申请金额：</label>
				<form:input path="profitBeginAmount" htmlEscape="false" maxlength="20" class="input-medium"/> -
				<form:input path="profitEndAmount" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>申请代理商编号</label>
				<form:input path="agentCode" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>申请代理商名称</label>
				<form:input path="agentName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>快递单号：</label>
				<form:input path="express" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${agentProfitStatusList}" itemLabel="value" itemValue="name" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan='2'>分润序号</th>
				<th rowspan='2'>结算日期</th>
				<th rowspan='2'>申请时间</th>
				<th rowspan='2'>申请金额</th>
				<th rowspan='2'>申请代理商编号</th>
				<th rowspan='2'>申请代理商名称</th>
				<th rowspan='2'>发票信息</th>
				<th rowspan='2'>快递信息</th>
				<th rowspan='2'>申请备注</th>
				<th rowspan='2'>状态</th>
				<shiro:hasPermission name="profit:profit:agentProfitLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="agentProfitLog">
			<tr>
				<td>
						${agentProfitLog.agentProfitId}
				</td>
				<td>
						${agentProfitLog.settleBeginDate}
				</td>
				<td>
						${agentProfitLog.applyTime}
				</td>
				<td>
						${agentProfitLog.profitAmount}
				</td>
				<td>
						${agentProfitLog.agentCode}
				</td>
				<td>
						${agentProfitLog.agentName}
				</td>
				<td>
					<a href="${agentProfitLog.invoices}">查看</a>
				</td>
				<td>
						${agentProfitLog.express}
				</td>
				<td>
						${agentProfitLog.applyRemark}
				</td>
				<td>
						${agentProfitLog.status}
				</td>
				<shiro:hasPermission name="profit:profit:agentProfitLog:edit"><td>
					<c:if test="${agentProfitLog.status == '待初审'}">
					<a href="${ctx}/profit/profit/agentProfitLog/verify?id=${agentProfitLog.id}&status=RECHEK" onclick="return confirmx('确认要通过该分润申请吗？', this.href)">初审通过</a>
					</c:if>
					<c:if test="${agentProfitLog.status == '待复审'&&currentUserId!=agentProfitLog.inichkUserId}">
						<a href="${ctx}/profit/profit/agentProfitLog/verify?id=${agentProfitLog.id}&status=FINISH" onclick="return confirmx('确认要通过该分润申请吗？', this.href)">复审通过</a>
					</c:if>
					<%--<a href="${ctx}/profit/profit/agentProfitLog/verify?id=${agentProfitLog.id}&status=REJECT" onclick="return confirmx('确认要驳回该分润申请吗？', this.href)">驳回</a>--%>
					<c:if test="${agentProfitLog.status == '待初审'}">
					<a href="javascript:void(0)"  data-toggle="modal"  data-target="#returnModal"  data-backdrop="static" data-keyboard="false" onclick="showModal('${agentProfitLog.id}','REJECT')">驳回</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<!-- 模态框（Modal） -->
		<div class="modal fade" id="returnModal" tabindex="-1" role="dialog"
			 style="width:400px;" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
								data-dismiss="modal" aria-hidden="true" onclick="closeModal('returnModal','returnText')">
							&times;
						</button>
						<h4 class="modal-title"  id="titleName">

						</h4>
					</div>
					<div class="modal-body"  >
						备注说明<textarea class="comments" id ="returnText" rows="2"></textarea>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
								data-dismiss="modal" onclick="closeModal('returnModal','returnText')">关闭
						</button>
						<button type="button" id='modalID' class="btn btn-primary">
							确认
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
</body>
</html>