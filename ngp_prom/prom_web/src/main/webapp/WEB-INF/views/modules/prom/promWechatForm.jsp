<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/prom/promMerchantManage/">商户列表</a></li>
		<li class="active"><a href="${ctx}/prom/promWechat/form?weId=${promWechat.weId}">微信用户<shiro:hasPermission name="prom:promWechat:edit"></shiro:hasPermission><shiro:lacksPermission name="prom:promWechat:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="promWechat" action="${ctx}/prom/promWechat/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<div class="control-left">
				<label class="control-label">用户openID(微信号)：</label>
				<div class="controls">
					<input value="${promWechat.openid}" readonly
						   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<div class="control-left">
					<label class="control-label">昵称：</label>
					<div class="controls">
						<input value="${promWechat.nickname}" readonly
							   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
					</div>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">头像：</label>
				<div class="controls">
					<img src="${promWechat.headimgurl}" readonly
						 style="border: 0px; background-color: #fff; padding-top: 3px;padding-right: 165px;" width="40" height="40"/>
				</div>
			</div>
			<div class="control-right">
				<div class="control-left">
					<label class="control-label">Unionid：</label>
					<div class="controls">
						<input value="${promWechat.unionid}" readonly
							   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
					</div>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">公众号APPID：</label>
				<div class="controls">
					<input value="${promWechat.appId}" readonly
						   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<div class="control-left">
					<label class="control-label">是否已关注：</label>
					<div class="controls">
						<c:choose>
							<c:when test="${promWechat.subscribe == '1'}">
								<input value="已关注" readonly
									   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
							</c:when>
							<c:otherwise>
								<input value="未关注" readonly
									   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">性别：</label>
				<div class="controls">
					<c:choose>
						<c:when test="${promWechat.sex == '1'}">
							<input value="男" readonly
						   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
						</c:when>
						<c:otherwise>
							<input value="女" readonly
						   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="control-right">
				<div class="control-left">
					<label class="control-label">语言：</label>
					<div class="controls">
						<c:choose>
							<c:when test="${promWechat.language == 'zh_CN'}">
								<input value="中文" readonly
									   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
							</c:when>
							<c:otherwise>
								<input value="外语" readonly
									   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">城市：</label>
				<div class="controls">
					<input value="${promWechat.city}" readonly
						   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<div class="control-left">
					<label class="control-label">省份：</label>
					<div class="controls">
						<input value="${promWechat.province}" readonly
							   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
					</div>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">国家：</label>
				<div class="controls">
					<input value="${promWechat.country}" readonly
						   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<div class="control-left">
					<label class="control-label">关注时间：</label>
					<div class="controls">
						<input value="${promWechat.subscribeTime}" readonly
							   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
					</div>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">操作人：</label>
				<div class="controls">
					<input value="${promWechat.createAuthor}" readonly
						   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<div class="control-left">
					<label class="control-label">创建时间：</label>
					<div class="controls">
						<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium"
							   value="<fmt:formatDate value="${promWechat.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							   />
					</div>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="control-left">
				<label class="control-label">修改人：</label>
				<div class="controls">
					<input value="${promWechat.updateAuthor}" readonly
						   style="border: 0px; background-color: #fff; padding-top: 3px;"/>
				</div>
			</div>
			<div class="control-right">
				<div class="control-left">
					<label class="control-label">修改时间：</label>
					<div class="controls">
						<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium "
							   value="<fmt:formatDate value="${promWechat.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							   />
					</div>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>