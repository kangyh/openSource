<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人商户批次详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#allRecord").click(function(){
				if($(this).attr("checked")){
					$(".exportParam").attr("checked",true);
				}else{
					$(".exportParam").attr("checked",false);
				}
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //搜索
        function onSubmit(){
            validateFrom.validate($("#searchForm"));
        }

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function checkReportDelete() {
        	if( !confirm("确认要上报删除这些记录？") ){
		    	return false;
		    }
			var x = 0 ;
			var reportIds = "";
			$(".exportParam").each(function(){
				if ( $(this).attr("checked") ) {
					x +=1;
					reportIds = reportIds + $(this).val() + "," ;
				}
			})
			if(x==0) {
				alertx("请选择要上报删除的记录");
				return false;
			}else{
				loading('正在提交，请稍等...');
				$.ajax({
					url:"${ctx}/pcac/pcacPersonReport/view/reportManyDelData",
					type:"post",
					cache:false,
					timeout:10000,
					data:{
						"reportIds":reportIds
					},
					success:function(msg){
						closeLoading();
						alertx(msg, closed);
						//$("#messageBox").text(msg);
						/* $(".exportParam").each(function(){
							if ( $(this).attr("checked") ) {
								$("#tr_"+$(this).val() ).remove();
							}
						}) */
						//$("#reportIds").val(reportIds);
						$("#searchForm").submit();
						return ;
					},
					error:function(){
						closeLoading();
						alertx("上报删除失败", closed);
						return ;
					}
				});
				
				//$("#reportIds_reportdelete").val(reportIds);
				//$("#reportDelForm").submit();
			}
			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pcac/pcacPersonReport/view/viewBatchDetail/">批次详情</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="pcacPersonReport" action="${ctx}/pcac/pcacPersonReport/view/viewBatchDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="reportIds" name="reportIds" type="hidden" value="${reportIds}"/>
		<input id="opStatus" name="opStatus" type="hidden" value="${opStatus }"/>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<c:if test="${opStatus eq '04' and type eq 'handle' }"><th>选定<input type="checkbox" name="allRecord" id="allRecord" /></th></c:if>
                <th>商户名称</th>
				<th>证件类型</th>
				<th>证件号码</th>
				<th>商户代码</th>
				<th>操作</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ppReportInfo" varStatus="index">
			<tr id="tr_${ppReportInfo.personReportId}">
                <td>
					${index.index+1}
                </td>
                <c:if test="${opStatus eq '04' and type eq 'handle' }">
                <td>
                	<input type="checkbox" name="personReportId" value="${ppReportInfo.personReportId}" class="exportParam"/>
                </td>
                </c:if>
				<td>
					${ppReportInfo.regName}
				</a></td>
				<c:forEach items="${enumMap.docType}" var="wStatus">
					<c:if test="${ppReportInfo.docType eq wStatus.value}">
						<td>${wStatus.name}</td>
					</c:if>
				</c:forEach>
				<td>
					${ppReportInfo.docCode}
				</td>
				<td>
					${ppReportInfo.cusCode}
				</td>
				<td>
					<a href="${ctx}/pcac/pcacPersonReport/view/viewDetail?personReportId=${ppReportInfo.personReportId}&type=${type}&fromWhere=batchDetail&param=hidden">详情</a>
					<%-- <c:if test="${ppReportInfo.opStatus eq '04' }">
						<a href="${ctx}/pcac/pcacPersonReport/view/reportOneDelData?personReportId=${ppReportInfo.personReportId}&param=hidden">接口删除</a>
					</c:if> --%>
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<div class="form-actions">
	<c:if test="${opStatus eq '04' and type eq 'handle' }">
	<div class="control-left" style="margin-right: 10px;">
	<form id="reportDelForm" action="${ctx}/pcac/pcacPersonReport/view/reportManyDelData" method="post"  >
		<input type="hidden" name="reportIds" id="reportIds_reportdelete" value=""/>
		<input class="btn btn-primary" type="button" value="批量接口删除" onclick="checkReportDelete()" />
	</form>
	</div>
	</c:if>
	
	<div class="control-right" style="margin-right: 10px;">
	<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)" />
	</div>
	</div>
	
</body>
</html>