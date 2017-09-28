<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异步通知管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//全选反选
			$("#checkboxId").bind("click", function(){
			    if ($(this).attr("checked")) {  
			        $(":checkbox").attr("checked", true);  
			    } else {  
			        $(":checkbox").attr("checked", false);  
			    }  
			});
			
			//批量发送
			$("#btnSave").bind("click", function(){
				var selectedData = [];
	        	$(":checkbox[name='checkName']:checked").each(function(index, item){
	        		var tablerow = $(this).parent().parent();
					var notifyId = tablerow.find("[name='notifyId']").val();
	        		selectedData.push({'notifyId':notifyId});
				});
				
	            $.ajax({
	                    type:"post",
	                    url:"${ctx}/payment/notifyRecord/send",
	                    dataType:'text',
	                    data : {"notifyId" : JSON.stringify(selectedData)},
	                    success: function(data) {
	                        alert(data);
	                        location.reload();
	                    },
	                    error:function(data){
	                        alert(data);
	                    }
	                   
	                });
			});
		
		
		
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function send(id){
        	$("#gosend"+id).unbind("click");
        	$("#gosend"+id).html("请稍后。。。");
        	
        	
        	var selectedData = [];
        	
        	selectedData.push({'notifyId':id});
        	
        	
            $.ajax({
                type:"post",
                url:"${ctx}/payment/notifyRecord/send",
                data : {"notifyId" : JSON.stringify(selectedData)},
                dataType:'text',
                success: function(data) {
                	  alert(data);
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
		<li class="active"><a href="${ctx}/payment/notifyRecord/form">异步通知列表</a></li>
		
	</ul>
	<form:form id="searchForm" modelAttribute="notifyRecord" action="${ctx}/payment/notifyRecord/form" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易号 ：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户登录账号：</label>
				<form:input path="merchantLoginName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			
			<li><label>通知状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="R1" label="全部"/>
					<form:option value="SUCCES" label="成功"/>
					<form:option value="FAILED" label="失败"/>
				</form:select>
			</li>
			
			
			<li><label>支付单号：</label>
				<form:input path="paymentId" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="merchantOrderNo" htmlEscape="false" maxlength="26" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
		<ul class="ul-form">
		<li class="btns"><input id="btnSave" class="btn btn-warning" type="button" value="批量补发"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table  table-striped table-bordered table-condensed">
		<thead>
			<tr>
			
				<th rowspan="2"><input type="checkbox" id="checkboxId"/>选择</th>
				<th>通知单号</th>
				<th>交易号</th>
				<th>商户编码</th>
				<th>商户账号</th>
				<th>商户订单号</th>
				<th>通知地址</th>
				<th>通知状态</th>
				<th>通知次数 </th>
				<th>通知类型</th>
				<th>通知时间</th>
				<th>返回信息</th>
				<th>最后通知时间</th>				
				<th>应付总额</th>
				<th>成功金额</th>
				<th>支付结果</th>
				<th>支付订单号</th>
				<th>创建时间</th>
				<th>业务类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="notifyRecord" varStatus="status">
			<tr>
				<td><input type="checkbox" name="checkName" value="${status.index}" /></td>
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
					${notifyRecord.notifyResponse}
				</td>
				<td>
					<fmt:formatDate value="${notifyRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
				<td>
					<input type="hidden" name="notifyId" value="${notifyRecord.notifyId }" />
    				<a href="#" id="gosend${notifyRecord.notifyId}" onclick="send(${notifyRecord.notifyId})">补发通知</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>