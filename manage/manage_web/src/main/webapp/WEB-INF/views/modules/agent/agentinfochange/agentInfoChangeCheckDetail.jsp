<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商修改信息审核管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		inputReadonly{
			border:none
		}

	</style>
	<link href="${ctxStatic}/common/img.css" type="text/css" rel="stylesheet">
	<script type="text/javascript">
        $(document).ready(function() {
            $(".pop_img span").on("click",function(e){
                e.stopPropagation();
                $(this).parent().hide();
            });

            $(".popA").on("click",function(e){
                e.stopPropagation();
                $(".pop_img").hide();
                $(this).siblings(".pop_img").show();
            });
            $(document).click(function(){
                $(".pop_img").hide();
            });

            var status='${agentInfoChange.agentChangeStatus}';
            if(status!='系统待审'){
                  //去掉审核通过和审核拒绝的按钮
                $('#btnCheck').removeAttr('onclick').hide().next().removeAttr('onclick').hide();
			}
        });
        function doCheck(status){
            window.location="${ctx}/agent/agentinfochange/agentInfoChange/doCheck?agentChangeStatus="+status+"&id="+$('#id').val();
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li><a href="${ctx}/agent/agent/agentInfo/">代理商列表</a></li>--%>
		<%--&lt;%&ndash;<li class="active"><a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">代理商信息管理<shiro:hasPermission name="agent:agent:agentInfo:edit">${not empty agentInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="agent:agent:agentInfo:edit">查看</shiro:lacksPermission></a></li>&ndash;%&gt;--%>
		<%--<li class="active"><a href="">代理商详情</a></li>--%>
	<%--</ul><br/>--%>
	<form:form id="inputForm" modelAttribute="agentInfoChange" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<h3>基本信息</h3>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">代理商状态：</label>
				<div class="controls">
					<input value="${agentInfoChange.agentChangeStatus}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>

		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">代理商名称：</label>
				<div class="controls">
					<input value="${agentInfoChange.agentName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">代理商名称简称：</label>
				<div class="controls">
					<input value="${agentInfoChange.shortName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">负责人姓名：</label>
				<div class="controls">
					<input value="${agentInfoChange.headName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
<%--					<form:input path="headName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>--%>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">负责人手机号：</label>
				<div class="controls">
					<input value="${agentInfoChange.headPhone}" maxlength="11" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">负责人身份证号：</label>
				<div class="controls">
					<input value="${agentInfoChange.headIdcard}" maxlength="18" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">身份证有效期：</label>
				<div class="controls">
					<input value="${agentInfoChange.headIdcardExpiry}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱：</label>
			<div class="controls">
				<input value="${agentInfoChange.email}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">代理商类型：</label>
				<div class="controls">
					<input value="${agentInfoChange.agentType}" maxlength="12" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">有效期：</label>
				<div class="controls">
					<input value="${agentInfoChange.agentExpiry}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">加入日期：</label>
				<div class="controls">
					<input value="<fmt:formatDate value="${agentInfoChange.createTime}" pattern="yyyy年MM月dd日"/>" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">最新修改日期：</label>
				<div class="controls">
					<input value="<fmt:formatDate value="${agentInfoChange.updateTime}" pattern="yyyy年MM月dd日"/>" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<h3>企业信息</h3>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人姓名：</label>
				<div class="controls">
					<input value="${agentInfoChange.legalName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人手机号：</label>
				<div class="controls">
					<input value="${agentInfoChange.legalPhone}" maxlength="11" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人身份证号：</label>
				<div class="controls">
					<input value="${agentInfoChange.legalIdcard}" maxlength="18" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人身份证有效期：</label>
				<div class="controls">
					<input value="${agentInfoChange.legalIdcardExpiry}" maxlength="18" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					<%--<fmt:parseDate value="${agentInfoChange.legalIdcardExpiry}" var="date1"></fmt:parseDate>
					<fmt:formatDate value="${date1 }" pattern="yyyy 年 MM 月 dd 日"/>--%>

<%--					<input name="legalIdcardExpiry" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						value="<fmt:formatDate value="${agentInfo.legalIdcardExpiry}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>--%>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业证照类型：</label>
				<div class="controls">
					<input value="${agentInfoChange.busiType}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">营业证照编号：</label>
				<div class="controls">
					<input value="${agentInfoChange.busiCode}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业证照有效期：</label>
				<div class="controls">
					<input value="${agentInfoChange.busiExpiry}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">注册资金：</label>
				<div class="controls">
					<input value="${agentInfoChange.regAmount}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<c:if test="${agentInfoChange.busiType=='普通'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">组织机构代码证：</label>
					<div class="controls">
						<input value="${agentInfoChange.orgInstCode}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">税务登记证：</label>
					<div class="controls">
						<input value="${agentInfoChange.taxRegCode}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">所在地：</label>
			<div class="controls">
				<input value="${agentInfoChange.addrProvinceName} ${agentInfoChange.addrCityName} ${agentInfoChange.addrCountryName} ${agentInfoChange.addrDetail}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">代理区域：</label>
			<div class="controls">
				<input value="${agentInfoChange.agentAreaAddr}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>
		<%--<h3>账户信息</h3>--%>
		<%--<div class="control-group">--%>
			<%--<div class="control-left">--%>
				<%--<label class="control-label">账户类型：</label>--%>
				<%--<div class="controls">--%>
					<%--&lt;%&ndash;<input value="${agentInfo.bankAccountType}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />&ndash;%&gt;--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="control-right">--%>
				<%--<label class="control-label">开户行：</label>--%>
				<%--<div class="controls">--%>
					<%--&lt;%&ndash;<input value="${agentInfo.bankName}" maxlength="70" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />&ndash;%&gt;--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<div class="control-left">--%>
				<%--<label class="control-label">开户行所在地：</label>--%>
				<%--<div class="controls">--%>
					<%--&lt;%&ndash;<input value="${agentInfo.bankProvinceName} ${agentInfo.bankCityName}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />&ndash;%&gt;--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="control-right">--%>
				<%--<label class="control-label">开户支行名称：</label>--%>
				<%--<div class="controls">--%>
					<%--&lt;%&ndash;<input value="${agentInfo.bankBranchName}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />&ndash;%&gt;--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<div class="control-left">--%>
				<%--<label class="control-label">银行账户名：</label>--%>
				<%--<div class="controls">--%>
					<%--&lt;%&ndash;<input value="${agentInfo.bankAccountName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />&ndash;%&gt;--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="control-right">--%>
				<%--<label class="control-label">银行账号：</label>--%>
				<%--<div class="controls">--%>
					<%--&lt;%&ndash;<input value="${agentInfo.bankAccountCode}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />&ndash;%&gt;--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<h3>图片信息</h3>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">身份证（个人信息页）：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${agentInfoChange.idcardFaceImage}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">身份证（国徽页）：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${agentInfoChange.idcardBackImage}">
					</p>
				</div>
			</div>
		</div>

		<%--<div class="control-group">
			<label class="control-label">合作协议：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfoChange.agreementFile}">
				</p>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">营业执照：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfoChange.busiImage}">
				</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">税务登记证：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfoChange.taxRegImage}">
				</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfoChange.orgInstImage}">
				</p>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">开户许可证：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfoChange.orgInstImage}">
				</p>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">开户许可证：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfoChange.otherGenaImage}">
				</p>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="agent:agent:agentInfo:edit">
			<input id="btnCheck" class="btn" type="button" value="审核通过" onclick="doCheck('NORMAL')"/>
			<input id="btnReject" class="btn" type="button" value="审核驳回" onclick="doCheck('REJECT')"/>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>