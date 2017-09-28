<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易账务查询管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/demo.css" rel="stylesheet" type="text/css"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var maxYear = 1;	//默认查询一年
				var bgTime = $("#beginCreateTime").val();
				var endTime = $("#endCreateTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}else if(compareYear(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime),maxYear) < 0){
						$("#messageBox").text("查询的时间范围不能超过" + maxYear + "年!");
						return ;
					}
				}
				var accountId = $("#accountId").val();
				var result = validateNum(accountId,"账号请输入数字!");
				var accountName = $("#accountName").val();
				var accountNameResult = validatePreventInject(accountName,"账号名称输入不合法!");
				var transNo = $("#transNo").val();
				var transNoResult = validateNum(transNo,"交易订单号请输入数字!");
				var logId = $("#logId").val();
				var logIdResult = validateNum(logId,"账务流水号请输入数字!");
				var paymentId = $("#paymentId").val();
				var paymentIdResult = validateNum(paymentId,"支付单号请输入数字!");
				if(!result || !accountNameResult || !transNoResult || !logIdResult || !paymentIdResult){
					return;
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
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			cancleSelect();
			$("#searchForm").find("input[type='text'][id!='beginCreateTime'][id!='endCreateTime']").val("");
			//默认排序方式
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#sortOrder option[selected]").text());
			$("#merchantType").find("option").removeAttr("selected");
			$("#merchantType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#merchantType option[selected]").text());
		}
		
		
		var setting = {
				check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
			
			function beforeClick(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("subjectTree");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}
			
			function onCheck(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("subjectTree"),
				nodes = zTree.getCheckedNodes(true),
				v = "";
				vh = "";
				for (var i=0, l=nodes.length; i<l; i++) {
					v += nodes[i].name + ",";
					vh += nodes[i].id + ",";
				}
				if (v.length > 0 ) v = v.substring(0, v.length-1);
				if(vh.length > 0) vh = vh.substring(0, vh.length-1)
				var subjectObj = $("#accountCodes");
				subjectObj.attr("value", v);
				$("#accountCodesHidden").val(vh);
			}

			function showMenu() {
				cancleSelect();
				var subjectObj = $("#accountCodes");
				var subjectOffset = $("#accountCodes").offset();
				$("#menuContent").css({left:subjectOffset.left + "px", top:subjectOffset.top + subjectObj.outerHeight() + "px"}).slideDown("fast");

				$("body").bind("mousedown", onBodyDown);
			}
			function hideMenu() {
				$("#menuContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
			function onBodyDown(event) {
				if (!(event.target.id == "menuBtn" || event.target.id == "accountCodes" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
					hideMenu();
				}
			}
			function cancleSelect(){
				$("#accountCodes").val("")
				$("#accountCodesHidden").val("");
				var treeObj = $.fn.zTree.getZTreeObj('subjectTree');
				treeObj.checkAllNodes(false);
			}

			$(document).ready(function(){
				$.ajax({
					type : "post",
					url : "${ctx}/account/accountQuery/getSubjectCodes",
					dataType : "json",
					async : false,
					success : function(data) {
						$.fn.zTree.init($("#subjectTree"), setting, data);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(errorThrown);
					}
				});
			});
		
	</script>
	<style>
	.table td{
		font-size:14px;
		font-family:"微软";
	}
	</style>
</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;background:#F5F5F5";>  
		<ul id="subjectTree" class="ztree" style="margin-top:0; width:220px; height: 300px;"></ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/merchantLog/">交易账务查询列表</a></li>
		<shiro:hasPermission name="payment:merchantLog:edit"><li><a href="${ctx}/payment/merchantLog/form">交易账务查询添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantLog" action="${ctx}/payment/merchantLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>账户名称：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label style="width:170px;">结算单号/差错批次号：</label>
				<form:input path="settleId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${merchantLog.beginCreateTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${merchantLog.endCreateTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>账务流水号：</label>
				<form:input path="logId" htmlEscape="false" maxlength="19" class="input-medium"/>
			</li>
			<li><label>支付单号：</label>
				<form:input path="paymentId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>科目号：</label>
				<form:input path="accountCodes" id="accountCodes" readonly="true" htmlEscape="false" onclick="showMenu();"/>
				<form:hidden path="accountCodesHidden" id="accountCodesHidden"/>
			</li>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>账户类型：</label>
				<form:select path="merchantType" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('MerchantAccountType')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>账号</th>
				<th>账户名称</th>
				<th>交易类型</th>
				<th>账务流水号</th>
				<th>交易订单号</th>
				<th>结算单号/差错批次号</th>
				<th>支付单号</th>
				<th>借</th>
				<th>贷</th>
				<th>余额</th>
				<th>备注</th>
				<th>记账标识</th>
				<th>创建时间</th>
				<shiro:hasPermission name="payment:merchantLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantLog">
			<tr>
				<td>
					<c:choose>
						<c:when test="${fn:startsWith(merchantLog.accountId,'6803')}">-</c:when>
						<c:otherwise>
							${merchantLog.merchantId}
						</c:otherwise>
					</c:choose>
				</td>
				<td>${merchantLog.accountId}</td>
				<td>${merchantLog.accountName}</td>
				<td>
					<c:set var="TRANS_TYPE" value="${fns:getDictLabel(merchantLog.type, 'TransType', '')}"></c:set>
					<c:choose>
						<c:when test="${not empty TRANS_TYPE}">
							${TRANS_TYPE}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					${merchantLog.logId}
				</td>
				<td>
					<c:choose>
						<c:when test="${empty merchantLog.transNo}">-</c:when>
						<c:otherwise>
							${merchantLog.transNo}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty merchantLog.settleId}">-</c:when>
						<c:otherwise>
							${merchantLog.settleId}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty merchantLog.paymentId}">-</c:when>
						<c:otherwise>
							${merchantLog.paymentId}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:if test="${merchantLog.balanceDirection eq 'D'}">
						<fmt:formatNumber value="${merchantLog.balanceAmountChanges}" pattern="￥#,##0.00" />
					</c:if>
				</td>
				<td>
					<c:if test="${merchantLog.balanceDirection eq 'C'}">
						<fmt:formatNumber value="${merchantLog.balanceAmountChanges}" pattern="￥#,##0.00" />
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${fn:startsWith(merchantLog.accountId, '6803110') || merchantLog.merchantType ne '3'}">
							<fmt:formatNumber value="${merchantLog.balanceAmount}" pattern="￥#,##0.00" />
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>

				<shiro:hasPermission name="payment:merchantLog:edit"><td>
    				<a href="${ctx}/payment/merchantLog/form?id=${merchantLog.id}">修改</a>
					<a href="${ctx}/payment/merchantLog/delete?id=${merchantLog.id}" onclick="return confirmx('确认要删除该账户明细查询吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
				<td>
					<c:set var="ACCOUNT_EXPLAIN" value="${fns:getDictLabel(merchantLog.accountExplain, 'AccountExplain', '')}"></c:set>
					<c:choose>
						<c:when test="${not empty ACCOUNT_EXPLAIN}">
							${ACCOUNT_EXPLAIN}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty merchantLog.accountMark}">
							${fns:getDictLabel(merchantLog.accountMark, 'AccountMark', '')}
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${merchantLog.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>