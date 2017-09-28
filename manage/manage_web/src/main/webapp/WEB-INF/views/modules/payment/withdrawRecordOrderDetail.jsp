<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现订单详情</title>
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
				var withdrawId = $("#withdrawId").val();
				var transType = $("#transType").val();
				var result = $("#result").val();
				var processDesc = $("#processDesc").val();
				var data = {
	                exceptionId:withdrawId,
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
	                    parent.changeThreeNameEp(result,transType,withdrawId);
	                },
	                error:function(data){
	                	parent.showThreeEp();
	                	parent.changeThreeNameEp("系统错误，请稍后再试");
	                }
	            });

			});
		});
		
		//查询
        function queryBankResult(withdrawId){
        	parent.show_one();
            var data = {
                exceptionId:withdrawId
            }
            $.ajax({
                type:"post",
                url:"${ctx}/payment/exceptionRecord/queryBatchPayBankResult",
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
        function processException(withdrawId,transType,result){
        	var processDesc = $("#processDesc").val();
			if($.trim(processDesc).length == 0 || "null" == $.trim(processDesc)){
				parent.showThree();
            	parent.changeThreeName("请填写处理备注");
				return;
			}
            $("#withdrawId").val(withdrawId);
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
      
		//处理
		function showThree_cl(){
			$('#ngp_cover').height($(document).height());
			$("#ngp_cover").show();
			$(".ngp_pop_three_cl").show();
			$("html,body").animate({scrollTop:0},500);
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
	<c:if test="${empty exception}">
		<li><a href="${ctx}/payment/withdrawRecord/">提现管理列表</a></li>
		<li class="active"><a href="${ctx}/payment/withdrawRecord/form?id=${withdrawRecord.id}">提现管理<shiro:hasPermission name="payment:withdrawRecord:edit">${not empty withdrawRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="payment:withdrawRecord:edit">查看</shiro:lacksPermission></a></li>
	</c:if>
	<c:if test="${not empty exception}">
		<li><a href="">提现详情</a></li>
	</c:if>
</ul><br/>
<form:form id="inputForm" modelAttribute="withdrawRecord" action="${ctx}/payment/withdrawRecord/auditCtrl?id=${withdrawRecord.withdrawId}" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">交易订单号：</label>
		<div class="controls">
				${withdrawRecord.withdrawId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">商户编码：</label>
		<div class="controls">
				${withdrawRecord.merchantId}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">商户登录账号：</label>
		<div class="controls">
				${withdrawRecord.merchantLoginName}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">商户公司名称：</label>
		<div class="controls">
				${withdrawRecord.merchantCompany}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">创建时间：</label>
		<div class="controls">
			<fmt:formatDate value="${withdrawRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">更新时间：</label>
		<div class="controls">
			<fmt:formatDate value="${withdrawRecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">提现金额：</label>
		<div class="controls">
			${withdrawRecord.withdrawAmount}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">手续费：</label>
		<div class="controls">
				${withdrawRecord.feeAmount}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">提现银行卡号：</label>
		<div class="controls">
				${withdrawRecord.bankcardNo}
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">提现状态：</label>
		<div class="controls">
				${fns:getDictLabels(withdrawRecord.status,'WithdrawStatus','')}
		</div>
	</div>
	<c:if test="${not empty exception}">
        <div class="control-group">
            <label class="control-label">处理备注：</label>
            <div class="controls">
                <form:textarea path="processDesc" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
            </div>
        </div>
    </c:if>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		<c:if test="${not empty exception}">
           <input id="queryResult" class="btn" type="button" value="查询银行处理结果" onclick="queryBankResult('${withdrawRecord.withdrawId}')"/>
           <c:if test="${withdrawRecord.status == 'DRAING' || withdrawRecord.status == 'PENDNG'}">
           	    <input id="processSuccess" class="btn" type="button" value="处理为成功" onclick="processException('${withdrawRecord.withdrawId}','WZDRAW','1000')"/>
           		<input id="processFail" class="btn" type="button" value="处理为失败" onclick="processException('${withdrawRecord.withdrawId}','WZDRAW','1002')"/>
           </c:if>
           <c:if test="${withdrawRecord.status == 'FAILED'}">
           		<input id="processFail" class="btn" type="button" value="处理为失败" onclick="processException('${withdrawRecord.withdrawId}','WZDRAW','1002')"/>
           </c:if>
        </c:if>
	</div>
</form:form>
<input type="hidden" id="withdrawId" value=""/>
<input type="hidden" id="transType" value=""/>
<input type="hidden" id="result" value=""/>
</body>
</html>