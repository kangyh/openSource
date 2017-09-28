<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>企业商户信息上报列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});
function check() {
	var opStatusNote = $("#opStatusNote").val();
	var opStatus = $("input:radio[name='opStatus']:checked").val();
	var reportIds = $("#reportIds").val();
	if(opStatusNote==null || opStatusNote==""){
		alertx("请填写备注");
		return false;
	}else if( opStatusNote.length > 40 ) {
		alertx("备注文字太长");
		return false;
	}else if( !checkSafe(opStatusNote) ){
		alertx("备注包含非法字符");
		return false;
	}else if(opStatus==undefined || opStatus == null || opStatus == "" ){
		alertx("请选择审核状态");
		return false;
	}else{
		loading('正在提交，请稍等...');
		$.ajax({
			url:"${ctx}/pcac/pcacMerchantReport/view/syndicBatch/",
			type:"post",
			cache:false,
			timeout:10000,
			data:{
				"reportIds":reportIds,
				"opStatus":$("input[name='opStatus']:checked").val() ,
				"opStatusNote":$("#opStatusNote").val() 
			},
			success:function(msg){
				closeLoading();
				alertx(msg);
				$("#btnSubmit").remove();
				return ;
			},
			error:function(){
				closeLoading();
				alertx("审核失败", closed);
				return ;
			}
		});
		//$("#inputForm").submit();
		//return ;
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
function returnQuery() {
	$("#searchForm").submit();
}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pcac/pcacMerchantReport/view">企业商户信息列表</a></li>
		<li class="active"><a>批量审核</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="syndicEntity" action="${ctx}/pcac/pcacMerchantReport/view/syndicBatch/" method="post" class="form-horizontal">
		
		
		<div style="font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;padding-top: 20px;">企业商户信息批量审核:</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">审核总笔数：</label>
				<div class="controls">
					${reportIdsLength}
				</div>
			</div>
			
		</div>
		

		    审核备注<input name="opStatusNote" id="opStatusNote" />
		    <input type="radio" name="opStatus" value="02"/>审核通过
		    <input type="radio" name="opStatus" value="03"/>审核拒绝
		    <input id="reportIds" name="reportIds" value="${reportIds }" type="hidden"/>
		    <div id="messageBox" style="font-size: 15px; color: red;"></div>
			<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" onclick="check()"  value="保存"/> 
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="returnQuery()" />
			</div>
	</form:form>
	<form  id="searchForm" modelAttribute="pcacMerchantReport" action="${ctx}/pcac/pcacMerchantReport/view/handle" method="post"  >
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="beginFillerTime_s" name="beginFillerTime" type="hidden" value="${pcacPersonReport.beginFillerTime}"/>
				<input id="endFillerTime_s" name="endFillerTime" type="hidden" value="${pcacPersonReport.endFillerTime}" />
				<input id="opStatus_s" name="opStatus"   type="hidden"  value="${pcacPersonReport.opStatus}"/>
				<input id="cusCode_s" name="cusCode"   type="hidden"  value="${pcacPersonReport.cusCode}"/>
				<input id="regName_s"  name="regName"  type="hidden"   value="${pcacPersonReport.regName}"/>
				<input id="beginReviewTime_s" name="beginReviewTime" type="hidden"  value="${pcacPersonReport.beginReviewTime}" />
				<input id="endReviewTime_s" name="endReviewTime" type="hidden"   value="${pcacPersonReport.endReviewTime}"/>
				<input id="beginRepDate_s" name="beginRepDate" type="hidden"   value="${pcacPersonReport.beginRepDate}"/>
				<input id="endRepDate_s" name="endRepDate" type="hidden"   value="${pcacPersonReport.endRepDate}"/>
				<input id="uploader_s"  name="uploader"  type="hidden"  value="${pcacPersonReport.uploader}"/>
				<input id="reviewer_s"  name="reviewer"  type="hidden"  value="${pcacPersonReport.reviewer}"/>
				<input id="repPerson_s"  name="repPerson" type="hidden"   value="${pcacPersonReport.repPerson}"/>
		</form>
</body>
</html>