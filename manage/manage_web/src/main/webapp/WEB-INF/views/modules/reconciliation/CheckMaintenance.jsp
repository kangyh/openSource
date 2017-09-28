<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>对账管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
        #main {
            margin: 50px;
        }
    </style>
    
	<script type="text/javascript">
		$(document).ready(function() {
		
			var bankValue = $("#channelCode").val();
			if(bankValue=='DIRCON'){
				$('#bank_id').css('display','block');
			}else{
				$('#bank_id').css('display','none');
			}
			//$("#channelName").val(obj.options[obj.options.selectedIndex].text);
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
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		//文件导出
		function onExport(){
			var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"/export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
		}
		
		//添加
		function onSubmitAdd(){
			$("#updateadd").submit();
		}
		
		//修改用户名密码
		function onPrompt(){
			var username=prompt("用户名","");
			var password=prompt("密码","");
		   	$.ajax({
				url:"${ctx}/reconciliation/CheckMaintenance/modify",
				type:"get",
				cache:false,
				data:{
					"username":username,
					"password":password
				},
				success:function(msg){
					if(msg == 1000){
						alert("修改成功");
					}else{
						alert("修改失败");
					}
				}
			});
		    
		}
		
		function Sel2change(field){
			
			$("#bankName").val(field);
		}
		
		function checkValue(obj){
			var bankValue = obj.value;
			if(bankValue=='DIRCON'){
				$('#bank_id').css('display','block');
			}else{
				$('#bank_id').css('display','none');
			}
			$("#channelName").val(obj.options[obj.options.selectedIndex].text);
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reconciliation/CheckMaintenance">对账参数维护列表</a></li>
	</ul>
	<form action="${ctx}/reconciliation/CheckMaintenance" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="settleChannelManager" action="${ctx}/reconciliation/CheckMaintenance" method="post" class="breadcrumb form-search">
		
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		
			
			<li><label class="control-label">通道合作方：</label>
					
			    	<form:select  path="channelCode" name="channelCode" id="channelCode" class="input-xlarge" onchange="checkValue(this)" >
			    	    <option selected value="">-通道合作方-</option>
			    	 
					    <c:forEach items="${dataEntity}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
						</c:forEach>
				    </form:select>
			    <input type="hidden" name="channelName" id="channelName" >
			</li>
			<li style="display: none;" id="bank_id" class="bankId"><label>选择直连银行：</label>
				<form:select id="bankNoid" path="bankNo" name="bankNo" class="input-xlarge" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="-选择直连银行-"/>
						<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
				</form:select>
				<input type="hidden" name="bankName" id="bankName" class="input-xlarge required">
			</li>
			
			<li><label>支付通道类型：</label>
				<form:select id="channelType" path="channelType" class="input-xlarge">
					<form:option value="" label="-支付通道类型-"/>
						<c:forEach items="${checkTypeList}" var="SettleRuleControl">
							<form:option value="${SettleRuleControl.value}" label="${SettleRuleControl.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			</ul>
			<ul class="ul-form">
			<li><label>对账标识：</label>
				<form:select id="checkFlg" path="checkFlg" class="input-xlarge">
					<form:option value="" label="-对账标识-"/>
						<c:forEach items="${manageCheckFlg}" var="manageCheckFlgControl">
							<form:option value="${manageCheckFlgControl.value}" label="${manageCheckFlgControl.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			
			<li><label>生效标识：</label>
				<form:select id="effectFlg" path="effectFlg" class="input-xlarge">
					<form:option value="" label="-生效标识-"/>
						<c:forEach items="${becomngeEffect}" var="becomngeEffectControl">
							<form:option value="${becomngeEffectControl.value}" label="${becomngeEffectControl.name}"/>
						</c:forEach>
				</form:select> 
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			<li class="btns"><input id="btnAddSubmit" class="btn btn-primary" type="button" onclick="onSubmitAdd()" value="添加"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<li class="btns"><input id="btnPrompt" class="btn btn-primary" type="button" onclick="onPrompt()" value="操作"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>通道合作方</th>
			<th>支付通道类型</th>
			<th>对账标识</th>
			<th>对账文件获取地址</th>
			<th>对账文件存放地址</th>
			<th>生效标识</th>
			<th>对账方式</th>
			<th>规则类型</th>

			<th>创建者</th>
			<th>创建时间</th>
			<th>更新者</th>
			<th>更新时间</th>

			<shiro:hasPermission name="settle:settleChannelManager:view"><th>操作</th></shiro:hasPermission>
			
			
		</tr>
		</thead>
		<tbody>
		
		<form id="updateadd" method="get" action="${ctx}/reconciliation/CheckMaintenance/updateAndAdd"></form>
		
		<c:forEach items="${page.list}" var="settleChannelManager">
			<tr>
				<td>${settleChannelManager.channelName}</td>
				<td>${settleChannelManager.channelType}</td>
				<td>${settleChannelManager.checkFlg}</td>
				<td>${settleChannelManager.remoteAdress}</td>
				<td>${settleChannelManager.localFilePath}</td>
				<td <c:choose>
			     		<c:when test="${settleChannelManager.effectFlg=='生效'}">
			     		 style="background:#6cf683" 	
			     		</c:when>
			     		<c:when test="${settleChannelManager.effectFlg=='无效'}">
			     		 style="background:red" 
			     		</c:when>
			     	</c:choose>>${settleChannelManager.effectFlg}</td>
				
				<td>${settleChannelManager.settleWay}</td>
				<td>${settleChannelManager.ruleType}</td>
				<td>${settleChannelManager.createAuthor}</td>
				<td>
					<fmt:formatDate value="${settleChannelManager.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${settleChannelManager.updateAuthor}</td>
				<td>
					<fmt:formatDate value="${settleChannelManager.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<shiro:hasPermission name="settle:settleChannelManager:view"><td>
					<a href="${ctx}/reconciliation/CheckMaintenance/delete/${settleChannelManager.channelManangeId}" onclick="return confirmx('确认要删除该记录吗？', this.href)">删除</a>
				|   <a style="cursor:pointer;" class="checkPass"   value-url="${ctx}/reconciliation/CheckMaintenance/updateAndAdd?channelManangeId=${settleChannelManager.channelManangeId}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>