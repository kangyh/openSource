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
			$("#statisticsDate").find("option").removeAttr("selected");
			$("#statisticsDate").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#statisticsDate option[selected]").text());
		}
        //导出
        function onExport(){
            if(checkDate()){
                var transNo = $("#transNo").val();
                var merchantId = $("#merchantId").val();
                var merchantLoginName = $("#merchantLoginName").val();
                var merchantCompany = $("#merchantCompany").val();
                var beginCreateTime = $("#beginCreateTime").val();
                var endCreateTime = $("#endCreateTime").val();
                var groupType = $("input[name='groupType']:checked").val();
                var host = "${ctx}/payment/exceptionRecord/exportExcel";
                var url = host+"?transNo="+transNo+"&merchantId="+merchantId+"&merchantLoginName="+merchantLoginName + "&merchantCompany="+merchantCompany +
                    "&beginCreateTime="+beginCreateTime+"&endCreateTime="+endCreateTime+"&groupType="+groupType;
                $('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();
            }
        }

        //重置确认
        function resetConfirm(){
            if($("#reset_desc").val() == "" || $("#reset_desc").val() == null){
                alert("请输入重置原因！");
                return ;
            }
            $("#resetForm").submit();
        }
        //重置取消
        function resetCancel(){
            $("#reset_desc").val("");
            $("#ngp_cover").hide();
            $("#ngp_pop_fours").hide();
        }
        //弹出重置弹出框
        function showDynamicPa(resetId, resetTransNo){
        	$("#reset_id").val(resetId);
			$("#reset_trans_no").text(resetTransNo);
            $("#ngp_cover").show();
            $("#ngp_pop_fours").show();
            $("#reset_desc").focus();
        }
	</script>
    <style type="text/css">
        #ngp_cover{position: absolute;width:100%;height:100%;background-color:#000;opacity:0.28;z-index:1;display: none;}
        .ngp_pop_fours{
            position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
            margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);
        }
        .ngp_pop_fours p{
            text-align: center;
        }
        .ngp_pop_fours p label{
            margin-right: 10px;
        }
        .ngp_btn_kt{margin-top: 8px;text-align: center;}
        .ngp_btn_kt span{background:#4287f5;width:120px; height:35px;line-height: 35px;text-align: center;color: #fff;
            margin: auto;font-size: 14px;font-weight: 400; cursor: pointer;display: inline-block;margin-left: 10px;}
		.ngp_name_text{font-size: 16px;color: #333;text-align: center;margin-top: 30px;}
    </style>
</head>
<body>
    <div id="ngp_cover"></div>
	<form id="resetForm" action="${ctx}/payment/exceptionRecord/reset">
		<div class="ngp_pop_fours" id="ngp_pop_fours">
			<p><input type="hidden" id="reset_id" name="resetId"></p>
			<p class="ngp_name_text">订单号:<span id="reset_trans_no"></span></p>
            <p class="ngp_name_text">请输入重置原因:</p>
            <p><input type="text" id="reset_desc" name="resetDesc"></p>
			<h3 class="ngp_btn_kt"><span onclick="resetConfirm()">确定</span><span onclick="resetCancel()">取消</span></h3>
		</div>
	</form>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/payment/exceptionRecord/getProcessList">投诉处理查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="exceptionRecord" action="${ctx}/payment/exceptionRecord/getProcessList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易订单号：</label>
				<form:input path="transNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商户编码：</label>
                <form:input path="merchantId" htmlEscape="false" maxlength="20" class="input-medium" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')} else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
            </li>
            <li><label>商户账号：</label>
                <form:input path="merchantLoginName" htmlEscape="false" maxlength="255" class="input-medium"/>
            </li>
            <li><label>商户公司名称：</label>
                <form:input path="merchantCompany" htmlEscape="false" maxlength="255" class="input-medium"/>
            </li>
			<li><label>时间：</label>
				<input id="beginCreateTime" name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${exceptionRecord.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input id="endCreateTime" name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${exceptionRecord.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<form:select id="statisticsDate" path="statisticsDate" class="input-medium" style="width:100px;" onchange="changeDateSection(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="全部"/>
					<c:forEach items="${fns:getDictList('statisticsDate')}" var="item">
						<form:option value="${item.value}" label="${item.label}"/>
					</c:forEach>
				</form:select>
				<input name="groupType"  type="radio" value="1" style="margin-left: 10px;"  <c:if test="${groupType == 1}">checked="checked"</c:if> >按创建时间
				<input name="groupType" type="radio" value="2" style="margin-right: 10px;" <c:if test="${groupType == 2}">checked="checked"</c:if>>按处理时间
			</li>
			<%-- <li><label>交易类型：</label>
				<form:select id="transType" path="transType" class="input-medium">
					<form:option value="CHARGE" label="充值"/>
					<form:option value="WZDRAW" label="提现"/>
					<form:option value="BATPAY" label="转账"/>
				</form:select>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="onSubmit()"  type="button" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-warning" type="button" onclick="onExport()" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped   table-bordered table-condensed">
		<thead>
		<tr>
			<th>投诉处理总金额</th>
			<th>支付失败总金额</th>
			<th>支付成功总金额</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td id="exceptionTotalAmount">￥0.00</td>
			<td id="failedTotalAmount">￥0.00</td>
			<td id="succesTotalAmount">￥0.00</td>
		</tr>
		</tbody>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>商户编码</th>
                <th>商户公司名称</th>
                <th>商户账号</th>
			    <th>交易订单号</th>
				<th>创建时间</th>
				<th>交易金额</th>
				<th>交易状态</th>
				<th>交易类型</th>
				<th>处理人</th>
                <th>处理时间</th>
                <th>处理备注</th>
				<th>重置人</th>
				<th>重置时间</th>
				<th>重置备注</th>
                <th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="exceptionRecord">
			<tr>
			    <td>
                    ${exceptionRecord.merchantId}
                </td>
                <td>
                    ${exceptionRecord.merchantCompany}
                </td>
                <td>
                    ${exceptionRecord.merchantLoginName}
                </td>
				<td>
				    ${exceptionRecord.transNo}
				</td>
				<td>
                    <fmt:formatDate value="${exceptionRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    <fmt:formatNumber value="${exceptionRecord.transAmount}" pattern="￥#,##0.00" />
                </td>
                <td>
                    ${fns:getDictLabel(exceptionRecord.status, 'PaymentRecordStatus', '')}
                </td>
                <td>
                    ${fns:getDictLabel(exceptionRecord.transType, 'TransType', '')}
                </td>
				<td>
                    ${exceptionRecord.operator}
                </td>
				<td>
                    <fmt:formatDate value="${exceptionRecord.processTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    ${exceptionRecord.processDesc}
                </td>
				<td>
					${exceptionRecord.resetOperator}
				</td>
				<td>
					<fmt:formatDate value="${exceptionRecord.resetTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${exceptionRecord.resetDesc}
				</td>
                <td>
                    <c:if test="${exceptionRecord.resetOperator == null || exceptionRecord.resetOperator == ''}">
                        <a onclick="showDynamicPa(${exceptionRecord.complainProcessId}, ${exceptionRecord.transNo})">重置</a>
                    </c:if>
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
        $(function(){
            var transNo = $("#transNo").val();
            var merchantId = $("#merchantId").val();
            var merchantLoginName = $("#merchantLoginName").val();
            var merchantCompany = $("#merchantCompany").val();
            var beginCreateTime = $("#beginCreateTime").val();
            var endCreateTime = $("#endCreateTime").val();
            var groupType = $("input[name='groupType']:checked").val();
            $.ajax({
                type : "POST",
                url : "${ctx}/payment/exceptionRecord/getStatisticsList",
                data : {
                    "transNo" : transNo,
                    "merchantId" : merchantId,
                    "merchantLoginName" : merchantLoginName,
                    "merchantCompany" : merchantCompany,
                    "beginCreateTime" : beginCreateTime,
                    "endCreateTime" : endCreateTime,
                    "groupType" : groupType
                },
                dataType : "json",
                success : function(data){
                    $("#exceptionTotalAmount").text(data.exceptionTotalAmount);
                    $("#failedTotalAmount").text(data.failedTotalAmount);
                    $("#succesTotalAmount").text(data.succesTotalAmount);
                }
            });

        });
	</script>
</body>
</html>