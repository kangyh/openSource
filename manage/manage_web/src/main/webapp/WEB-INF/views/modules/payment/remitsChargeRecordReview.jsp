<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易管理</title>
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
				var chargeId = $("#chargeId").val();
				var transType = $("#transType").val();
				var result = $("#result").val();
				var processDesc = $("#remark").val();
				var data = {
	                exceptionId:chargeId,
	                transType:transType,
	                processDesc:processDesc,
	                processResult:result
			    }
				$.ajax({
	                type:"post",
	                url:"${ctx}/payment/exceptionRecord/processException",
	                data:data,
	                success: function(data) {
	                    var result = data.result;
	                    parent.showThreeEp();
	                    parent.changeThreeNameEp(result,transType,chargeId);
	                },
	                error:function(data){
	                	parent.showThreeEp();
	                	parent.changeThreeNameEp("系统错误，请稍后再试");
	                }
	            });
	
			});
		});
		
		//连接后台
		function remitsAuditing(status){
			var chargeId = $("#chargeId").val();
			var remark = $("#remark").val();
			if(remark == null || remark == ''){
				alert("请录入处理备注");
				return false;
			}
			var data={
					chargeId :chargeId,
					status:status,
					remark:remark
			}
			$.ajax({
				type:"post",
	            url:"${ctx}/payment/remitsChargeRecord/auditingRemit",
	            data:data,
	            dataType : "json",
	            success: function(data) {
					if(data.flag){
						alert("处理成功");
					}else{
						alert(data.msg);
					}
					window.location.href="${ctx}/payment/remitsChargeRecord"
	            },
	            error:function(data){
	            	alert("系统内部错误");
	            }
			});
		}
		
		
		//查询
	    function queryBankResult(chargeId){
	    	parent.show_one();
	        var data = {
	            exceptionId:chargeId,
	            transType:"CHARGE"
	        }
	        $.ajax({
	            type:"post",
	            url:"${ctx}/payment/exceptionRecord/queryChargeResult",
	            data:data,
	            success: function(data) {
	                var result = data.result;
	                parent.showThree();
	                parent.changeThreeName(result);
	                $("#ngp_cover").hide();
	                return;
	            },
	            error:function(data){
	            	parent.showThreeEp();
	                parent.changeThreeNameEp("系统错误，请稍后再试");
	                $("#ngp_cover").hide();
	            }
	        });
	    }
		//处理
        function processException(chargeId,transType,result){
			
        	var processDesc = $("#remark").val();
            if($.trim(processDesc).length == 0){
				parent.showThree();
            	parent.changeThreeName("请填写处理备注");
				return;
			}
            $("#chargeId").val(chargeId);
            $("#transType").val(transType);
            $("#result").val(result);
            var msg = "";
            if("1000"==result){
            	msg = "成功";
            }else{
            	msg = "失败";
            }
            showThree_cl();
            changeThreeName_cl('确认处理为'+msg+'?');
        }
		function showThree_cl(){
			$('#ngp_cover').height($(document).height());
			$("#ngp_cover").show();
			$(".ngp_pop_three_cl").show();
			$("html,body").animate({scrollTop:0},500);
		}
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
		<li><a href="${ctx}/payment/remitsChargeRecord/">充值管理列表</a></li>
		<li class="active"><a href="javascript:void(0)">汇款充值审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="chargeRecord" action="${ctx}/payment/paymentRecord/save" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/>--%>
		<sys:message content="${message}"/>		
<!-- 		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				充值
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">交易订单号：</label>
			<div class="controls">
				${chargeRecord.id}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户公司名称：</label>
			<div class="controls">
				${chargeRecord.merchantCompany}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户账号：</label>
			<div class="controls">
					${chargeRecord.merchantLoginName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付类型：</label>
			<div class="controls">
				<td>
				<%--${fns:getDictLabel(paymentRecord.productCode, 'ProductCodeType', '')}--%>
						${fns:getDictLabel(paymentRecord.payType, 'payType', '')}
				</td>
			</div>
		</div>
<%-- 		<div class="control-group">
			<label class="control-label">通道合作方：</label>
			<div class="controls">
				<td>${paymentRecord.channelName}</td>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				<td>${paymentRecord.bankName}</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水号：</label>
			<div class="controls">
				<td>${paymentRecord.bankSerialNo}</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${chargeRecord.chargeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际支付金额：</label>
			<div class="controls">
				<fmt:formatNumber value="${paymentRecord.successAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手续费：</label>
			<div class="controls">
				<fmt:formatNumber value="${chargeRecord.feeAmount}" pattern="￥#,##0.00" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付时间：</label>
			<div class="controls">
					<fmt:formatDate value="${paymentRecord.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作IP：</label>
			<div class="controls">
			${chargeRecord.requestIp}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易状态：</label>
			<div class="controls">
				${fns:getDictLabel(chargeRecord.status, 'ChargeRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通知商户状态：</label>
			<div class="controls">
				${fns:getDictLabel(notifyRecord.status, 'NotifyRecordStatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${chargeRecord.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">渠道代码：</label>
			<div class="controls">
				<td>${chargeRecord.channelCode}</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">渠道名称：</label>
			<div class="controls">
				<td>${chargeRecord.channelName}</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">汇入银行：</label>
			<div class="controls">
				<td>${chargeRecord.payBankName}</td>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行交易流水号：</label>
			<div class="controls">
				<td>${chargeRecord.bankSerialnumber}</td>
			</div>
		</div>
	    <div class="control-group">
               <label class="control-label"><font color="red">*</font>处理备注：</label>
               <div class="controls">
                   <form:textarea path="remark" id="remark" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
               </div>
           </div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>&nbsp;&nbsp;
			<input id="btnPass" class="btn btn-primary" type="button" value="审核通过" onclick="remitsAuditing('SUCCESS')"/>&nbsp;&nbsp;
			<input id="btnRefuse" class="btn btn-warning" type="button" value="审核拒绝" onclick="remitsAuditing('AUDREJ')"/>
		</div>
	</form:form>
	<input type="hidden" id="chargeId" value="${chargeRecord.id}"/>
	<input type="hidden" id="transType" value=""/>
	<input type="hidden" id="result" value=""/>
</body>
</html>