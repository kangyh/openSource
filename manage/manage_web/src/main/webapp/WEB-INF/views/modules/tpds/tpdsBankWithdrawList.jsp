<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成功管理</title>
	<meta name="decorator" content="default"/>
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
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpds/tpdsBankWithdraw/">存管银行提现列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tpdsBankWithdraw" action="${ctx}/tpds/tpdsBankWithdraw/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>业务流水号：</label>
				<form:input path="businessSeqNo" htmlEscape="false" onchange="checkValue(this.value)" maxlength="64" style="width:256px;" class="input-medium required"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
    <div style="text-align: center;font-size: 2em;padding-bottom: 8px;color: #e9322d;">汇元提现统计</div>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>总交易金额</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><fmt:formatNumber value="${totalAmount}" pattern="￥0.0000" /></td>
        </tr>
        </tbody>
    </table>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>业务流水号</th>
				<th>类型</th>
				<th>币种</th>
				<th>交易金额</th>
				<th>银行卡号</th>
				<th>创建时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpdsBankWithdraw">
			<tr>
				<td>${tpdsBankWithdraw.businessSeqNo}</td>
				<td>${tpdsBankWithdraw.raType}</td>
				<td>${tpdsBankWithdraw.currency}</td>
				<td>
					<fmt:formatNumber value="${tpdsBankWithdraw.amount}" pattern="￥0.0000" />
				</td>
				<td>${tpdsBankWithdraw.bankAccountNo}</td>
				<td><fmt:formatDate value="${tpdsBankWithdraw.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${tpdsBankWithdraw.note}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>