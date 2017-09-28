<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资金归集商户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
        });

        function checkExcel(){
            var fileName = $("#declareExcelFile").val();
            var reg=/[^\.](\.xls)$/i;
            if(!reg.test(fileName)){
                alert("请导入xls格式的Excel文件！");
                return false;
            }

            loading('正在上传文件，请稍等...');
            $("#uploadForm").submit();

        }
        //文件导出
        function onExport(){
            var actionURL = $("#uploadForm").attr("action");
            $("#uploadForm").attr("action",$("#uploadForm").attr("action")+"/export");
            validateFrom.validate($("#uploadForm"));
            $("#uploadForm").attr("action",actionURL);
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hgms/hgmsMerchantInfo/">资金归集商户列表</a></li>
		<li class="active"><a href="${ctx}/hgms/hgmsMerchantInfo/toBatchAdd?id=${id}">资金归集商户<shiro:hasPermission name="hgms:hgmsMerchantInfo:edit">批量添加</shiro:hasPermission><shiro:lacksPermission name="hgms:hgmsMerchantInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="uploadForm" action="${ctx}/hgms/hgmsMerchantInfo/doBathUpload?id=${id}" method="post" class="form-horizontal" enctype="multipart/form-data">
		<sys:message content="${message}"/><br><br>
		<div class="control-group">
            <div class="control-left">
                <label class="control-label">商户批量上传文件：</label>
                <div class="controls">
                    <input type="file" id="declareExcelFile" name="declareExcelFile" accept="application/vnd.ms-excel" />
                </div>
            </div>
            <div class="control-right">
                <label class="control-label"></label>
                <div class="controls">
                    <input id="uploadBtn" type="button" class="btn btn-primary" onclick="checkExcel()" value="上传" />
                    <a class="btn btn-primary" href="${ctx}/hgms/hgmsMerchantInfo/exportTemplate?id=${id}" >下载模板文件</a>
                    <c:if test="${not empty message}" >
                        <a class="btn btn-primary" href="${ctx}/hgms/hgmsMerchantInfo/export?id=${id}" >下载失败文件</a>
                    </c:if>
                </div>
            </div>
		</div>
        <br><br>
        <div class="control-group">
            <label class="control-label">填报说明：</label>
            <div class="controls">
                <p>1、只支持上传2003版本的Excel文件</p>
                <p>2、必须按照Excel模板文件填写</p>
                <%--<p>3、每次最多导入100行数据，大于100行数据请分多批导入</p>--%>
            </div>
        </div>
		<div class="form-actions">
			<shiro:hasPermission name="hgms:hgmsMerchantInfo:edit">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
            </shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>