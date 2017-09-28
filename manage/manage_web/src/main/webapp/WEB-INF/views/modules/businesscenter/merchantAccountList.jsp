<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户管理管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/demo.css" rel="stylesheet" type="text/css"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		//验证搜索条件
		var validateFrom = {
			validate : function(form) {
				var maxYear = 1; //默认查询一年
				var bgTime = $("#beginCreateTime").val();
				var endTime = $("#endCreateTime").val();
				if (bgTime == "" && endTime != "" || bgTime != ""
						&& endTime == "") {
					$("#messageBox").text("请正确设置查询时间范围!");
					return;
				}
				if (bgTime != "" && endTime != "") {
					if (compareDate(convertDateToJoinStr(bgTime),
							convertDateToJoinStr(endTime)) > 0) {
						$("#messageBox").text("起始日期不能大于结束日期!");
						return;
					} else if (compareYear(convertDateToJoinStr(bgTime),
							convertDateToJoinStr(endTime), maxYear) < 0) {
						$("#messageBox").text("查询的时间范围不能超过" + maxYear + "年!");
						return;
					}
				}
				var merchantId = $("#merchantId").val();
				var result = validateNum(merchantId, "商户编码请输入数字!");
				var accountId = $("#accountId").val();
				var accountIdResult = validateNum(accountId, "账号请输入数字!");
				var accountName = $("#accountName").val();
				var accountNameResult = validatePreventInject(accountName,
						"账号名称输入不合法!");
				if (!result || !accountIdResult || !accountNameResult) {
					return;
				}
				form.submit();
			}
		}

		function page(n, s) {
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		//搜索
		function onSubmit() {
			validateFrom.validate($("#searchForm"));
		}

		//清空
		function onClear() {
			cancleSelect();
			$("#searchForm").find("input[type='text']").val("");
			//默认账户类型
			$("#type").find("option").removeAttr("selected");
			$("#type").find("option:eq(0)").attr("selected", "selected");
			$(".select2-chosen:eq(0)").text($("#type option[selected]").text());
			//默认状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected", "selected");
			$(".select2-chosen:eq(1)").text(
					$("#status option[selected]").text());
			//默认升降排序
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected", "selected");
			$(".select2-chosen:eq(2)").text(
					$("#sortOrder option[selected]").text());
		}

		//导出
		function onExport() {

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
		
		function onSupplementManualAccount(){
			$("#supplementManualAccountFrom").submit();
		}
		function onNonSettle(){
			window.open("${ctx}/account/accountSettleQuery/getNonSettleList");
		}
	</script>
</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;background:#F5F5F5";>  
		<ul id="subjectTree" class="ztree" style="margin-top:0; width:220px; height: 300px;"></ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/business/businessMerchantAccount/getMerchantAccountList">账户管理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantAccount" action="${ctx}/business/businessMerchantAccount/getMerchantAccountList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账户名称：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账户类型：</label>
				<form:select path="type" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('MerchantAccountType')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantAccount.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantAccount.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>科目号：</label>
				<form:input path="accountCodes" id="accountCodes" readonly="true" htmlEscape="false" onclick="showMenu();"/>
				<form:hidden path="accountCodesHidden" id="accountCodesHidden"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-xlarge">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('MerchantStatus')}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
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
			<th>账户余额</th>
			<th>可用余额</th>
			<th>可提现金额</th>
			<th>冻结余额</th>
			<th>状态</th>
			<th>创建时间</th>
			<th>更新时间</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAccount">
			<tr>
				<td>
					${merchantAccount.merchantId}
				</td>
				<td>
					<a href="${ctx}/business/businessMerchantAccount/form?id=${merchantAccount.accountId}">
						${merchantAccount.accountId}
					</a>
				</td>
				<td>${merchantAccount.accountName}</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceAmount}" pattern="￥#,##0.00" />
				
				</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceAvailableAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceAvailableWithdrawAmount}" pattern="￥#,##0.00" />

				</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceFreezedAmount}" pattern="￥#,##0.00" />
				</td>
				<td>${fns:getDictLabel(merchantAccount.status, 'MerchantStatus', '')}</td>
				<td><fmt:formatDate value="${merchantAccount.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<fmt:formatDate value="${merchantAccount.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>

		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

</body>
</html>