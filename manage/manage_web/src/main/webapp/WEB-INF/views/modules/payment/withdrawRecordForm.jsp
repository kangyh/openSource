<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//处理取消
			$("#ngp_ok_c").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_three_cl").hide();
			});
			//处理确定
			$("#ngp_ok_q").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_three_cl").hide();
				$("#inputForm").submit();
			});
		});
		
		//审核通过
		function auditPass(auditResult){
			$("#status").val(auditResult);
			showThree_cl();
            changeThreeName_cl('确认审核通过?');
		}
		//审核拒绝
		function auditReject(auditResult){
			var auditDesc = $("#auditDesc").val();
			if(""==auditDesc){
				parent.showThree();
            	parent.changeThreeName("请填写审核备注");
				return;
			}
			$("#status").val(auditResult);
			showThree_cl();
            changeThreeName_cl('确认审核拒绝?');
		}
		//处理
		function showThree_cl(){
			$("#ngp_cover").show();
			$(".ngp_pop_three_cl").show();
		}
		//修改处理
		function changeThreeName_cl(a){
			$("#three_text_cl").text(a);
		}
	</script>
	<style>
	#ngp_cover{position: absolute;width:100%;height:100%;background-color:#000;opacity:0.28;z-index:1;display: none;}
	.ngp_pop_three_cl{position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
		    margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}
	.ngp_name_text{font-size: 16px;color: #333;text-align: center;margin-top: 30px;}
	.ngp_btn_kt{margin-top: 30px;text-align: center;}
	.ngp_btn_kt span{background:#4287f5;width:120px; height:35px;line-height: 35px;text-align: center;color: #fff;
	        margin: auto;font-size: 14px;font-weight: 400; cursor: pointer;display: inline-block;margin-left: 10px;}
	</style>
</head>
<body>
	<div id="ngp_cover"></div>
	<div class="ngp_pop_three_cl">
	    <p class="ngp_name_text" id="three_text_cl"></p>
	    <h3 class="ngp_btn_kt"><span id="ngp_ok_q">确定</span><span id="ngp_ok_c">取消</span></h3>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/payment/withdrawRecord/">提现管理列表</a></li>
		<li class="active"><a href="javascript:void(0)">提现详细查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="withdrawRecord" action="${ctx}/payment/withdrawRecord/auditCtrl?id=${withdrawRecord.withdrawId}&merchantId=${withdrawRecord.merchantId}" method="post" class="form-horizontal">
		<%-- <form:hidden path="id"/> --%>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
					${withdrawRecord.withdrawId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户账号：</label>
			<div class="controls">
					${withdrawRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现银行卡号：</label>
			<div class="controls">
				${ withdrawRecord.bankcardNo}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
					${withdrawRecord.bankcardOwnerName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户银行名称：</label>
			<div class="controls">
				${withdrawRecord.openBankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现金额：</label>
			<div class="controls">
						<fmt:formatNumber value="${withdrawRecord.withdrawAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费：</label>
			<div class="controls">
				<fmt:formatNumber value="${withdrawRecord.feeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<fmt:formatDate value="${withdrawRecord.createTime}" pattern="YYYY-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交银行时间：</label>
			<div class="controls">
				<fmt:formatDate value="${withdrawRecord.updateTime}" pattern="YYYY-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现状态：</label>
			<div class="controls">
					${fns:getDictLabels(withdrawRecord.status,'WithdrawStatus','')}
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">提现备注：</label>
            <div class="controls">
                    ${withdrawRecord.remark}
            </div>
        </div>
		<div class="control-group">
            <label class="control-label">网关信息：</label>
            <div class="controls">
                    ${withdrawRecord.gatewayMsg}
            </div>
        </div>
        <div class="control-group">
			<label class="control-label">审核人：</label>
			<div class="controls">
				${withdrawRecord.auditPerson}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核时间：</label>
			<div class="controls">
				<fmt:formatDate value="${withdrawRecord.auditTime}" pattern="YYYY-mm-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核备注：</label>
			<div class="controls">
				<form:textarea path="auditDesc" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">审核结果：</label>
			<div class="controls">
				<form:textarea path="status" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${not empty auditStatus}">
				<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/> -->
				<input id="pass" class="btn btn-primary" type="button" value="审核通过" onclick="auditPass('PENDNG')"/>
				<input id="reject" class="btn btn-primary" type="button" value="审核拒绝" onclick="auditReject('AUDREJ')"/>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>