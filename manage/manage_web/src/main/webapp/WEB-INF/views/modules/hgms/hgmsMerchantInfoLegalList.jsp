<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资金归集开户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            $(".checkPass").on("click",function(){
                var url = $(this).attr("value-url");
                parent.showDynamicPa();
                parent.enterDynamicPa(url);
            });
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        //搜索框 清空
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");

            //默认状态
            $("#legalAuditStatus").find("option").removeAttr("selected");
            $("#legalAuditStatus").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#legalAuditStatus option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/hgmsMerchantInfoLegal">资金归集法务审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hgmsMerchantInfo" action="${ctx}/hgms/hgmsMerchantInfoLegal" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="10" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}
					else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</li>
			<li><label style="width:100px">商户公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="50" class="input-medium"
							onkeyup="value=value.replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09]/g,'')"
							onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5|\uFF08|\uFF09]/g,''))"
							onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
							onBlur="if(!value){value=defaultValue;this.style.color='#999'}"
							style="color:#999999"/>
			</li>
            <li><label>审核状态：</label>
                <form:select  path="legalAuditStatus" class="input-xlarge">
                    <form:option value="" label="全部"/>
                    <form:options id="legalAuditStatus" items="${fns:getEnumList('routeStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hgmsMerchantInfo.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endCreateTime\')}'});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${hgmsMerchantInfo.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginCreateTime\')}'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>总部公司名称</th>
				<th>法人代表姓名</th>
				<th>单位类型</th>
				<th>创建时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hgmsMerchantInfo">
			<tr>
				<td>${hgmsMerchantInfo.merchantId}</td>
				<td>${hgmsMerchantInfo.companyName}</td>
				<td>${hgmsMerchantInfo.superiorName}</td>
				<td>${hgmsMerchantInfo.legalRepresentative}</td>
				<td>${hgmsMerchantInfo.type}</td>
                <td><fmt:formatDate value="${hgmsMerchantInfo.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${hgmsMerchantInfo.legalAuditStatus}</td>
                <td>

				<shiro:hasPermission name="hgms:hgmsMerchantInfoLegal:edit">
                    <c:if test="${hgmsMerchantInfo.legalAuditStatus == '待审核'}" >
                        <a href="${ctx}/hgms/hgmsMerchantInfoLegal/audit?id=${hgmsMerchantInfo.merchantId}&goal=3">审核</a>
                    </c:if>
                    <c:if test="${hgmsMerchantInfo.legalAuditStatus == '审核不通过'}" >
                        <a href="${ctx}/hgms/hgmsMerchantInfo/edit?id=${hgmsMerchantInfo.merchantId}&goal=4">修改</a>
                    </c:if>
				</shiro:hasPermission>
                    <a href="${ctx}/hgms/hgmsMerchantInfo/detail?id=${hgmsMerchantInfo.merchantId}&goal=6">查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>