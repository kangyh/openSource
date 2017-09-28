<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投诉管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
//				var exceptionId = $("#exceptionId").val();
//				if(exceptionId==null || exceptionId==""){
//					parent.showThree();
//	                parent.changeThreeName("请输入交易单号");
//					return ;
//				}
				form.submit();
			}
		}
		
		//搜索
		function onSubmit(){
			validateFrom.validate($("#searchForm"));
		}
		function onClear(){
			$("#searchForm").find("input[type='text']").val("");
			//默认排序方式
			$("#transType").find("option").removeAttr("selected");
			$("#transType").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#transType option[selected]").text());
		}
		//动态口令
		$(document).ready(function() {
            $("#hybl").on("click",function(){
                var url = $(this).attr("url");
                parent.showDynamicPa();
                parent.enterDynamicPa(url);
            });
        });
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/exceptionRecord/list2">投诉处理管理</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="exceptionRecord" action="${ctx}/payment/exceptionRecord/list2" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>支付单号：</label>
				<form:input path="exceptionId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>交易单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="onSubmit()"  type="button" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	
</body>
</html>