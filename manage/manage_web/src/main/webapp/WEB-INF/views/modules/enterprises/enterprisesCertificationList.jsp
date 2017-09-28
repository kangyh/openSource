<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业认证对外服务管理</title>
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

        //清空
        function onClear(){
            $("#searchForm").find("input[type='text']").val("");
            //默认扣款状态
            $("#statisticsDate").find("option").removeAttr("selected");
            $("#statisticsDate").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(0)").text($("#statisticsDate option[selected]").text());
        }

        //导出
        function onExport(){
            if(checkDate()){
                var merchantId = $("#merchantId").val();
                var beginCreateTime = $("#beginCreateTime").val();
                var endCreateTime = $("#endCreateTime").val();
                var groupType = $("input[name='groupType']:checked").val();
                var host = "${ctx}/enterprises/enterprisesCertification/exportExcel";
                var url = host+"?merchantId="+merchantId+"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+"&groupType="+groupType;
                $('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprises/enterprisesCertification/">企业认证对外服务列表</a></li>
		<shiro:hasPermission name="enterprises:enterprisesCertification:edit"><li><a href="${ctx}/enterprises/enterprisesCertification/form">企业认证对外服务添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="enterprisesCertification" action="${ctx}/enterprises/enterprisesCertification/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${enterprisesCertification.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${enterprisesCertification.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按创建时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按更新时间
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-warning" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
		<tr>
			<th>交易金额总金额</th>
			<th>手续费总金额</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td id="requestAmountTotalAmount">￥0.00</td>
			<td id="feeAmountTotalAmount">￥0.00</td>
		</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>ID</th>
				<th>商户ID</th>
				<th>公司名称</th>
				<th>工商注册号</th>
				<th>法人代表名称</th>
				<th>法人代表身份证号</th>
				<th>认证结果</th>
				<th>返回信息</th>
				<th>通道流水号</th>
				<th>交易金额</th>
				<th>手续费</th>
				<th>公司名称比对结果</th>
				<th>工商注册号比对结果</th>
				<th>法定代表人比对结果</th>
				<th>法定代表人身份证号码比对结果</th>
				<th>通道代码</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="enterprises:enterprisesCertification:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterprisesCertification">
			<tr>
				<td><a href="${ctx}/enterprises/enterprisesCertification/form?id=${enterprisesCertification.id}">
					${enterprisesCertification.id}
				</a></td>
				<td>
					${enterprisesCertification.merchantId}
				</td>
				<td>
					${enterprisesCertification.entName}
				</td>
				<td>
					${enterprisesCertification.entRegNo}
				</td>
				<td>
					${enterprisesCertification.repName}
				</td>
				<td>
					${enterprisesCertification.repIdNo}
				</td>
				<td>
					${enterprisesCertification.result}
				</td>
				<td>
					${enterprisesCertification.message}
				</td>
				<td>
					${enterprisesCertification.channelNo}
				</td>
				<td>
					${enterprisesCertification.requestAmount}
				</td>
				<td>
					${enterprisesCertification.feeAmount}
				</td>
				<td>
					<c:if test="${enterprisesCertification.entNameMatch ==0 }">
						不一致
					</c:if>
					<c:if test="${enterprisesCertification.entNameMatch ==1 }">
						一致
					</c:if>						
				</td>
				<td>
					<c:if test="${enterprisesCertification.entRegNoMatch ==0 }">
						不一致
					</c:if>
					<c:if test="${enterprisesCertification.entRegNoMatch ==1 }">
						一致
					</c:if>						
				</td>
				<td>
					<c:if test="${enterprisesCertification.repNameMatch ==0 }">
						不一致
					</c:if>
					<c:if test="${enterprisesCertification.repNameMatch ==1 }">
						一致
					</c:if>						
				</td>
				<td>
					<c:if test="${enterprisesCertification.repIdNoMatch ==0 }">
						不一致
					</c:if>
					<c:if test="${enterprisesCertification.repIdNoMatch ==1 }">
						一致
					</c:if>						
				</td>
				<td>
					${enterprisesCertification.channelCode}
				</td>
				<td>
					<fmt:formatDate value="${enterprisesCertification.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${enterprisesCertification.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="enterprises:enterprisesCertification:edit"><td>
    				<a href="${ctx}/enterprises/enterprisesCertification/form?id=${enterprisesCertification.id}">修改</a>
					<a href="${ctx}/enterprises/enterprisesCertification/delete?id=${enterprisesCertification.id}" onclick="return confirmx('确认要删除该企业认证对外服务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
        $(function(){
            var merchantId = $("#merchantId").val();
            var beginCreateTime = $("#beginCreateTime").val();
            var endCreateTime = $("#endCreateTime").val();
            var groupType = $("input[name='groupType']:checked").val();
            $.ajax({
                type : "POST",
                url : "${ctx}/enterprises/enterprisesCertification/getStatisticsList",
                data : {
                    "merchantId" : merchantId,
                    "beginCreateTime" : beginCreateTime,
                    "endCreateTime" : endCreateTime,
                    "groupType" : groupType
                },
                dataType : "json",
                success : function(data){
                    $("#requestAmountTotalAmount").text(data.requestAmountTotalAmount);
                    $("#feeAmountTotalAmount").text(data.feeAmountTotalAmount);
                }
            });
        });
	</script>
</body>
</html>