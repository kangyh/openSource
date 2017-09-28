<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户充值信息管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/validateNum.js"></script>
    <script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		var flg = true;
		$(document).ready(function() {


		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        //验证搜索条件
        var validateFrom = {
            validate: function(form){
                var bgTime = $("#beginTime").val();
                var endTime = $("#endTime").val();
                if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
                    $("#messageBox").text("请正确设置查询时间范围!");
                    return ;
                }
                if( bgTime!="" && endTime!=""){
                    if(compareDate(convertDateToJoinStr(bgTime),
                            convertDateToJoinStr(endTime)) > 0){
                        $("#messageBox").text("起始日期不能大于结束日期!");
                        return ;
                    }
                }
                form.submit();
            }
        }

        //验证是否是数字
        function checkValue(obj){
		    if(obj != null && obj != '' && obj != undefined){
                var pattern = new RegExp("[`~!#%$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？.]")
                if(pattern.test(obj)){
                    $("#messageBox").text("业务流水号输入不合法，请重新输入");
                    flg = false;
                    return;
                }else{
                    if(validatePreventInject(obj)){
                        $("#messageBox").text("");
                    }else{
                        $("#messageBox").text("输入字符违法，请重新输入!");
                        flg = false;
                        return;
                    }
                    pattern = new RegExp("[a-zA-Z0-9]+")
                    if(!pattern.test(obj)){
                        $("#messageBox").text("业务流水号输入不合法，请重新输入");
                        flg = false;
                        return;
                    }else{
                        $("#messageBox").text("");
                        flg = true;
                        return;
                    }
                }
            }
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
            $("#searchForm").find("input[type='text']").val("");

            $("#messageBox").text("");
            flg = true;


            $("#raType").find("option").removeAttr("selected");
            $("#raType").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(0)").text($("#raType option[selected]").text());

            $("#payType").find("option").removeAttr("selected");
            $("#payType").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(1)").text($("#payType option[selected]").text());

            $("#resturnCode").find("option").removeAttr("selected");
            $("#resturnCode").find("option:eq(0)").attr("selected","selected");
            $(".select2-chosen:eq(2)").text($("#resturnCode option[selected]").text());
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a id="url_id" href="${ctx}/cuscharge/tpdsCustomCharge/">客户充值信息列表</a></li>
		<shiro:hasPermission name="cuscharge:tpdsCustomCharge:edit"><li><a href="${ctx}/cuscharge/tpdsCustomCharge/form">客户充值信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tpdsCustomCharge" action="${ctx}/cuscharge/tpdsCustomCharge/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>业务流水号：</label>
				<form:input path="businessSeqNo" htmlEscape="false" onchange="checkValue(this.value)" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li><label>类型：</label>
				<form:select id="raType" path="raType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${rlist}" var="rtype">
						<form:option value="${rtype.value}" label="${rtype.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>支付方式：</label>
				<form:select id="payType" path="payType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<c:forEach items="${plist}" var="paytype">
						<form:option value="${paytype.value}" label="${paytype.name}"/>
					</c:forEach>
				</form:select>
			</li>
        </ul>
        <ul class="ul-form">
            <li><label>创建日期：</label>
                <input id="beginTime" name="beginTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
                       value="<fmt:formatDate value="${tpdsCustomCharge.beginTime}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
                <input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" style="width:115px;" class="input-medium Wdate"
                       value="<fmt:formatDate value="${tpdsCustomCharge.endTime}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            </li>
            <li><label>状态：</label>
                <form:select id="resturnCode" path="resturnCode" class="input-xlarge">
                    <form:option value="" label="请选择"/>
                    <form:option value="SUCCESS" label="成功"/>
                    <form:option value="FAIL" label="失败"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}"/>
    <div style="text-align: center;font-size: 2em;padding-bottom: 8px;color: #e9322d;">汇元充值统计【存管网银支付、认证支付】</div>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>总交易笔数</th>
            <th>成功交易笔数</th>
            <th>总交易金额</th>
            <th>成功交易金额</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${totalNum}</td>
            <td>${successNum}</td>
            <td><fmt:formatNumber value="${totalAmount}" pattern="￥0.0000" /></td>
            <td><fmt:formatNumber value="${successAmount}" pattern="￥0.0000" /></td>
        </tr>
        </tbody>
    </table>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>业务流水号</th>
			<th>订单流水号</th>
			<th>类型</th>
			<th>借方台账账户</th>
			<th>贷方台账账户</th>
			<th>币种</th>
			<th>交易金额</th>
			<th>其他金额类型</th>
			<th>其他金额</th>
			<th>支付方式</th>
			<th>支付公司代码</th>
			<th>银行卡号</th>
			<th>二类户账户</th>
			<th>银行名称</th>
			<th>卡类型</th>
			<th>创建时间</th>
			<th>状态</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpdsCustomCharge" varStatus="i">
				<tr>
					<td>${tpdsCustomCharge.businessSeqNo}</td>
					<td>${tpdsCustomCharge.businessOrderNo}</td>
					<td>${tpdsCustomCharge.raType}</td>
					<td>${tpdsCustomCharge.debitAccountNo}</td>
				    <td>${tpdsCustomCharge.cebitAccountNo}</td>
				    <td>${tpdsCustomCharge.currency}</td>
				    <td>
				    	<fmt:formatNumber value="${tpdsCustomCharge.amount}" pattern="￥0.0000" />
				    </td>
				    <td>${tpdsCustomCharge.otherAmounttype}</td>
				    <td>
				    	<fmt:formatNumber value="${tpdsCustomCharge.otherAmount}" pattern="￥0.0000" />
				    </td>
				    <td>${tpdsCustomCharge.payType}</td>
				    <td>${tpdsCustomCharge.busiBranchNo}</td>
				    <td>${tpdsCustomCharge.bankAccountNo}</td>
				    <td>${tpdsCustomCharge.secBankaccNo}</td>
				    <td>${tpdsCustomCharge.bankName}</td>
				    <td>${tpdsCustomCharge.cardType}</td>
				    <td><fmt:formatDate value="${tpdsCustomCharge.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				    <td <c:choose>
                        <c:when test="${tpdsCustomCharge.note=='处理成功'}">
                            style="background:#6cf683"
                        </c:when>
                        <c:when test="${tpdsCustomCharge.note=='受理成功'}">
                            style="background:#6cf683"
                        </c:when>
                        <c:when test="${tpdsCustomCharge.note !='处理成功'}">
                            style="background:#e9322d"
                        </c:when>
                        <c:when test="${tpdsCustomCharge.note !='受理成功'}">
                            style="background:#e9322d"
                        </c:when>
                    </c:choose>>${tpdsCustomCharge.note}</td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>