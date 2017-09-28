<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部账户管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/demo.css" rel="stylesheet" type="text/css"/>
	<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var validateFrom = {
			validate: function(form){
				var accountId = $("#accountId").val();
				var result = validateNum(accountId,"账号请输入数字!");
				var accountName = $("#accountName").val();
				var accountNameResult = validatePreventInject(accountName,"账号名称输入不合法!");
				if(!result || !accountNameResult){
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
			$("#searchForm").find("input[type='text']").val("");
			//默认升降排序
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#sortOrder option[selected]").text());
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
				
				
				$("#addInnerAccount").on("click", function(){
					var url = $("#addInnerAccount").attr("value-url");
					$.ajax({
						type : "POST",
						url : "${ctx}/allocate/allocateRecord/isAdminUser",
						dataType : "json",
						async : false,
						success : function(data){
							if(data){
								alert("超级管理员不允许操作此功能");
								return false;
							}else{
								parent.showDynamicPa();
								parent.enterDynamicPa(url);
								return true;
							}
						}
					});		
					
				});
				
				
				$(".checkPass").on("click",function(){
					var url = $(this).attr("value-url");
					$.ajax({
						type : "POST",
						url : "${ctx}/allocate/allocateRecordReview/isAdminUser",
						dataType : "json",
						async : false,
						success : function(data){
							if(data){
								alert("超级管理员不允许操作此功能");
								return false;
							}else{
								parent.showDynamicPa();
								parent.enterDynamicPa(url);
								return true;
							}
						}
					});			
				});
				
				
			});
		
	</script>
</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;background:#F5F5F5";>  
		<ul id="subjectTree" class="ztree" style="margin-top:0; width:220px; height: 300px;"></ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/inner/innerAccount/">内部账户查询列表</a></li>
		<%--<shiro:hasPermission name="inner:innerAccount:edit"><li><a href="${ctx}/inner/innerAccount/form">账户管理添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantAccount" action="${ctx}/inner/innerAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账号：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>账户名称：</label>
				<form:input path="accountName" htmlEscape="false" maxlength="256" class="input-medium"/>
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
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/>
				<input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/>
				<a id="addInnerAccount" class="btn btn-warning" value-url="${ctx}/inner/innerAccount/form">新增内部账户</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>账号</th>
				<th>账户名称</th>
				<th>账户余额</th>
				<th>可用余额</th>
				<th>状态</th>
				<th>余额方向</th>
				<th>是否为热点账户</th>
				<th>创建时间</th>
				<shiro:hasPermission name="inner:innerAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAccount">
			<tr>
				<td>
					<a href="${ctx}/inner/innerAccount/detail?accountId=${merchantAccount.accountId}">
						${merchantAccount.accountId}
					</a>
				</td>
				<td>
					${merchantAccount.accountName}
				</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceAmount}" pattern="￥#,##0.00" />
				</td>
				<td>
					<fmt:formatNumber value="${merchantAccount.balanceAvailableAmount}" pattern="￥#,##0.00" />
				</td>
				<td>${fns:getDictLabel(merchantAccount.status, 'MerchantStatus', '')}</td>
				<td>
					<c:if test="${merchantAccount.balanceDirection == '0' }">
						借方
					</c:if>
					<c:if test="${merchantAccount.balanceDirection == '1' }">
						贷方
					</c:if>
				</td>
				<td>
					<c:if test="${merchantAccount.isHot == 'Y' }">
						是
					</c:if>
					<c:if test="${merchantAccount.isHot == 'N' }">
						否
					</c:if>
					<span style="display: none;">(version: ${merchantAccount.version})</span>
				</td>
				<td>
					<fmt:formatDate value="${merchantAccount.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="inner:innerAccount:edit"><td>
					<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/inner/innerAccount/toUpdatePage?accountId=${merchantAccount.accountId}">修改</a>&nbsp;&nbsp;
    				<c:if test="${merchantAccount.status == 'NORMAL' }">
						<a href="${ctx}/inner/innerAccount/updateMerchantAccountStatus?flag=CLOSED&accountId=${merchantAccount.accountId}" onclick="return confirmx('确认要禁用该内部账户吗？', this.href)">禁用账户</a>
    				</c:if>
    				<c:if test="${merchantAccount.status == 'CLOSED' }">
						<a href="${ctx}/inner/innerAccount/updateMerchantAccountStatus?flag=NORMAL&accountId=${merchantAccount.accountId}" onclick="return confirmx('确认要开启该内部账户吗？', this.href)">开启账户</a>
    				</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>