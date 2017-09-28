<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转账审核管理</title>
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
		textarea{ resize:none; width:350px; height:150px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
            $("input").attr("onkeyup","value=value.replace(/[^0-9\u4E00-\u9FA5\a-z\A-Z\.\-]/g,'')")
		});
        function showpop(){
            parent.showThree_one();
            parent.changeThreeName_one("后台正在转账，请耐心等待");
        }
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        // 操作拒绝
        function msgboxclose(){
            var  reasonfailure = document.getElementsByName("reasonfailure")[0].value;
            var  orderInputId = document.getElementById("orderInputId").value;


            if(reasonfailure!=""){
                document.getElementById("orderhidden").value = orderInputId;
                document.getElementById("reasonhidden").value = reasonfailure;
                document.getElementById("examinefailed").action="${ctx}/hgms/transferapply/save/";
                $('#examinefailed').submit();
            }else{
                alert("请填写拒绝理由");
            }
        }
        function inputid(n){

            document.getElementById('orderInputId').value=n;
        }
        $(function(){
            $("#messageBox").show();
        });
        function deleteselect(){
            $("#searchForm").find("input[type='text']").val("");
            //默认状态
            $("#transferType").find("option").removeAttr("selected");
            $("#transferType").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#transferType option[selected]").text());
            //默认状态
            $("#applyStatus").find("option").removeAttr("selected");
            $("#applyStatus").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(1)").text($("#applyStatus option[selected]").text());
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hgms/transferapply/">转账审核列表</a></li>
	</ul>
	<form:form id="examinefailed"  action="${ctx}/hgms/transferapply/" method="post"  class="form-horizontal" >
		<%--<input id="pids" name="pids" type="hidden" value=""/>--%>
		<input id="reasonhidden"  name="reasonhidden" type="hidden" value=""/>
		<input id="orderhidden"  name="orderhidden" type="hidden" value=""/>
		<input id="examineFalg"  name="examineFalg" type="hidden" value="FAILED"/>
	</form:form>
	<form:form id="searchForm" modelAttribute="transferapply" action="${ctx}/hgms/transferapply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width:71px">商户编号：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="100" class="input-medium" cssStyle="width: 126px"/>
			</li>
			<li><label style="width: 86px">订单号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>转账类型：</label>
				<form:select path="transferType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="CHTOSP" label="现金账户到资金归集 "/>
					<form:option value="SPTOCH" label="资金归集到现金账户 "/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${transferapply.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${transferapply.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>申请状态：</label>
				<form:select path="applyStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="AUDING" label="待审核"/>
					<form:option value="SUCCES" label="审核通过"/>
					<form:option value="AUDREJ" label="审核拒绝"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="deleteselect()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编号</th>
				<th>订单号</th>
				<th>转账金额</th>
				<th>转账类型</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>申请状态</th>
				<th>支付单号</th>
				<th>审核人</th>
				<th>审核拒绝理由</th>
				<shiro:hasPermission name="hgms:transferapply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="transferapply">
			<tr>
				<td>
					${transferapply.merchantId}
				</td>
				<td>
					${transferapply.orderId}
				</td>
				<td>
						<fmt:formatNumber value="${transferapply.amount}" pattern="￥0.00" />
				</td>
				<td>
					${transferapply.transferType}
				</td>
				<td>
					<fmt:formatDate value="${transferapply.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${transferapply.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${transferapply.applyStatus}
				</td>
				<td>
						${transferapply.extend1}
				</td>

				<td>
					${transferapply.auditor}
				</td>
				<td>
					${transferapply.rejectReason}
				</td>
				<shiro:hasPermission name="hgms:transferapply:edit"><td>
					<c:if test="${transferapply.applyStatus =='待审核' && transferapply.transferType!='现金账户到资金归集'}">
					<a href = "${ctx}/hgms/transferapply/save?orderhidden=${transferapply.id}" onclick = "document.getElementById('lightsucc').style.display='block'">通过</a>
					<a href = "JavaScript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block';inputid(${transferapply.id})">拒绝</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div id="lightsucc" class="white_content" >
		<font size="4px" color="white">后台正在转账，请耐心等待</font>
	</div>
	<div id="light" class="white_content">
		<div style="margin:0 auto;">
			<font size="6px" color="red">请填写拒绝理由：</font><br/>
			<input type="hidden" id="orderInputId" value=""/>
			<textarea name="reasonfailure"  maxlength="500"></textarea><br/>
			<shiro:hasPermission name="cbms:CbmsOrderexamineController:edit">
				<input type="button" onclick = "msgboxclose();document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'" value="确定"/> </shiro:hasPermission>&nbsp;&nbsp;&nbsp;
			<input type="button" onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none';" value="返回"/>
		</div>
	</div>
	<div id="pifade" class="black_overlay"></div>
	<div id="fade" class="black_overlay"></div>
	<div class="pagination">${page}</div>
</body>
</html>