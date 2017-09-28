<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上下级关系管理</title>
	<meta name="decorator" content="default"/>
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
		<li class="active"><a href="${ctx}/prom/promTeacherManage/">上下级关系列表</a></li>
		<shiro:hasPermission name="prom:promTeacherManage:edit"><li><a href="${ctx}/prom/promTeacherManage/form">上下级关系添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="promTeacherManage" action="${ctx}/prom/promTeacherManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
            <li><label>商户编码：</label>
                <form:input path="merchantCode" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
            </li>
            <li><label>上级商户编码：</label>
                <form:input path="superMerchantCode" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
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
                <th>商户编码</th>
                <th>上级（商户编码）</th>
                <th>商户名称</th>
                <th>上级名称（商户名称）</th>
                <th>分润比例模板号</th>
                <th>生效日期</th>
                <th>失效日期</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>修改人</th>
                <th>修改时间</th>
				<shiro:hasPermission name="prom:promTeacherManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="promTeacherManage">
			<tr>
                <td>${promTeacherManage.merchantCode}</td>
                <td>${promTeacherManage.superMerchantCode}</td>
                <td>${promTeacherManage.merchantName}</td>
                <td>${promTeacherManage.superMerchantName}</td>
                <td>${promTeacherManage.templetId}</td>
                <td><fmt:formatDate value="${promTeacherManage.effectTime}" type="both" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${promTeacherManage.failureTime}" type="both" pattern="yyyy-MM-dd"/></td>
                <td>${promTeacherManage.createAuthor}</td>
                <td><fmt:formatDate value="${promTeacherManage.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${promTeacherManage.updateAuthor}</td>
                <td><fmt:formatDate value="${promTeacherManage.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <shiro:hasPermission name="prom:promTeacherManage:edit"><td>
    				<a href="${ctx}/prom/promTeacherManage/form?teId=${promTeacherManage.teId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>