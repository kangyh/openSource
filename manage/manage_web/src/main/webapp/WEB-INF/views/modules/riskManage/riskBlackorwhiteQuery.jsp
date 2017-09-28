<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>黑名单查询</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<script type="text/javascript">
		String.prototype.trim = function() {
		  return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
		}
		//用于验证
		var flg = true;	
		
		$(document).ready(function() {
			$("#allRecord").click(function(){
				if($(this).attr("checked")){
					$(".exportParam").attr("checked",true);
				}else{
					$(".exportParam").attr("checked",false);
				}
				
			});
		});
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				var name_q = $("#name").val().trim();
				var blackId_q = $("#blackId").val().trim();
				var productCode_q = $("#productCode").val().trim();
				if(!checkSafe(name_q)){
					alertx("名单名称包含非法字符!");
					return ;
				}
				if(isNaN(blackId_q)){
					alertx("名单编码必须是数字!");
					return ;
				}
				if(!checkSafe(blackId_q)){
					alertx("名单编码包含非法字符!");
					return ;
				}
				if(!checkSafe(productCode_q)){
					alertx("产品编码包含非法字符!");
					return ;
				}
				var beginCreTime = $("#beginCreTime").val();
				var endCreTime = $("#endCreTime").val();
				var beginUpdTime = $("#beginUpdTime").val();
				var endUpdTime = $("#endUpdTime").val();

				if(beginCreTime=="" && endCreTime!="" || beginCreTime!="" && endCreTime==""
						|| beginUpdTime=="" && endUpdTime!="" || beginUpdTime!="" && endUpdTime==""  ){
					alertx("请正确设置查询时间范围!");
					return ;
				}
				if( beginCreTime!="" && endCreTime!=""){
					if(compareDate(convertDateToJoinStr(beginCreTime),
									convertDateToJoinStr(endCreTime)) > 0){
						alertx("起始日期不能大于结束日期!");
						return ;
					}
				}
				if( beginUpdTime!="" && endUpdTime!=""){
					if(compareDate(convertDateToJoinStr(beginUpdTime),
									convertDateToJoinStr(endUpdTime)) > 0){
						alertx("起始日期不能大于结束日期!");
						return ;
					}
				}
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
			//查询类型
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
			
			$("#type").find("option").removeAttr("selected");
			$("#type").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#type option[selected]").text());
			
			
			$("#category").find("option").removeAttr("selected");
			$("#category").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#category option[selected]").text());
			
			$("#productCode").find("option").removeAttr("selected");
			$("#productCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(3)").text($("#productCode option[selected]").text());

		}
		
		//验证是否是数字
		function checkNum(obj){
			if(isNaN(obj)){
				alertx("请输入数字！");
				flg = false;
				return;
			}else{
				flg = true;
				return;
			}
		}
		
		//文件导出
		function onExport(){
			var exportParam = "";
			$(".exportParam").each(function(){
				if ( $(this).attr("checked") ) {
					exportParam = exportParam + $(this).val() + "," ;
				}
			})
			$("#exportParam").val(exportParam);
	        var actionURL = $("#searchForm").attr("action");
	        $("#searchForm").attr("action",$("#searchForm").attr("action")+"export");
			validateFrom.validate($("#searchForm"));
			$("#searchForm").attr("action",actionURL);
	        
		}
		function checkSafe(a){
			if ( a.indexOf("script")>-1 || a.indexOf("\\")>-1 ){
				return false;
			}
			fibdn = new Array ("'",">","<","*","%","#","$","}","{","~","`","!","￥","/","?","&","^","(",")","=",";");
			i=fibdn.length;
			j=a.length;
			for (ii=0; ii<i; ii++) {
				for (jj=0; jj<j; jj++) {
					temp1=a.charAt(jj);
					temp2=fibdn[ii];
					if (temp1==temp2){
					return false;
					}
				}
			}
			
			return true;
		}
		function checkDelete(blackId) {
			if( !confirm("删除名单会删除该名单包含的所有名单明细，确认要删除这些记录？") ){
		    	return false;
		    }
			loading('正在提交，请稍等...');
			$.ajax({
				url:"${ctx}/risk/riskBlackorwhite/view/delete",
				type:"post",
				cache:false,
				timeout:10000,
				data:{
					"blackId":blackId
				},
				success:function(msg){
					closeLoading();
					alertx(msg, closed);
					if( msg.indexOf("成功")>-1 ){
						$("#searchForm").submit();
					}
					return ;
				},
				error:function(){
					closeLoading();
					alertx("删除失败", closed);
					return ;
				}
			});
		}
		
		function onAdd(  ) {
			$("#blackId_qa").val(   $("#blackId").val()    );
			$("#beginCreTime_qa").val(   $("#beginCreTime").val()    );
			$("#endCreTime_qa").val(   $("#endCreTime").val()    );
			$("#beginUpdTime_qa").val(   $("#beginUpdTime").val()    );
			$("#endUpdTime_qa").val(   $("#endUpdTime").val()    );
			
			$("#name_qa").val(   $("#name").val().trim()    );
			$("#type_qa").val(   $("#type").val()    );
			$("#status_qa").val(   $("#status").val()    );
			$("#productCode_qa").val(   $("#productCode").val()    );
			$("#category_qa").val(   $("#category").val()    );
			$("#saveInitForm").submit();
		}
		function updateInit(blackId) {
			$("#blackId_key").val(   blackId    );
			
			$("#blackId_qu").val(   $("#blackId").val()    );
			$("#beginCreTime_qu").val(   $("#beginCreTime").val()    );
			$("#endCreTime_qu").val(   $("#endCreTime").val()    );
			$("#beginUpdTime_qu").val(   $("#beginUpdTime").val()    );
			$("#endUpdTime_qu").val(   $("#endUpdTime").val()    );
			
			$("#name_qu").val(   $("#name").val().trim()    );
			$("#type_qu").val(   $("#type").val()    );
			$("#status_qu").val(   $("#status").val()    );
			$("#productCode_qu").val(   $("#productCode").val()    );
			$("#category_qu").val(   $("#category").val()    );
			
			$("#updateInitForm").submit();
		}
		function forwardItemlist(blackId) {
			$("#blackId_key2").val(   blackId    );
			$("#blackId_c").val(   $("#blackId").val()    );
			$("#beginCreTime_c").val(   $("#beginCreTime").val()    );
			$("#endCreTime_c").val(   $("#endCreTime").val()    );
			$("#beginUpdTime_c").val(   $("#beginUpdTime").val()    );
			$("#endUpdTime_c").val(   $("#endUpdTime").val()    );
			$("#name_c").val(   $("#name").val().trim()    );
			$("#type_c").val(   $("#type").val()    );
			$("#status_c").val(   $("#status").val()    );
			$("#productCode_c").val(   $("#productCode").val()    );
			$("#category_c").val(   $("#category").val()    );
			$("#itemListForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="###">黑白名单</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="riskBlackorwhite" action="${ctx}/risk/riskBlackorwhite/view/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="exportParam" name="exportParam" type="hidden" value=""/>
		<ul class="ul-form">
			<li><label>名单名称：</label>
				<form:input id="name" path="name"  htmlEscape="false"  style="width:256px;" class="input-medium required" maxlength="32"/>
			</li>
			<li><label>名单编码：</label>
				<form:input id="blackId" path="blackId"  htmlEscape="false"  style="width:256px;" class="input-medium required" maxlength="32"/>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-xlarge">
						<form:option value="" label="全部"/>
					<c:forEach items="${enumMap.riskBwStatus}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			
		</ul>
		<ul class="ul-form">
			<li><label>名单类型：</label>
				<form:select id="type" path="type" class="input-xlarge">
						<form:option value="" label="全部"/>
					<c:forEach items="${enumMap.riskBwType}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>名单数据类型：</label>
				<form:select id="category" path="category" class="input-xlarge">
						<form:option value="" label="全部"/>
					<c:forEach items="${enumMap.riskBwCategory}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>产品名称：</label>
				<%-- <form:input id="productCode" path="productCode"  htmlEscape="false"  style="width:256px;" class="input-medium required"/> --%>
				<%-- <form:select id="productCode" path="productCode" class="input-xlarge">
               		<form:option value="" label="全部"/>
					<c:forEach items="${enumMap.prodList}" var="wStatus">
						<form:option value="${wStatus.value}" label="${wStatus.name}"/>
					</c:forEach>
				</form:select> --%>
				<form:select id="productCode" path="productCode" class="input-xlarge required" >
					<form:option value="" label="全部"/>
					<form:options items="${fns:getProductList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>创建时间：</label>
				<input id="beginCreTime" name="beginCreTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskBlackorwhite.beginCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endCreTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreTime" name="endCreTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskBlackorwhite.endCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>最后更新时间：</label>
				<input id="beginUpdTime" name="beginUpdTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskBlackorwhite.beginUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endUpdTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endUpdTime" name="endUpdTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskBlackorwhite.endUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginUpdTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onAdd()" value="新建"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<!-- <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li> -->
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
				<th>序号</th>
				<th>名单名称</th>
				<th>名单编码</th>
				<th>产品名称</th>
				<th>名单类型</th>
				<th>名单数据类型</th>
				<th>状态</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>最后更新时间</th>
				<th>创建人</th>
				<th>最后更新人</th>
				<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="riskBWInfo" varStatus="seq">
				<tr>
					<td>${seq.index+1}</td>
					<td>${riskBWInfo.name}</td>
					<td>${riskBWInfo.blackId}</td>
					<td>
						<c:forEach items="${fns:getProductList()}" var="wStatus">
							<c:if test="${riskBWInfo.productCode eq wStatus.code}">${wStatus.name}</c:if>
						</c:forEach>
					</td>
					<td>
					<c:forEach items="${enumMap.riskBwType}" var="wStatus">
						<c:if test="${riskBWInfo.type eq wStatus.value}">
							${wStatus.name}
						</c:if>
					</c:forEach>
					</td>
					<td>
					<c:forEach items="${enumMap.riskBwCategory}" var="wStatus">
						<c:if test="${riskBWInfo.category eq wStatus.value}">
							${wStatus.name}
						</c:if>
					</c:forEach>
					</td>
					<td>
					<c:forEach items="${enumMap.riskBwStatus}" var="wStatus">
						<c:if test="${riskBWInfo.status eq wStatus.value}">
							${wStatus.name}
						</c:if>
					</c:forEach>
					</td>
					<td>${riskBWInfo.desc}</td>
					<td>
						<fmt:formatDate value="${riskBWInfo.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<fmt:formatDate value="${riskBWInfo.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${riskBWInfo.createAuthor}</td>
					<td>${riskBWInfo.updateAuthor}</td>
					<td>
							<shiro:hasPermission name="risk:riskBlackorwhite:edit">
								<a href="javascript:void(0)" onclick="updateInit('${riskBWInfo.blackId}')">编辑</a>
								<c:if test="${riskBWInfo.status eq 'ENABLE' }">
								<a href="javascript:void(0)" onclick="checkDelete('${riskBWInfo.blackId}')">删除</a>
								<a href="javascript:void(0)" onclick="forwardItemlist('${riskBWInfo.blackId}')">管理名单值</a>
								</c:if>
							</shiro:hasPermission>
							
					</td>			
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form id="updateInitForm"    action="${ctx}/risk/riskBlackorwhite/view/updateInit" method="post"  >
	
		<input type="hidden" name="blackId" id="blackId_key" value=""/>
		<input type="hidden" name="ifItem" id="ifItem" value="no"/>
		
		<input type="hidden" name="beginCreTime_q" id="beginCreTime_qu" value=""/>
		<input type="hidden" name="endCreTime_q" id="endCreTime_qu" value=""/>
		<input type="hidden" name="beginUpdTime_q" id="beginUpdTime_qu" value=""/>
		<input type="hidden" name="endUpdTime_q" id="endUpdTime_qu" value=""/>
		<input type="hidden" name="blackId_q" id="blackId_qu" value=""/>
		<input type="hidden" name="name_q" id="name_qu" value=""/>
		<input type="hidden" name="type_q" id="type_qu" value=""/>
		<input type="hidden" name="status_q" id="status_qu" value=""/>
		<input type="hidden" name="productCode_q" id="productCode_qu" value=""/>
		<input type="hidden" name="category_q" id="category_qu" value=""/>
	</form>
	<form:form id="saveInitForm"  action="${ctx}/risk/riskBlackorwhite/view/saveInit" method="post"  >
		<input name="ifItem" value="no" type="hidden"/>
		
		<input type="hidden" name="beginCreTime_q" id="beginCreTime_qa" value=""/>
		<input type="hidden" name="endCreTime_q" id="endCreTime_qa" value=""/>
		<input type="hidden" name="beginUpdTime_q" id="beginUpdTime_qa" value=""/>
		<input type="hidden" name="endUpdTime_q" id="endUpdTime_qa" value=""/>
		<input type="hidden" name="blackId_q" id="blackId_qa" value=""/>
		<input type="hidden" name="name_q" id="name_qa" value=""/>
		<input type="hidden" name="type_q" id="type_qa" value=""/>
		<input type="hidden" name="status_q" id="status_qa" value=""/>
		<input type="hidden" name="productCode_q" id="productCode_qa" value=""/>
		<input type="hidden" name="category_q" id="category_qa" value=""/>
	</form:form>
	<form:form id="itemListForm"  action="${ctx}/risk/riskBlackorwhite/view/itemlist" method="post"  >
	
		<input type="hidden" name="searchFlag"   value="2" />
		<input type="hidden" name="blackId" id="blackId_key2" value=""/>
		<input type="hidden" name="beginCreTime_c" id="beginCreTime_c" value=""/>
		<input type="hidden" name="endCreTime_c" id="endCreTime_c" value=""/>
		<input type="hidden" name="beginUpdTime_c" id="beginUpdTime_c" value=""/>
		<input type="hidden" name="endUpdTime_c" id="endUpdTime_c" value=""/>
		<input type="hidden" name="blackId_c" id="blackId_c" value=""/>
		<input type="hidden" name="name_c" id="name_c" value=""/>
		<input type="hidden" name="type_c" id="type_c" value=""/>
		<input type="hidden" name="status_c" id="status_c" value=""/>
		<input type="hidden" name="productCode_c" id="productCode_c" value=""/>
		<input type="hidden" name="category_c" id="category_c" value=""/>
	</form:form>
</body>
</html>