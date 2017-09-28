<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        //用于验证
        var flg = true;

        $(document).ready(function() {

        });

        //验证搜索条件
        var validateFrom = {
            validate: function(form){
                form.submit();
            }
        }

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/prom/promMerchantManage/">商户列表</a></li>
		<shiro:hasPermission name="prom:promMerchantManage:edit"><li><a href="${ctx}/prom/promMerchantManage/form">商户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="promMerchantManage" action="${ctx}/prom/promMerchantManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>商户编码：</label>
                <form:input path="merchantId" onchange="checkValue(this.value)" htmlEscape="false" maxlength="26" style="width:256px;" class="input-medium required"/>
            </li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
            <li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
            <li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>商户编码</th>
                <th>商户名称</th>
                <th>商户性质</th>
                <th>注册来源</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>修改人</th>
                <th>修改时间</th>
				<shiro:hasPermission name="prom:promMerchantManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="promMerchantManage">
			<tr>
                <td>${promMerchantManage.merchantId}</td>
                <td>${promMerchantManage.merchantName}</td>
                <td>${promMerchantManage.merchantType}</td>
                <td>
					<c:if test="${promMerchantManage.merSource == '微信'}">
						<c:choose>
							<c:when test="${promMerchantManage.wechatNo != '' and promMerchantManage.wechatNo != ''}">
								<a href="${ctx}/prom/promWechat/form?openid=${promMerchantManage.wechatNo}" >${promMerchantManage.merSource}</a>
							</c:when>
							<c:otherwise>
								${promMerchantManage.merSource}
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${promMerchantManage.merSource != '微信'}">
						${promMerchantManage.merSource}
					</c:if>
				</td>
                <td>${promMerchantManage.operator}</td>
                <td><fmt:formatDate value="${promMerchantManage.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${promMerchantManage.updateAuthor}</td>
                <td><fmt:formatDate value="${promMerchantManage.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="prom:promMerchantManage:edit"><td>
    				<a href="${ctx}/prom/promMerchantManage/form?merchantId=${promMerchantManage.merchantId}">修改</a>
                    &nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="${ctx}/prom/promAccountInfo/form?merchantId=${promMerchantManage.merchantId}">绑定银行卡</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>