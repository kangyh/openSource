<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>差错管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
		/*上传图片*/
		.upload{
			position: relative;
		}
		#receiptsPathHand {
			width: 130px;
			height: 85px;
			cursor: pointer;
			opacity: 0;
			z-index: 1;
			position: absolute;
			left: 0px;
			top: 0px;
		}

		.small_img {
			display: inline-block;
			width: 130px;
			height: 85px;
			vertical-align: middle;
		}

		.small_img img {
			width: 100%;
			height: 100%;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){

				    var beginRowNumRule = /(^[1-9]([0-9]+)?(\.[0-9]{1,4})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
					var beginRowNum=$('#beginRowNum').val();
					if(beginRowNum != ''){
                        if(!beginRowNumRule.test(beginRowNum)){
                            alertx("账单金额输入错误，请重新输入!");
                            return false;
                        }
					}

                    var handleMessage = $("#handleMessage").val();
                    var requestAmount = $("#requestAmount").val();
                    var beginRowNum = $("#beginRowNum").val();
                    if(handleMessage == 'Y'){
                        if(Number(requestAmount) != Number(beginRowNum)){
                            $("#msg").html("账单金额和我方金额不一致");
                            return false;
                        }else{
                            $("#msg").html("*");
                        }
                    }

					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});


		//判断选择的状态
		function changeValue(id) {
			if(id.value == 'Y'){
			    $("#hiddenDiv").removeAttr("style");
			}else if(id.value == 'N'){
                $("#hiddenDiv").attr("style","display: none");
			}
        }

        //上传图片
        $(function () {
            $("#receiptsPathHand").change(function () {
                var objUrl = getObjectURL(this.files[0]);
                if (objUrl) {
                    $(this).siblings("span").find("img").attr("src", objUrl);
                }
            });
            //建立一個可存取到該file的url
            function getObjectURL(file) {
                var url = null;
                if (window.createObjectURL != undefined) { // basic
                    url = window.createObjectURL(file);
                } else if (window.URL != undefined) { // mozilla(firefox)
                    url = window.URL.createObjectURL(file);
                } else if (window.webkitURL != undefined) { // webkit or chrome
                    url = window.webkitURL.createObjectURL(file);
                }
                return url;
            }
		});

		//图片预览
        function showImg() {
			 var objUrl =  $("#HandImage").attr("src");

			 if(objUrl!="${ctxStatic}/adjustMoney/sign_upload.jpg"){
				 $("#resultImg").attr("src", objUrl);
				 closeModal('fenpeiModal');
			 }
        }

        function closeModal(){
            $('#fenpeiModal').modal('show');
            $(".pop").show();
        }

        //判断金额是否一致
        function changeMoney(id) {
            var handleMessage = $("#handleMessage").val();
            var requestAmount = $("#requestAmount").val();

            if(handleMessage == 'Y'){
				if(Number(requestAmount) != Number(id.value)){
					$("#btnSubmit").attr("disabled","true");
					$("#msg").html("账单金额和我方金额不一致");
				}else{
                    $("#btnSubmit").removeAttr("disabled");
                    $("#msg").html("*");
				}
			}
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adjustMoney/settleDifferRrecord?cache=1">对账差错列表</a></li>
		<li class="active"><a href="#">调账处理页面</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settleDifferRecord" action="${ctx}/adjustMoney/settleDifferRrecord/save/${settleDifferRecord.differId}" method="post" enctype="multipart/form-data" class="form-horizontal">
	<div class="control-group">
		<label class="control-label">通道合作方：</label>
		<div class="controls">
			<input value="${settleDifferRecord.channelName}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付通道类型：</label>
		<div class="controls">
			<input value="${settleDifferRecord.channelType}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">差错日期：</label>
		 <div class="controls">
			<fmt:formatDate value="${settleDifferRecord.errorDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
	</div> 
	<div class="control-group">
		<label class="control-label">差错类型：</label>
		<div class="controls">
			<input value="${settleDifferRecord.differType}" readOnly="true"style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">交易订单号：</label>
		<div class="controls">
			<input id="transNo" value="${settleDifferRecord.transNo}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付单号：</label>
		<div class="controls">
			<input id="paymentId" value="${settleDifferRecord.paymentId}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">我方金额：</label>
		<div class="controls">
			<input id="requestAmount" value="${settleDifferRecord.requestAmount}" readOnly="true" style="border:0px;background-color:#fff;padding-top: 3px;"/>
		</div>
	</div>
		<div class="control-group">
			<label class="control-label">对方金额：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${not empty settleDifferRecord.successAmount}">
						<fmt:formatNumber value="${settleDifferRecord.successAmount}" pattern="￥0.0000" />
					</c:when>
					<c:otherwise>
						--
					</c:otherwise>
				</c:choose>

			</div>
		</div>

	
	<div class="control-group">
		<label class="control-label">处理结果：</label>
		<div class="controls">
		 <!-- 隐藏域用来显示提示警告 -->
		<input id="handleMessageid"  class="required" type="hidden"/>
	    	<form:select id="handleMessage" path="handleMessage" name="handleMessage" class="input-xlarge required" onclick="changeValue(this)">
	    	    <option selected value="">-请选择-</option>
			    <c:forEach items="${errorNote}" var="errorNoteControl">
					<form:option value="${errorNoteControl.value}" label="${errorNoteControl.name}"/>
				</c:forEach>
		    </form:select>
		    <span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>

	<div id="hiddenDiv" style="display: none">
		<div class="control-group">
			<label class="control-label">通道流水：</label>
			<div class="controls">
				<form:input path="channleNo" id="channleNo" value="${settleRuleControl.bankSeq}" htmlEscape="false" maxlength="32" class="required" style="width:256px;"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账单金额：</label>
			<div class="controls">
				<form:input path="successAmount" id="beginRowNum" value="${settleRuleControl.beginRowNum}" htmlEscape="false" onchange="changeMoney(this)" maxlength="100" class="required" style="width:256px;"/>
				<span class="help-inline" id="msg" style="color: red"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上传回执单：</label>
			<div class="controls upload">
				<span class="small_img"><img src="${ctxStatic}/adjustMoney/sign_upload.jpg"  id="HandImage"></span>
				<input type="file"  multiple="multiple" name="receiptsPathHand" id="receiptsPathHand" class="required"/>
				<span class="help-inline" style="cursor: pointer" onclick="showImg()"><a>查看大图</a></span>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
	</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return confirmation()" value="保存并提交"/>
			<input id="btnCancel" class="btn" type="button" value="取 消" onclick="history.go(-1)"/>
		</div>
	</form:form>

	<!-- 模态框（Modal）  -->
	<div class="modal fade" id="fenpeiModal" tabindex="-1" role="dialog"
		 style="width:50%;display:none;width: 600px" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"
							data-dismiss="modal" aria-hidden="true" onclick="closeModal('fenpeiModal')">
						&times;
					</button>
					<br>
					<h4 class="modal-title"  id="titleName">银行回执单</h4>
				</div>
				<div class="pop">
					<img src="" id="resultImg"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
							data-dismiss="modal" onclick="closeModal('fenpeiModal')">取消
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>