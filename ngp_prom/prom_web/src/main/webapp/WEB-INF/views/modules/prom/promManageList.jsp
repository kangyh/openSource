<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推广位管理</title>
	<meta name="decorator" content="default"/>
    <script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
	<script type="text/javascript">
        //用于验证
        var flg = true;

		$(document).ready(function() {
			
		});

        //验证搜索条件
        var validateFrom = {
            validate: function(form){
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
            if(flg){
                $("#pageNo").val(1);
                validateFrom.validate($("#searchForm"));
            }
        }

        //清空
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");

            $("#messageBox").text("");
            flg = true;
        }

        //验证
        function checkValue(obj){
            if(obj != null && obj != '' && obj != undefined){
                var pattern = new RegExp("[`~!#%$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？.]")
                if(pattern.test(obj)){
                    $("#messageBox").text("输入字符不合法，请重新输入");
                    flg = false;
                    return;
                }else{
                    if(validatePreventInject(obj)){
                        $("#messageBox").text("");
                    }else{
                        $("#messageBox").text("输入字符不合法，请重新输入!");
                        flg = false;
                        return;
                    }
                }
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/prom/promManage/">推广位列表</a></li>
		<shiro:hasPermission name="prom:promManage:edit"><li><a href="${ctx}/prom/promManage/form">推广位添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="promManage" action="${ctx}/prom/promManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>推广位ID：</label>
                <form:input path="promotionId" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
            </li>
            <li><label>产品名称：</label>
                <form:input path="productName" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
            </li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
            <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>推广位ID</th>
                <th>推广位名称</th>
                <th>产品名称</th>
                <th>推广方式</th>
                <th>生成时间</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>修改人</th>
                <th>修改时间</th>
                <th>状态</th>
                <th>推广连接</th>
				<shiro:hasPermission name="prom:promManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="promManage">
			<tr>
                <td>${promManage.promotionId}</td>
                <td>${promManage.proName}</td>
                <td>${promManage.productName}</td>
                <td>${promManage.proType}</td>
                <td><fmt:formatDate value="${promManage.createDate}" type="both" pattern="yyyy-MM-dd"/></td>
                <td>${promManage.createAuthor}</td>
                <td><fmt:formatDate value="${promManage.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${promManage.updateAuthor}</td>
                <td><fmt:formatDate value="${promManage.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${promManage.status}</td>
                <td>${promManage.proUrl}</td>
                <shiro:hasPermission name="prom:promManage:edit"><td>
    				<a href="${ctx}/prom/promManage/form?proId=${promManage.proId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>