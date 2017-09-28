<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>同申报批次号详情管理</title>
	<meta name="decorator" content="default"/>
	<style>

		.white_content {
			border:1px ;    /*边框*/

			text-align: center;        /*文字水平居中对齐*/

			/* line-height: 100%;  */      /*设置文字行距等于div的高度*/
			padding-top: 200px;
			padding-bottom: 200px;
			overflow:hidden;
			display: none;
			position: fixed;
			/* padding-left:50%;
             padding-top:20%;
 */         background: rgba(3,0,17,0.28);
			top: 0%;
			left: 0%;
			width: 100%;
			height: 100%;
			z-index:1001;
			-moz-opacity: 0.8;

			filter: alpha(opacity=88);
		}
		.scs{

			width:200px;font-size:12px;

			border:0px solid #ddd;

			overflow:hidden;

			text-overflow:ellipsis;

			white-space:nowrap;}
		textarea{ resize:none; width:350px; height:150px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
            var width=$("#contentTable").width();
            $("#ulTableId").attr("style","width:"+width+"px;");
            $("#searchForm").attr("style","width:"+(width-27)+"px;");
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //批量通过
        function examinesuces(){
            var chk = document.getElementsByName("chk");
            var id = "";
            for(var i = 0 ; i<chk.length;i++){
                if(chk[i].checked){
                    id+=chk[i].value+",";
                }
            }
            pid = id.substring(0,id.length-1);
            document.getElementById("pids").value = pid;
            $('#examinesuces').submit();
        }
        //批量拒绝
        function examinedele(){
            var chk = document.getElementsByName("chk");
            reasonfailure = document.getElementsByName("reasonfailure")[0].value;
            var id = "";
            for(var i = 0 ; i<chk.length;i++){
                if(chk[i].checked){
                    id+=chk[i].value+",";
                }
            }
            pid = id.substring(0,id.length-1);
            document.getElementsByName("pids")[0].value = pid;
            document.getElementById("reasonhidden").value = reasonfailure;
            document.getElementById("examinesuces").action="${ctx}/cbms/CbmsOrderexamineController/examinefalse/";
            $('#examinesuces').submit();

        }
        function selectAll(){
            var check = document.getElementById("checkAll");
            var chk = document.getElementsByName("chk");
            for(var i = 0 ; i<chk.length;i++){
                chk[i].checked = check.checked;
            }
        }
        $(function(){
            $("#messageBox").show();
        });
	</script>
</head>
<body>
	<ul class="nav nav-tabs" id="ulTableId">
		<li class="active"><a href="${ctx}/cbms/CbmsOrderexamineController/">通关申报订单详情</a></li>
		<%--<shiro:hasPermission name="cbms:cbmsOrderform:edit"><li><a href="${ctx}/cbms/cbmsOrderform/form">同申报批次号详情添加</a></li></shiro:hasPermission>--%>
	</ul>
    <form:form id="examinesuces"  action="${ctx}/cbms/CbmsOrderexamineController/examinesucces/" method="post"  class="form-horizontal" >
        <input id="pids" name="pids" type="hidden" value=""/>
        <input id="reasonhidden"  name="reasonhidden" type="hidden" value=""/>
        <input id="importBatchNumber"  name="importBatchNumber" type="hidden" value="${cbmsOrderform.importBatchNumber}"/>
        <input id="orderhidden"  name="orderhidden" type="hidden" value=""/>
    </form:form>
	<form:form id="searchForm"  modelAttribute="cbmsOrderSum" action="${ctx}/cbms/cbmsOrderform/orderformlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="importBatchNumber" name="importBatchNumber" type="hidden" value="${cbmsOrderform.importBatchNumber}"/>

	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
						<input type="checkbox"  id="checkAll"  onclick="selectAll()"/>
					</th>
				<th>审核状态</th>
				<th>审核失败原因</th>
				<th>批次号</th>
				<th>海关编码</th>
				<th>支付交易编号</th>
				<th>原始支付企业名称</th>
				<th>电子订单号</th>
				<th>电商企业编号</th>
				<th>电商平台名称</th>
				<th>证件类型</th>
				<th>付款人证件号</th>
				<th>付款人名称</th>
				<th>付款人手机号</th>
				<th>支付金额</th>
				<th>付款时间</th>
				<th>交易订单号</th>
				<th>交易状态</th>
				<th>报送类型</th>
				<th>报送状态</th>
				<th>标识</th>
				<th>实名认证结果</th>
				<th>模式代码</th>
				<th>主管海关代码</th>
				<th>检验检疫机构代码</th>
				<th>标的物名称</th>
				<th>数量</th>
				<th>单位</th>
				<th>支付用途</th>
				<th>支付税款</th>
				<th>支付运费</th>
				<th>支付货款</th>
				<th>支付币种</th>
				<th>支付交易状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cbmsOrderform">
			<tr><%--&& cbmsOrderform.transStatus !='手续费扣款失败' &&  cbmsOrderform.customStatus !='5'--%>
				<td>
					<c:if test="${(cbmsOrderform.orderStatus== '待审核'|| cbmsOrderform.orderStatus== '不可审核通过' ) && cbmsOrderform.transStatus!='手续费扣款失败' &&  cbmsOrderform.customStatus !='取消'}">
					<input type="checkbox"  name="chk" value="${cbmsOrderform.orderId}" />
					</c:if>
				</td>
				<td><c:out value="${cbmsOrderform.orderStatus}"/></td>
				<td><c:out value="${cbmsOrderform.auditFailReason}"/></td>
				<td><c:out value="${cbmsOrderform.importBatchNumber}"/></td>
				<td><c:out value="${cbmsOrderform.customCode}"/></td>
				<td><c:out value="${cbmsOrderform.orgPayTransno}"/></td>
				<td><c:out value="${cbmsOrderform.orgPayEnterpriseName}"/></td>
				<td><c:out value="${cbmsOrderform.orderFormNo}"/></td>
				<td><c:out value="${cbmsOrderform.busEnterpriseNo}"/></td>
				<td><c:out value="${cbmsOrderform.busEnterpriseName}"/></td>
				<td><c:out value="${cbmsOrderform.payErcertificateType}"/></td>
				<td><c:out value="${cbmsOrderform.payErcertificateNo}"/></td>
				<td><c:out value="${cbmsOrderform.payName}"/></td>
				<td><c:out value="${cbmsOrderform.payerPhone}"/></td>
				<td><c:out value="${cbmsOrderform.rmbPayAmount}"/></td>
				<td nowrap="nowrap"><fmt:formatDate value="${cbmsOrderform.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><c:out value="${cbmsOrderform.transId}"/></td>
				<td><c:out value="${cbmsOrderform.transStatus}"/></td>
				<td><c:out value="${cbmsOrderform.transCode}"/></td>
				<td><c:out value="${cbmsOrderform.customStatus}"/></td>
				<td><c:out value="${cbmsOrderform.identification}"/></td>
				<td><c:out value="${cbmsOrderform.authMsg}"/></td>
				<td><c:out value="${cbmsOrderform.modeCode}"/></td>
				<td><c:out value="${cbmsOrderform.mainCustomCode}"/></td>
				<td><c:out value="${cbmsOrderform.inspectionQuarantineCode}"/></td>
				<td><div class="scs" title="<c:out value="${cbmsOrderform.commodityName}"/>"><c:out value="${cbmsOrderform.commodityName}"/></div></td>
				<td><c:out value="${cbmsOrderform.commodityCount}"/></td>
				<td><c:out value="${cbmsOrderform.commodityUnit}"/></td>
				<td><c:out value="${cbmsOrderform.payPurpose}"/></td>
				<td><c:out value="${cbmsOrderform.payTaxes}"/></td>
				<td><c:out value="${cbmsOrderform.freight}"/></td>
				<td><c:out value="${cbmsOrderform.rmbPrices}"/></td>
				<td><c:out value="${cbmsOrderform.currencyId}"/></td>
				<td><c:out value="${cbmsOrderform.payStatus}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>

	<c:if test="${status!='1'}">
		<shiro:hasPermission name="cbms:CbmsOrderexamineController:edit"><input type="button" value="批量通过" onclick="examinesuces();document.getElementById('lightsucc').style.display='block'"/></shiro:hasPermission>
	&nbsp;&nbsp;<shiro:hasPermission name="cbms:CbmsOrderexamineController:edit"><input type="button" value="批量拒绝" onclick="document.getElementById('pilight').style.display='block';document.getElementById('pifade').style.display='block'"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
	</c:if>
	<div id="pilight" class="white_content" >
		<div style="margin:0 auto;">
			<font size="6px" color="red">请填写拒绝理由：</font><br/>

			<textarea name="reasonfailure" maxlength="500"></textarea><br/>
			<shiro:hasPermission name="cbms:CbmsOrderexamineController:edit">
				<input type="button" onclick = "examinedele();document.getElementById('pilight').style.display='none';document.getElementById('pifade').style.display='none'" value="确定"/> </shiro:hasPermission>&nbsp;&nbsp;&nbsp;
			<input type="button" onclick = "document.getElementById('pilight').style.display='none';document.getElementById('pifade').style.display='none';" value="返回"/>
		</div>
	</div>
	<div id="lightsucc" class="white_content" >

		<font size="4px" color="white">正在申报海关，请耐心等待</font>

	</div>
	<div id="pifade" class="black_overlay"></div>
	<div id="fadesucc" class="black_overlay"></div>
	<div class="pagination">${page}</div>
</body>
</html>