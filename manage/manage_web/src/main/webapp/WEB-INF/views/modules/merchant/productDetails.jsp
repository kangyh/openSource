<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>单表管理</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript">
    $(document).ready(function() {
      if ($("#auditS").val() == "AUDREJ"){
        $("#objectionDiv").show();
      }else{
        $("#objectionDiv").hide();
      }
    });
  </script>
</head>
<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/merchant/product/">产品列表</a></li>
        <li class="active"> <a href="${ctx}/merchant/product/details?id=${product.id}">产品信息</a></li>
    </ul><br/>
    <form:form  modelAttribute="product"  method="post" class="form-horizontal">
        <sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">产品编码：</label>
            <div class="controls">
                    ${product.code}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">业务类型：</label>
            <div class="controls">
                    ${product.businessType}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">交易类型：</label>
            <div class="controls">
                    ${product.trxType}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">产品名称：</label>
            <div class="controls">
                    ${product.name}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注：</label>
            <div class="controls">
                    ${product.remark}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">创建人：</label>
            <div class="controls">
                   ${product.createName}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">创建时间：</label>
            <div class="controls">
                <fmt:formatDate value="${product.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
        <div class="control-group" style="display: none">
          <label class="control-label">审核状态：</label>
          <div class="controls">
              ${product.auditStatus}
              <input type="hidden" name="auditS" id="auditS" class="input-xlarge required" value="${product.auditStatus}">
          </div>
        </div>
        <div id="objectionDiv" class="control-group" >
          <label class="control-label">拒绝理由：</label>
          <div class="controls">
           <%-- <form:input id="objection" path="objection" htmlEscape="false" maxlength="50" class="input-xlarge"/>--%>
              ${product.objection}
          </div>
        </div>
        <div class="form-actions">
          <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form:form>
</body>
</html>