<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业商户信息上报处理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		//用于验证
		var flg = true;	
		
		$(document).ready(function() {
			$("#allRecord").click(function(){
				if($(this).attr("checked")){
					$(".exportParam").attr("checked",true);
				}else{
					$(".exportParam").attr("checked",false);
				}
				
			});
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var cusCode = $("#cusCode_q").val().trim();
				var regName = $("#regName_q").val().trim();
				var uploader = $("#uploader_q").val().trim();
				var reviewer = $("#reviewer_q").val().trim();
				var repPerson = $("#repPerson_q").val().trim();
				if(!checkSafe(cusCode)){
					alertx("商户代码包含非法字符!");
					return ;
				}
				if(!checkSafe(regName)){
					alertx("商户名称包含非法字符!");
					return ;
				}
				if(!checkSafe(uploader)){
					alertx("申请人包含非法字符!");
					return ;
				}
				if(!checkSafe(reviewer)){
					alertx("审核人包含非法字符!");
					return ;
				}
				if(!checkSafe(repPerson)){
					alertx("上报人包含非法字符!");
					return ;
				}
				
				var beginFillerTime = $("#beginFillerTime").val();
				var endFillerTime = $("#endFillerTime").val();
				var beginReviewTime = $("#beginReviewTime").val();
				var endReviewTime = $("#endReviewTime").val();
				var beginRepDate = $("#beginRepDate").val();
				var endRepDate = $("#endRepDate").val();
				if(beginFillerTime=="" && endFillerTime!="" || beginFillerTime!="" && endFillerTime==""
						|| beginReviewTime=="" && endReviewTime!="" || beginReviewTime!="" && endReviewTime=="" 
						|| beginRepDate=="" && endRepDate!="" || beginRepDate!="" && endRepDate=="" ){
					alertx("请正确设置查询时间范围!");
					return ;
				}
				if( beginFillerTime!="" && endFillerTime!=""){
					if(compareDate(convertDateToJoinStr(beginFillerTime),
									convertDateToJoinStr(endFillerTime)) > 0){
						alertx("起始日期不能大于结束日期!");
						return ;
					}
				}
				if( beginReviewTime!="" && endReviewTime!=""){
					if(compareDate(convertDateToJoinStr(beginReviewTime),
									convertDateToJoinStr(endReviewTime)) > 0){
						alertx("起始日期不能大于结束日期!");
						return ;
					}
				}
				if( beginRepDate!="" && endRepDate!=""){
					if(compareDate(convertDateToJoinStr(beginRepDate),
									convertDateToJoinStr(endRepDate)) > 0){
						alertx("起始日期不能大于结束日期!");
						return ;
					}
				}
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
			flg = true;
			
			$("#searchForm").find("input[type='text']").val("");
			
			//查询类型
			$("#pcacReportOpStatus").find("option").removeAttr("selected");
			$("#pcacReportOpStatus").find("option:eq(0)").attr("selected","selected");
		}
		
		//验证是否是数字
		function checkNum(obj){
			if(isNaN(obj)){
				alertx("请输入数字！");
				flg = false;
				return;
			}else{
				flg = true;
				return;
			}
		}
		
		//文件导出
		function onExport(){
			var exportParam = "";
			$(".exportParam").each(function(){
				if ( $(this).attr("checked") ) {
					exportParam = exportParam + $(this).val() + "," ;
				}
			})
			$("#exportParam").val(exportParam);
	        var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action","${ctx}/pcac/pcacMerchantReport/view/export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
	        
		}
		function checkSyndic() {
			var x = 0 ;
			var reportIds = "";
			$(".exportParam").each(function(){
				if ( $(this).attr("checked") ) {
					x +=1;
					reportIds = reportIds + $(this).val() + "," ;
				}
			})
			if(x==0) {
				alertx("请选择要审核的记录");
				return false;
			}else{
				$("#reportIds").val(reportIds);
				$("#beginFillerTime_s").val($("#beginFillerTime").val());
				$("#endFillerTime_s").val($("#endFillerTime").val());
				$("#opStatus_s").val($("#opStatus").val());
				$("#cusCode_s").val($("#cusCode").val());
				$("#regName_s").val($("#regName").val());
				$("#beginReviewTime_s").val($("#beginReviewTime").val());
				$("#endReviewTime_s").val($("#endReviewTime").val());
				$("#beginRepDate_s").val($("#beginRepDate").val());
				$("#endRepDate_s").val($("#endRepDate").val());
				$("#uploader_s").val($("#uploader").val());
				$("#reviewer_s").val($("#reviewer").val());
				$("#repPerson_s").val($("#repPerson").val());
				$("#syndicInitForm").submit();
			}
			
		}
		function checkDelete() {
			if( !confirm("确认要删除这些记录？") ){
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
				alertx("请选择要删除的记录");
				return false;
			}else{
				loading('正在提交，请稍等...');
				$.ajax({
					url:"${ctx}/pcac/pcacMerchantReport/view/deleteManyData",
					type:"post",
					cache:false,
					timeout:10000,
					data:{
						"reportIds":reportIds
					},
					success:function(msg){
						closeLoading();
						/* $("#messageBox").text(msg);
						$(".exportParam").each(function(){
							if ( $(this).attr("checked") ) {
								alert("#tr_"+$(this).val());
								 $("#tr_"+$(this).val() ).remove();
							}
						}) */
						alertx(msg, closed);
						$("#searchForm").submit();
						return ;
					},
					error:function(){
						closeLoading();
						alertx("删除失败", closed);
						return ;
					}
				});
				
				//$("#reportIds_delete").val(reportIds);
				//$("#deleteForm").submit();
			}
			
		}
		function checkReport() {
			if( !confirm("确认要上报这些记录？") ){
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
				alertx("请选择要上报的记录");
				return false;
			}else{
				loading('正在提交，请稍等...');
				$.ajax({
					url:"${ctx}/pcac/pcacMerchantReport/view/reportManyData",
					type:"post",
					cache:false,
					timeout:10000,
					data:{
						"reportIds":reportIds
					},
					success:function(msg){
						closeLoading();
						/* $("#messageBox").text(msg);
						$(".exportParam").each(function(){
							if ( $(this).attr("checked") ) {
								 $("#tr_"+$(this).val() ).remove();
							}
						}) */
						alertx(msg, closed);
						$("#searchForm").submit();
						return ;
					},
					error:function(){
						closeLoading();
						alertx("上报失败", closed);
						return ;
					}
				});
				//$("#reportIds_report").val(reportIds);
				//$("#reportForm").submit();
			}
			
		}
		function checkSafe(a){
			if ( a.indexOf("script")>-1 || a.indexOf("\\")>-1 ){
				return false;
			}
			fibdn = new Array ("'",">","<","*","%","#","$","}","{","~","`","!","￥","/","?","&","^","(",")","=",";");
			i=fibdn.length;
			j=a.length;
			for (ii=0; ii<i; ii++) {
				for (jj=0; jj<j; jj++) {
					temp1=a.charAt(jj);
					temp2=fibdn[ii];
					if (temp1==temp2){
					return false;
					}
				}
			}
			
			return true;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pcac/pcacMerchantReport/view/handle">企业商户信息上报处理</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="pcacMerchantReport" action="${ctx}/pcac/pcacMerchantReport/view/handle" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="exportParam" name="exportParam" type="hidden" value=""/>
		<ul class="ul-form">
			<li><label>申请时间：</label>
				<input id="beginFillerTime" name="beginFillerTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacMerchantReport.beginFillerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endFillerTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endFillerTime" name="endFillerTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacMerchantReport.endFillerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginFillerTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>查询类型：</label>
				<form:select id="opStatus" path="opStatus" class="input-xlarge">
					<c:forEach items="${enumMap.opStatus}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>商户代码：</label>
				<form:input id="cusCode_q" path="cusCode"  htmlEscape="false"  style="width:256px;" class="input-medium required"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>商户名称：</label>
				<form:input id="regName_q"  path="regName"  htmlEscape="false" style="width:350px;" class="input-medium required"/>
			</li>
			<li><label>审核时间：</label>
				<input id="beginReviewTime" name="beginReviewTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacMerchantReport.beginReviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endReviewTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endReviewTime" name="endReviewTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacMerchantReport.endReviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginReviewTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>上报时间：</label>
				<input id="beginRepDate" name="beginRepDate" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacMerchantReport.beginRepDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endRepDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endRepDate" name="endRepDate" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacMerchantReport.endRepDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginRepDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>申请人：</label>
				<form:input id="uploader_q"  path="uploader"  htmlEscape="false" style="width:350px;" class="input-medium required"/>
			</li>
			<li><label>审核人：</label>
				<form:input id="reviewer_q"  path="reviewer"  htmlEscape="false" style="width:350px;" class="input-medium required"/>
			</li>
			<li><label>上报人：</label>
				<form:input id="repPerson_q"  path="repPerson"  htmlEscape="false"  style="width:256px;" class="input-medium required"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
				<th>序号</th>
				
			<c:if test="${pcacMerchantReport.opStatus eq '01' or pcacMerchantReport.opStatus eq '02' or pcacMerchantReport.opStatus eq '03'  }">
				<th>选定<input type="checkbox" name="allRecord" id="allRecord" /></th>
				<th>商户名称</th>
				<th>法人证件类型</th>
				<th>法人证件号码</th>
				<th>法定代表人姓名</th>
				<th>法定代表人证件类型</th>
				<th>法定代表人证件号码</th>
				<th>商户代码</th>
				<th>申请人录入时间</th>
				<th>录入人</th>
			<c:if test="${pcacMerchantReport.opStatus eq '02' or pcacMerchantReport.opStatus eq '03'  }">
				<th class="readySyndic">审核时间</th>
				<th class="readySyndic">审核人</th>
				<th class="readySyndic">审核备注</th>
			</c:if>	
			</c:if>
			<c:if test="${pcacMerchantReport.opStatus eq '04' or pcacMerchantReport.opStatus eq '05' }">
				<th class="pass_syndic_reportsuc">批次号</th>
				<th class="pass_syndic_reportsuc">上报时间</th>
				<th class="pass_syndic_reportsuc">上报人</th>
				<th class="pass_syndic_reportsuc">上报数量</th>
				<c:if test="${pcacMerchantReport.opStatus eq '05'   }">
				<th class="pass_syndic_reportfail">上报失败原因</th>
				</c:if>
			</c:if>
			<c:if test="${ pcacMerchantReport.opStatus eq '06'  }">
					<th class="pass_syndic_reportsuc">批次号</th>
					<th class="pass_syndic_reportsuc">删除时间</th>
					<th class="pass_syndic_reportsuc">删除人</th>
					<th class="pass_syndic_reportsuc">删除数量</th>
			</c:if>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="ppReportInfo" varStatus="seq">
				<tr id="tr_${ppReportInfo.merchantReportId}">
					<td>${seq.index+1}</td>
					
					
					
					<c:if test="${pcacMerchantReport.opStatus eq '01' or pcacMerchantReport.opStatus eq '02' or pcacMerchantReport.opStatus eq '03'   }">
						<td><input type="checkbox" name="merchantReportId" value="${ppReportInfo.merchantReportId}" class="exportParam"/></td>
						<td>${ppReportInfo.regName}</td>
						<c:forEach items="${enumMap.docType}" var="wStatus">
							<c:if test="${ppReportInfo.docType eq wStatus.value}">
								<td>${wStatus.name}</td>
							</c:if>
						</c:forEach>
						<td>${ppReportInfo.docCode}</td>
						<td>${ppReportInfo.legDocName}</td>
						<c:forEach items="${enumMap.legDocType}" var="wStatus">
							<c:if test="${ppReportInfo.legDocType eq wStatus.value}">
								<td>${wStatus.name}</td>
							</c:if>
						</c:forEach>
						<td>${ppReportInfo.legDocCode}</td>
						<td>${ppReportInfo.cusCode}</td>
						<td>
							<fmt:formatDate value="${ppReportInfo.uploadTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>${ppReportInfo.uploader}</td>
					<c:if test="${pcacMerchantReport.opStatus eq '02' or pcacMerchantReport.opStatus eq '03'   }">
						<td><fmt:formatDate value="${ppReportInfo.reviewTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${ppReportInfo.reviewer}</td>
						<td>${ppReportInfo.opStatusNote}</td>
					</c:if>	
					</c:if>
					<c:if test="${pcacMerchantReport.opStatus eq '04' or pcacMerchantReport.opStatus eq '05'   or pcacMerchantReport.opStatus eq '06'  }">
						<td>${ppReportInfo.batchNo}</td>
						<td><fmt:formatDate value="${ppReportInfo.repDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${ppReportInfo.repPerson}</td>
						<td>${ppReportInfo.repNum}</td>
					<c:if test="${pcacMerchantReport.opStatus eq '05' }">
						<td>${ppReportInfo.opStatusNote}</td>
					</c:if>
					</c:if>
					
					<td>
							
							<%-- <shiro:hasPermission name="pcac:pcacMerchantReport:edit">
									<c:if test="${pcacMerchantReport.opStatus eq '01' }">
									<a href="${ctx}/pcac/pcacMerchantReport/view/viewDetail?merchantReportId=${ppReportInfo.merchantReportId}&param=hidden">单笔审核</a>
									<a href="${ctx}/pcac/pcacMerchantReport/view/deleteOneData?merchantReportId=${ppReportInfo.merchantReportId}&param=hidden">单笔删除</a>
									</c:if>
									<c:if test="${pcacMerchantReport.opStatus eq '02' }">
										<a href="${ctx}/pcac/pcacMerchantReport/view/reportOneData?merchantReportId=${ppReportInfo.merchantReportId}&param=hidden">单笔上报</a>
									</c:if>
									<c:if test="${pcacMerchantReport.opStatus eq '04' }">
										<a href="${ctx}/pcac/pcacMerchantReport/view/reportManyDelData?batchNo=${ppReportInfo.batchNo}&param=hidden">接口批次删除</a>
									</c:if>
							</shiro:hasPermission> --%>
							
							<c:if test="${pcacMerchantReport.opStatus eq '01' or pcacMerchantReport.opStatus eq '02'   or pcacMerchantReport.opStatus eq '03'  }">
								<a href="${ctx}/pcac/pcacMerchantReport/view/viewDetail?merchantReportId=${ppReportInfo.merchantReportId}&type=handle&param=hidden">详情</a>
							</c:if>
							<c:if test="${pcacMerchantReport.opStatus eq '04' or pcacMerchantReport.opStatus eq '05' or pcacMerchantReport.opStatus eq '06'  }">
							<a href="${ctx}/pcac/pcacMerchantReport/view/viewBatchDetail?batchNo=${ppReportInfo.batchNo}&opStatus=${pcacMerchantReport.opStatus}&type=handle&pageNo=1">批次详情</a>
							</c:if>
					</td>			
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions">
	<c:if test="${pcacMerchantReport.opStatus ne '04' and pcacMerchantReport.opStatus ne '06' }">
	<div class="control-left" style="margin-right: 10px;">
	<form id="deleteForm" action="${ctx}/pcac/pcacMerchantReport/view/deleteManyData" method="post"  >
		<input type="hidden" name="reportIds" id="reportIds_delete" value=""/>
		<input class="btn btn-primary" type="button" value="批量删除" onclick="checkDelete()" />
	</form>
	</div>
	</c:if>
	
	<c:if test="${pcacMerchantReport.opStatus eq '01'}">
	<div class="control-right" style="margin-right: 10px;">
	<form id="syndicInitForm"  modelAttribute="pcacMerchantReport"  action="${ctx}/pcac/pcacMerchantReport/view/initSyndicBatch" method="post"  >
		<input type="hidden" name="reportIds" id="reportIds" value=""/>
		<input id="beginFillerTime_s" name="beginFillerTime" type="hidden" />
		<input id="endFillerTime_s" name="endFillerTime" type="hidden" />
		<input id="opStatus_s" name="opStatus"   type="hidden" />
		<input id="cusCode_s" name="cusCode"   type="hidden" />
		<input id="regName_s"  name="regName"  type="hidden"  />
		<input id="beginReviewTime_s" name="beginReviewTime" type="hidden"  />
		<input id="endReviewTime_s" name="endReviewTime" type="hidden"  />
		<input id="beginRepDate_s" name="beginRepDate" type="hidden"  />
		<input id="endRepDate_s" name="endRepDate" type="hidden"  />
		<input id="uploader_s"  name="uploader"  type="hidden"/>
		<input id="reviewer_s"  name="reviewer"  type="hidden"/>
		<input id="repPerson_s"  name="repPerson" type="hidden" />
		<input  class="btn btn-primary" type="button" value="批量审核" onclick="checkSyndic()" />
	</form>
	</div>
	</c:if>
	
	<c:if test="${pcacMerchantReport.opStatus eq '02'  }">
	<div class="control-right" style="margin-right: 10px;">
	<form id="reportForm" action="${ctx}/pcac/pcacMerchantReport/view/reportManyData" method="post"  >
		<input type="hidden" name="reportIds" id="reportIds_report" value=""/>
		<input class="btn btn-primary" type="button" value="批量上报" onclick="checkReport()" />
	</form>
	</div>
	</c:if>
	
	
	</div>
</body>
</html>