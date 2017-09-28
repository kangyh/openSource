<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息补发申请</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		//用于验证
		var flg = true;	
		function checkReSend() {
			if( !confirm("确认要补发？") ){
		    	return false;
		    }
			var beginDate = $("#beginDate").val();
			if(beginDate==""  ){
				alertx.text("请填写申请时间!");
				return ;
			}
			loading('正在提交，请稍等...');
			$.ajax({
				url:"${ctx}/pcac/pcacResendApply/view/resendApply",
				type:"post",
				cache:false,
				timeout:10000,
				data:{
					"beginDate":beginDate,
					"riskType":$("#riskType").val()
				},
				success:function(msg){
					closeLoading();
					alertx(msg, closed);
					return ;
				},
				error:function(){
					closeLoading();
					alertx("补发失败", closed);
					return ;
			    }
			});
			
			
		}
		
		
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var beginDate = $("#beginDate").val();
				/* var endDate = $("#endDate").val();*/
				if(beginDate==""  ){
					$("#messageBox").text("请填写申请时间!");
					return ;
				}
				/*if( beginDate!="" && endDate!=""){
					if(compareDate(convertDateToJoinStr(beginDate),
									convertDateToJoinStr(endDate)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
						return ;
					}
				} */
				var actionURL = $("#searchForm").attr("action");
		        $("#searchForm").attr("action",$("#searchForm").attr("action")+"?random="+Math.random());
				form.submit();
			}
		}
		//搜索
		function onSubmit(){
				validateFrom.validate($("#searchForm"));
		}

		//清空
		function onClear(){
			$("#messageBox").text("");
			flg = true;
			
			$("#searchForm").find("input[type='text']").val("");
		}
		
		
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pcac/pcacResendApply/view/">信息补发申请</a></li>
	</ul>
	<form:form id="searchForm"  modelAttribute="pcacResendApply"  action="${ctx}/pcac/pcacResendApply/view/resendApply" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>推送日期：</label>
				<input id="beginDate" name="beginDate" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacResendApply.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
<%-- 					 - 
				<input id="endDate" name="endDate" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacResendApply.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> --%>
			</li>
			<li><label>推送类型：</label>
				<form:select id="riskType" path="riskType" class="input-xlarge">
					<c:forEach items="${riskTypes}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="checkReSend()" value="申请补发"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
		</ul>
		
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
</body>
</html>