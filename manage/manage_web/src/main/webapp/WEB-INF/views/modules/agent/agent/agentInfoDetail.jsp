<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商信息管理管理</title>
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

            var status='${agentInfo.agentStatus}';
            if(!(status=='草稿' || status=='审核拒绝')){  //只有草稿状态才可以提交审核
           $('#verify').removeAttr('onclick').hide();
			}

        });
        function verifyInfo() {
          $('#inputForm').submit();
        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li><a href="${ctx}/agent/agent/agentInfo/">代理商列表</a></li>--%>
		<%--&lt;%&ndash;<li class="active"><a href="${ctx}/agent/agent/agentInfo/form?id=${agentInfo.id}">代理商信息管理<shiro:hasPermission name="agent:agent:agentInfo:edit">${not empty agentInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="agent:agent:agentInfo:edit">查看</shiro:lacksPermission></a></li>&ndash;%&gt;--%>
		<%--<li class="active"><a href="">代理商详情</a></li>--%>
	<%--</ul><br/>--%>
	<form:form id="inputForm" modelAttribute="agentInfo" class="form-horizontal" action="${ctx}/agent/agent/agentInfo/verify">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<h3>基本信息</h3>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">当前状态：</label>
				<div class="controls">
					<input value="${agentInfo.agentStatus}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>

		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">代理商名称：</label>
				<div class="controls">
					<input value="${agentInfo.agentName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">代理商名称简称：</label>
				<div class="controls">
					<input value="${agentInfo.shortName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">负责人姓名：</label>
				<div class="controls">
					<input value="${agentInfo.headName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
<%--					<form:input path="headName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>--%>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">负责人手机号：</label>
				<div class="controls">
					<input value="${agentInfo.headPhone}" maxlength="11" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">负责人身份证号：</label>
				<div class="controls">
					<input value="${agentInfo.headIdcard}" maxlength="18" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">身份证有效期：</label>
				<div class="controls">
					<input value="${agentInfo.headIdcardExpiry}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱：</label>
			<div class="controls">
				<input value="${agentInfo.email}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">代理商类型：</label>
				<div class="controls">
					<input value="${agentInfo.agentType}" maxlength="12" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">有效期：</label>
				<div class="controls">
					<input value="${agentInfo.agentExpiry}" maxlength="10" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">加入日期：</label>
				<div class="controls">
					<input value="<fmt:formatDate value="${agentInfo.createTime}" pattern="yyyy年MM月dd日"/>" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">最新修改日期：</label>
				<div class="controls">
					<input value="<fmt:formatDate value="${agentInfo.updateTime}" pattern="yyyy年MM月dd日"/>" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<h3>企业信息</h3>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人姓名：</label>
				<div class="controls">
					<input value="${agentInfo.legalName}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人手机号：</label>
				<div class="controls">
					<input value="${agentInfo.legalPhone}" maxlength="11" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">法人身份证号：</label>
				<div class="controls">
					<input value="${agentInfo.legalIdcard}" maxlength="18" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">法人身份证有效期：</label>
				<div class="controls">
					<fmt:parseDate value="${agentInfo.legalIdcardExpiry}" var="date1"></fmt:parseDate>
					<fmt:formatDate value="${date1 }" pattern="yyyy 年 MM 月 dd 日"/>

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
					<input value="${agentInfo.busiType}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">营业证照编号：</label>
				<div class="controls">
					<input value="${agentInfo.busiCode}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">营业证照有效期：</label>
				<div class="controls">
					<fmt:parseDate value="${agentInfo.busiExpiry}" var="date1"></fmt:parseDate>
					<fmt:formatDate value="${date1}" pattern="yyyy 年 MM 月 dd 日"/>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">注册资金：</label>
				<div class="controls">
					<input value="${agentInfo.regAmount}" maxlength="50" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
				</div>
			</div>
		</div>
		<c:if test="${agentInfo.busiType=='普通'}">
			<div class="control-group">
				<div class="control-left">
					<label class="control-label">组织机构代码证：</label>
					<div class="controls">
						<input value="${agentInfo.orgInstCode}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
				<div class="control-right">
					<label class="control-label">税务登记证：</label>
					<div class="controls">
						<input value="${agentInfo.taxRegCode}" maxlength="30" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
					</div>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">所在地：</label>
			<div class="controls">
				<input value="${agentInfo.addrProvinceName} ${agentInfo.addrCityName} ${agentInfo.addrCountryName} ${agentInfo.addrDetail}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">代理区域：</label>
			<div class="controls">
				<input value="${agentInfo.agentAreaAddr}" maxlength="100" readonly style="border:0px;background-color:#fff;padding-top: 3px;" />
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
						<img src="${agentInfo.idcardFaceImage}">
					</p>
				</div>
			</div>
			<div class="control-right">
				<label class="control-label">身份证（国徽页）：</label>
				<div class="controls">
					<a class="popA">图片</a>
					<p class="pop_img">
						<span>X</span>
						<img src="${agentInfo.idcardBackImage}">
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
					<img src="${agentInfo.agreementFile}">
				</p>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">营业执照：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfo.busiImage}">
				</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">税务登记证：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfo.taxRegImage}">
				</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfo.orgInstImage}">
				</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户许可证：</label>
			<div class="controls">
				<a class="popA">图片</a>
				<p class="pop_img">
					<span>X</span>
					<img src="${agentInfo.otherGenaImage}">
				</p>
			</div>
		</div>
		<div class="form-actions">
			<input id="verify" class="btn" type="button" value="提交审核" onclick="verifyInfo()"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>