<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人商户上报查询</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		String.prototype.trim = function() {
		  return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
		}
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
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
	        
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
		<li class="active"><a href="${ctx}/pcac/pcacPersonReport/view/">个人商户信息上报列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="pcacPersonReport" action="${ctx}/pcac/pcacPersonReport/view/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="exportParam" name="exportParam" type="hidden" value=""/>
		<ul class="ul-form">
			<li><label>申请时间：</label>
				<input id="beginFillerTime" name="beginFillerTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacPersonReport.beginFillerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endFillerTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endFillerTime" name="endFillerTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacPersonReport.endFillerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
					value="<fmt:formatDate value="${pcacPersonReport.beginReviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endReviewTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endReviewTime" name="endReviewTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacPersonReport.endReviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginReviewTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>上报时间：</label>
				<input id="beginRepDate" name="beginRepDate" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacPersonReport.beginRepDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endRepDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endRepDate" name="endRepDate" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${pcacPersonReport.endRepDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				
			<c:if test="${pcacPersonReport.opStatus eq '01' or pcacPersonReport.opStatus eq '02' or pcacPersonReport.opStatus eq '03'  }">
				<th>商户名称</th>
				<th>证件类型</th>
				<th>证件号码</th>
				<th>商户代码</th>
				<th>申请人录入时间</th>
				<th>录入人</th>
			<c:if test="${pcacPersonReport.opStatus eq '02' or pcacPersonReport.opStatus eq '03'   }">
				<th class="readySyndic">审核时间</th>
				<th class="readySyndic">审核人</th>
				<th class="readySyndic">审核备注</th>
			</c:if>	
			</c:if>
			<c:if test="${pcacPersonReport.opStatus eq '04' or pcacPersonReport.opStatus eq '05'  }">
				<th class="pass_syndic_reportsuc">批次号</th>
				<th class="pass_syndic_reportsuc">上报时间</th>
				<th class="pass_syndic_reportsuc">上报人</th>
				<th class="pass_syndic_reportsuc">上报数量</th>
				<c:if test="${pcacPersonReport.opStatus eq '05'   }">
				<th class="pass_syndic_reportfail">上报失败原因</th>
				</c:if>
				
			</c:if>
			<c:if test="${ pcacPersonReport.opStatus eq '06'  }">
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
				<tr>
					<td>${seq.index+1}</td>
					<c:if test="${pcacPersonReport.opStatus eq '01' or pcacPersonReport.opStatus eq '02' or pcacPersonReport.opStatus eq '03'   }">
						<td>${ppReportInfo.regName}</td>
						<c:forEach items="${enumMap.legDocType}" var="wStatus">
							<c:if test="${ppReportInfo.docType eq wStatus.value}">
								<td>${wStatus.name}</td>
							</c:if>
						</c:forEach>
						<td>${ppReportInfo.docCode}</td>
						<td>${ppReportInfo.cusCode}</td>
						<td>
							<fmt:formatDate value="${ppReportInfo.uploadTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>${ppReportInfo.uploader}</td>
					<c:if test="${pcacPersonReport.opStatus eq '02' or pcacPersonReport.opStatus eq '03'   }">
						<td><fmt:formatDate value="${ppReportInfo.reviewTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${ppReportInfo.reviewer}</td>
						<td>${ppReportInfo.opStatusNote}</td>
					</c:if>	
					</c:if>
					<c:if test="${pcacPersonReport.opStatus eq '04' or pcacPersonReport.opStatus eq '05'    or pcacPersonReport.opStatus eq '06' }">
						<td>${ppReportInfo.batchNo}</td>
						<td><fmt:formatDate value="${ppReportInfo.repDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${ppReportInfo.repPerson}</td>
						<td>${ppReportInfo.repNum}</td>
					<c:if test="${pcacPersonReport.opStatus eq '05'    }">
						<td>${ppReportInfo.opStatusNote}</td>
					</c:if>
					</c:if>
					
					<td>
							
							<c:if test="${pcacPersonReport.opStatus eq '01' or pcacPersonReport.opStatus eq '02'   or pcacPersonReport.opStatus eq '03'   }">
								<a href="${ctx}/pcac/pcacPersonReport/view/viewDetail?personReportId=${ppReportInfo.personReportId}&type=view&param=hidden">详情</a>
							</c:if>
							<c:if test="${pcacPersonReport.opStatus eq '04' or pcacPersonReport.opStatus eq '05'  or pcacPersonReport.opStatus eq '06' }">
							<a href="${ctx}/pcac/pcacPersonReport/view/viewBatchDetail?batchNo=${ppReportInfo.batchNo}&opStatus=${pcacPersonReport.opStatus}&type=view&pageNo=1">批次详情</a>
							</c:if>
							
					</td>			
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
</body>
</html>