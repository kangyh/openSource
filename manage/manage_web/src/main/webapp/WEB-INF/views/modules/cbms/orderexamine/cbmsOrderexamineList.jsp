<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>通关申报订单审核</title>
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

        });
        function showpop(){
            parent.showThree_one();
            parent.changeThreeName_one("正在申报海关，请耐心等待");
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
            document.getElementById("examinesuces").action="${ctx}/cbms/CbmsOrderexamineController/examinefal/";
            $('#examinesuces').submit();
            }else{
                alert("请填写拒绝理由");
            }
        }
        function inputid(n){

            document.getElementById('orderInputId').value=n;
        }
        function deleteselect(){
            $("#searchForm").find("input[type='text']").val("");
            $("#declareType").find("option").removeAttr("selected");
            $("#declareType").find("option:eq(0)").attr("selected", "selected");
            $(".select2-chosen:eq(0)").text($("#declareType option[selected]").text());
        }
        $(function(){
            $("#messageBox").show();
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/cbms/CbmsOrderexamineController/">通关申报订单审核列表</a></li>
    <%--<shiro:hasPermission name="cbms:cbmsOrderSum:edit"><li><a href="${ctx}/cbms/cbmsOrderSum/form">导入文件查询添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="examinesuces"  action="${ctx}/cbms/CbmsOrderexamineController/examinesucces/" method="post"  class="form-horizontal" >
    <input id="pids" name="pids" type="hidden" value=""/>
    <input id="reasonhidden"  name="reasonhidden" type="hidden" value=""/>
    <input id="orderhidden"  name="orderhidden" type="hidden" value=""/>
</form:form>
<form:form id="searchForm" modelAttribute="cbmsOrderSum" action="${ctx}/cbms/CbmsOrderexamineController/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>商户编码：</label>
            <form:input path="merchantNo" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="value=value.replace(/[^\d]/g,'') "/>
        </li>
        <li><label>导入批次号：</label>
            <form:input path="importBatchNumber" htmlEscape="false" maxlength="64" class="input-medium" onkeyup="value=value.replace(/[^\d\a-z\A-Z]/g,'')"/>
        </li>
        <li><label>申报方式：</label>
            <form:select path="declareType" class="input-medium">
                <form:option value="" label="全部"/>
                <form:option value="1" label="文件上传"/>
                <form:option value="2" label="API接口"/>
            </form:select>
         </li>
        <li><label>导入日期：</label>
            <input id="beginImportTime" name="beginImportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${cbmsOrderSum.beginImportTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'#F{$dp.$D(\'endImportTime\')}'});"/> -
            <input id="endImportTime" name="endImportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${cbmsOrderSum.endImportTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,minDate:'#F{$dp.$D(\'beginImportTime\')}'});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="清空" onclick="deleteselect()"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message  content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>商户编码</th>
        <th>商户公司名称</th>
        <th>导入批次号</th>

        <th>总申报条数</th>
        <th>总申报金额</th>
        <th>手续费</th>
        <th>状态</th>
        <th>申报方式</th>
        <th>交易日期</th>
        	<shiro:hasPermission name="cbms:CbmsOrderexamineController:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cbmsOrderSum">
        <tr>
            <td>
                <c:out value="${cbmsOrderSum.merchantNo}"/>
            </td>
            <td>
                    <c:out value="${cbmsOrderSum.cbmsCompanyName}"/>
            </td>
            <td>
                <c:out value="${cbmsOrderSum.importBatchNumber}"/>
            </td>

            <td>
                    <c:out value="${cbmsOrderSum.declarationNumber}"/>
            </td>
            <td>
                <c:out value="${cbmsOrderSum.declarationMoney}"/>
            </td>
            <td>
                    <c:out value="${cbmsOrderSum.fee}"/>
            </td>
            <td>
                <c:out value="${cbmsOrderSum.comments}"/>
            </td>
            <td>
                <c:out value="${cbmsOrderSum.declareType}"/>
            </td>

            <td>
                <fmt:formatDate value="${cbmsOrderSum.importTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
                	<shiro:hasPermission name="cbms:CbmsOrderexamineController:edit"><td>

                         <c:choose>
                             <c:when test="${cbmsOrderSum.status =='5' }">
                                 <a href="${ctx}/cbms/cbmsOrderform/orderformlist?importBatchNumber=${cbmsOrderSum.importBatchNumber}">查看</a>
                                 <a href = "${ctx}/cbms/CbmsOrderexamineController/examinesuc?id=${cbmsOrderSum.orderInputId}" onclick = "document.getElementById('lightsucc').style.display='block';document.getElementById('fadefadesucc').style.display='block'">通过</a>
                                 <a href = "JavaScript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block';inputid(${cbmsOrderSum.orderInputId})">拒绝</a>
                             </c:when>
                             <c:when test="${cbmsOrderSum.status =='2' }">
                                 <a href="${ctx}/cbms/cbmsOrderform/orderformlist?importBatchNumber=${cbmsOrderSum.importBatchNumber}">查看</a>
                                 <a href = "JavaScript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block';inputid(${cbmsOrderSum.orderInputId})">拒绝</a>
                             </c:when>
                             <c:when test="${cbmsOrderSum.status =='1' }">
                                 <a href="${ctx}/cbms/cbmsOrderform/orderformlist?importBatchNumber=${cbmsOrderSum.importBatchNumber}&status=1">查看</a>
                                自动审核
                             </c:when>
                         </c:choose>
                    </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>



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
<div id="lightsucc" class="white_content" >

    <font size="4px" color="white">正在申报海关，请耐心等待</font>

</div>
<div id="pifade" class="black_overlay"></div>
<div id="fade" class="black_overlay"></div>
<div id="fadesucc" class="black_overlay"></div>

<div class="pagination">${page}</div>

</body>
</html>