<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实名认证管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var merchantId = $("#merchantId").val();
				var certificationId = $("#certificationId").val();
				var merchantCompany=$("#merchantCompany").val();
				
				if(!isEmpty(merchantId)&&!/\d+/.test(merchantId)){
					alert("商户编码输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(certificationId)&&!/\d+/.test(certificationId)){
					alert("交易订单号输入不合法，只能输入数字");
					return ;
				}
				if(!isEmpty(merchantCompany)&&!/^[a-zA-Z0-9\u4e00-\u9fa5()（）]+$/.test(merchantCompany)){
					alert("商户公司名称输入不合法，请重新输入");
					return ;
				}
				
				
				$("#searchForm").submit();
			});
			
			//控制输入框只能输入数字
			$("#merchantId,#certificationId").keyup(function(){
				 if(this.value.indexOf(".")==0){
					 this.value="0"+this.value;
				 }
				 this.value = this.value.replace(/[^\d]/g,'');
			});
		});
		
	
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function isEmpty(value){
	 		if(value==undefined||value==""||value.trim()==""){
	 			return true;
	 		}
	 		return false;
	 	}
		//清空
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
 			//默认支付状态
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(0)").text($("#status option[selected]").text());
			$(".select2-chosen:eq(1)").text($("#status option[selected]").text());
			//默认排序方式
			$("#sortOrder").find("option").removeAttr("selected");
			$("#sortOrder").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#sortOrder option[selected]").text());
			
		}
		
		
		//导出
		function exportExcel(){
			if(checkDate()){
				var certificationId = $("#certificationId").val();
				var merchantId = $("#merchantId").val();
				var merchantCompany = $("#merchantCompany").val();
				var beginCreateTime = $("#beginCreateTime").val();
				var endCreateTime = $("#endCreateTime").val();
				var status = $("#status").val();
				var host = "${ctx}/payment/certificationRecord/exportExcel";
				var url = host+"?certificationId="+certificationId+"&merchantId="+merchantId+"&merchantCompany="+merchantCompany +
						"&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+"&status="+status;
				$('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/certificationRecord/">实名认证列表</a></li>
		<shiro:hasPermission name="payment:certificationRecord:edit"><li><a href="${ctx}/payment/certificationRecord/form">实名认证添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="certificationRecord" action="${ctx}/payment/certificationRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input id="certificationId" path="certificationId" htmlEscape="false" maxlength="25" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" id="merchantId" htmlEscape="false" maxlength="19" class="input-medium"/>
			</li>
			<li><label>商户公司名称：</label>
				<form:input id="merchantCompany" path="merchantCompany" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${certificationRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${certificationRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:options items="${fns:getDictList('CertificationStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>升降排序：</label>
				<form:select id="sortOrder" path="sortOrder" class="input-medium">
					<c:forEach items="${fns:getDictList('SortStatus')}" var="sStatus">
						<form:option value="${sStatus.value}" label="${sStatus.label}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input class="btn btn-warning" type="button" onclick="exportExcel()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>成功笔数</th>
				<th>失败笔数</th>
				<th>异常笔数</th>
				<th>成功总额</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td id="successTotalSum">0笔</td>
				<td id="failedTotalSum">0笔</td>
				<td id="errorTotalSum">0笔</td>
				<td id="successTotalAmount">￥0.00</td>
			</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped   table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编码</th>
				<th>商户公司名称</th>
				<th>交易订单号</th>
				<th>认证人姓名</th>
				<th>认证人身份证号</th>
				<th>手续费</th>
				<th>认证状态</th>
				<th>创建时间</th>
				<th>失败原因</th>
				<%-- <shiro:hasPermission name="payment:certificationRecord:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="certificationRecord">
			<tr >
				<%-- <td><a href="${ctx}/payment/certificationRecord/form?id=${certificationRecord.id}">
					${certificationRecord.certificationId}
				</a></td> --%>
				<td>
					${certificationRecord.merchantId}
				</td>
				<td>
					${certificationRecord.merchantCompany}
				</td>
				<td>
					${certificationRecord.certificationId}
				</td>
				<td>
					${certificationRecord.certificationName}
				</td>
				<td>
					${certificationRecord.certificationIdcard}
				</td>
				<td>
					<fmt:formatNumber value="${certificationRecord.feeAmount}" pattern="￥#,##0.00" />
				</td>
				<td <c:if test="${certificationRecord.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if>>
					${fns:getDictLabel(certificationRecord.status, 'CertificationStatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${certificationRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${certificationRecord.status!='SUCCES'}">
						${certificationRecord.remark }
					</c:if>
				
				</td>	
			<%-- 	<shiro:hasPermission name="payment:certificationRecord:edit"><td>
    				<a href="${ctx}/payment/certificationRecord/form?id=${certificationRecord.id}">修改</a>
					<a href="${ctx}/payment/certificationRecord/delete?id=${certificationRecord.id}" onclick="return confirmx('确认要删除该实名认证吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$(function(){
			var certificationId = $("#certificationId").val();
			var merchantId = $("#merchantId").val();
			var merchantCompany = $("#merchantCompany").val();
			var beginCreateTime = $("#beginCreateTime").val();
			var endCreateTime = $("#endCreateTime").val();
			var status = $("#status").val(); 
			$.ajax({
				type : "POST",
				url : "${ctx}/payment/certificationRecord/getStatisticsList",
				data : {
					"certificationId" : certificationId,
					"merchantId" : merchantId,
					"merchantCompany" : merchantCompany,
					"beginCreateTime" : beginCreateTime,
					"endCreateTime" : endCreateTime,
					"status" : status
				},
				dataType : "json",
				success : function(data){
					$("#successTotalSum").text(data.successTotalSum);
					$("#failedTotalSum").text(data.failedTotalSum);
					$("#errorTotalSum").text(data.errorTotalSum);
					$("#successTotalAmount").text(data.successTotalAmount);
				}
			});
			
		});
	</script>
</body>
</html>