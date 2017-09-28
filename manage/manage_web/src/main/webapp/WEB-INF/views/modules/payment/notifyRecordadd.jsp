<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异步通知管理</title>
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
        function send(){
        	
        	
            $.ajax({
                type:"post",
                url:"${ctx}/payment/notifyRecord/adddo",
                data:data,
                success: function(data) {
                  	var result = data.result;
                  	alert(result);
                    location.reload();
                },
                error:function(data){
                    alert(data);
                }
               
            });
			
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/notifyRecord/">异步通知列表</a></li>
	
	</ul>
	<form:form id="searchForm" modelAttribute="notifyRecord" action="${ctx}/payment/notifyRecord/adddo" method="post" class="breadcrumb form-search">
		
		<ul class="ul-form">
			<li><label>交易号 ：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="send()" type="submit" value="补加"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table  table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>通知ID</th>
				<th>交易号</th>
				<th>商户id</th>
				<th>商户账号</th>
				<th>商户订单号</th>
				<th>通知地址</th>
				<th>通知状态</th>
				<th>通知次数 </th>
				<th>通知类型</th>
				<th>通知时间</th>
				<th>应付总额</th>
				<th>成功金额</th>
				<th>支付结果</th>
				<th>支付ID</th>
				<th>创建时间</th>
				<th>业务类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="notifyRecord">
			<tr>
				<td>
					${notifyRecord.notifyId}
				</td>
				<td>
					${notifyRecord.transNo}
				</td>
				<td>
					${notifyRecord.merchantId}
				</td>
				<td>
					${notifyRecord.merchantLoginName}
				</td>
				<td>
					${notifyRecord.merchantOrderNo}
				</td>
				<td>
					${notifyRecord.notifyUrl}
				</td>
				<td   <c:if test="${notifyRecord.status=='SUCCES'}">
			style="background:${success_background}" 
			</c:if> >
					<c:if test="${notifyRecord.status=='SUCCES'}">
					成功
					</c:if>
					<c:if test="${notifyRecord.status=='FAILED'}">
					失败
					</c:if>	
				</td>
				<td>
					${notifyRecord.notifyNumber}
				</td>
				<td>
					<c:if test="${notifyRecord.notifyType=='1'}">
					人工
					</c:if>
					<c:if test="${notifyRecord.notifyType=='2'}">
					系统
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${notifyRecord.notifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${notifyRecord.payAmount}
				</td>
				<td>
					${notifyRecord.successAmount}
				</td>
				<td>
					${notifyRecord.payResult}
				</td>
				<td>
					${notifyRecord.paymentId}
				</td>
				
				<td>
					<fmt:formatDate value="${notifyRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${notifyRecord.productCode=='CP01'}">
					充值
					</c:if>
					<c:if test="${notifyRecord.productCode=='CP06'}">
					快捷支付API
					</c:if>					
					<c:if test="${notifyRecord.productCode=='CP07'}">
					快捷支付H5
					</c:if>
					<c:if test="${notifyRecord.productCode=='CP08'}">
					快捷支付PC
					</c:if>
					<c:if test="${notifyRecord.productCode=='CP18'}">
					兴业点芯
					</c:if>
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>