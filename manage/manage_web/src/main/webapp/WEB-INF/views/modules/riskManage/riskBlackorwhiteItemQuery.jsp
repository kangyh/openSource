<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>黑名单明细查询</title>
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
				var blackItemValue = $("#blackItemValue").val().trim();
				if(!checkSafe(blackItemValue)){
					alertx("元素值包含非法字符!");
					return ;
				}else if(blackItemValue.length>32){
					alertx("元素值长度过长!");
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
			$("#status").find("option").removeAttr("selected");
			$("#status").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#status option[selected]").text());
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
		function checkDelete(blackItemId) {
			if( !confirm("确认要删除这条数据？") ){
		    	return false;
		    }
			loading('正在提交，请稍等...');
			$.ajax({
				url:"${ctx}/risk/riskBlackorwhite/view/deleteItem",
				type:"post",
				cache:false,
				timeout:10000,
				data:{
					"blackItemId":blackItemId
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
			$("#blackItemValue_qa").val(   $("#blackItemValue").val().trim()    );
			$("#beginCreTime_qa").val(   $("#beginCreTime").val()    );
			$("#endCreTime_qa").val(   $("#endCreTime").val()    );
			$("#beginUpdTime_qa").val(   $("#beginUpdTime").val()    );
			$("#endUpdTime_qa").val(   $("#endUpdTime").val()    );
			$("#status_qa").val(   $("#status").val()    );
			$("#saveInitForm").submit();
		}
		function onImport() {
			$("#blackItemValue_up").val(   $("#blackItemValue").val().trim()    );
			$("#beginCreTime_up").val(   $("#beginCreTime").val()    );
			$("#endCreTime_up").val(   $("#endCreTime").val()    );
			$("#beginUpdTime_up").val(   $("#beginUpdTime").val()    );
			$("#endUpdTime_up").val(   $("#endUpdTime").val()    );
			$("#status_up").val(   $("#status").val()    );
			$("#importForm").submit();
		}
		function updateInit(blackItemId) {
			$("#blackItemId").val(blackItemId);
			$("#blackItemValue_qu").val(   $("#blackItemValue").val().trim()    );
			$("#beginCreTime_qu").val(   $("#beginCreTime").val()    );
			$("#endCreTime_qu").val(   $("#endCreTime").val()    );
			$("#beginUpdTime_qu").val(   $("#beginUpdTime").val()    );
			$("#endUpdTime_qu").val(   $("#endUpdTime").val()    );
			$("#status_qu").val(   $("#status").val()    );
			$("#updateInitForm").submit();
		}
		function returnFristWebPage() {
			$("#searchForm2").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="###">
				<c:forEach items="${enumMap.riskBwType}" var="wStatus">
					<c:if test="${rbwInfo.type eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>-
				<c:forEach items="${enumMap.riskBwCategory}" var="wStatus">
					<c:if test="${rbwInfo.category eq wStatus.value}">
						${wStatus.name}
					</c:if>
				</c:forEach>-
				${rbwInfo.name}明细-查询
			</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="riskBlackorwhiteItem" action="${ctx}/risk/riskBlackorwhite/view/itemlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="blackId" name="blackId" type="hidden" value="${riskBlackorwhiteItem.blackId }"/>
		<input id="exportParam" name="exportParam" type="hidden" value=""/>
		<ul class="ul-form">
			<li><label>元素值：</label>
				<form:input id="blackItemValue" path="blackItemValue"  htmlEscape="false" value="${riskBlackorwhiteItem.blackItemValue}"  style="width:256px;" class="input-medium required"/>
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
			<li><label>创建时间：</label>
				<input id="beginCreTime" name="beginCreTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskBlackorwhiteItem.beginCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endCreTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endCreTime" name="endCreTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskBlackorwhiteItem.endCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginCreTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>最后更新时间：</label>
				<input id="beginUpdTime" name="beginUpdTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskBlackorwhiteItem.beginUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endUpdTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endUpdTime" name="endUpdTime" type="text" readonly="readonly" class="input-medium Wdate"
					value="<fmt:formatDate value="${riskBlackorwhiteItem.endUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginUpdTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onAdd()" value="新建"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onImport()" value="批量导入"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="returnFristWebPage()" value="返回"/></li>
			<!-- <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li> -->
			<li class="clearfix"></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
	</form:form>

	<form:form id="importForm"  action="${ctx}/risk/riskBlackorwhite/view/readyUploadFile" method="post"  >
				<input  name="blackId" type="hidden" value="${riskBlackorwhiteItem.blackId }"/>
				<input type="hidden" name="blackItemValue_q" id="blackItemValue_up" value=""/>
				<input type="hidden" name="beginCreTime_q" id="beginCreTime_up" value=""/>
				<input type="hidden" name="endCreTime_q" id="endCreTime_up" value=""/>
				<input type="hidden" name="beginUpdTime_q" id="beginUpdTime_up" value=""/>
				<input type="hidden" name="endUpdTime_q" id="endUpdTime_up" value=""/>
				<input type="hidden" name="status_q" id="status_up" value=""/>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
				<th>序号</th>
				<th>元素值</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>最后更新时间</th>
				<th>创建人</th>
				<th>最后更新人</th>
				<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="riskBWItemInfo" varStatus="seq">
				<tr>
					<td>${seq.index+1}</td>
					<td>${riskBWItemInfo.blackItemValue}</td>
					<td>
					<c:forEach items="${enumMap.riskBwStatus}" var="wStatus">
						<c:if test="${riskBWItemInfo.status eq wStatus.value}">
							${wStatus.name}
						</c:if>
					</c:forEach>
					</td>
					<td>
						<fmt:formatDate value="${riskBWItemInfo.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<fmt:formatDate value="${riskBWItemInfo.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${riskBWItemInfo.createAuthor}</td>
					<td>${riskBWItemInfo.updateAuthor}</td>
					<td>
							<shiro:hasPermission name="risk:riskBlackorwhite:edit">
								<a href="javascript:void(0)" onclick="updateInit('${riskBWItemInfo.blackItemId}')">编辑</a>
								<c:if test="${riskBWItemInfo.status eq 'ENABLE' }">
								<a href="javascript:void(0)" onclick="checkDelete('${riskBWItemInfo.blackItemId}')">删除</a>
								</c:if>
								
							</shiro:hasPermission>
					</td>			
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form id="updateInitForm"    action="${ctx}/risk/riskBlackorwhite/view/updateInit" method="post"  >
		<input type="hidden" name="blackItemId" id="blackItemId" value=""/>
		<input type="hidden" name="ifItem" id="ifItem" value="yes"/>
		<input type="hidden" name="blackItemValue_q" id="blackItemValue_qu" value=""/>
		<input type="hidden" name="beginCreTime_q" id="beginCreTime_qu" value=""/>
		<input type="hidden" name="endCreTime_q" id="endCreTime_qu" value=""/>
		<input type="hidden" name="beginUpdTime_q" id="beginUpdTime_qu" value=""/>
		<input type="hidden" name="endUpdTime_q" id="endUpdTime_qu" value=""/>
		<input type="hidden" name="status_q" id="status_qu" value=""/>
		<input type="hidden" name="blackId_q" id="blackId_qu" value="${riskBlackorwhiteItem.blackId }"/>
	</form>
	<form:form id="saveInitForm"  action="${ctx}/risk/riskBlackorwhite/view/saveInit" method="post"  >
		<input name="ifItem" value="yes" type="hidden"/>
		<input type="hidden" name="blackId_q" id="blackId_qa" value="${riskBlackorwhiteItem.blackId }"/>
		<input type="hidden" name="blackItemValue_q" id="blackItemValue_qa" value=""/>
		<input type="hidden" name="beginCreTime_q" id="beginCreTime_qa" value=""/>
		<input type="hidden" name="endCreTime_q" id="endCreTime_qa" value=""/>
		<input type="hidden" name="beginUpdTime_q" id="beginUpdTime_qa" value=""/>
		<input type="hidden" name="endUpdTime_q" id="endUpdTime_qa" value=""/>
		<input type="hidden" name="status_q" id="status_qa" value=""/>
	</form:form>
	<form:form id="searchForm2" modelAttribute="riskBlackorwhite" action="${ctx}/risk/riskBlackorwhite/view" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="1"/>
		<input id="pageSize" name="pageSize" type="hidden" value="10"/>
		<input id="searchFlag" name="searchFlag" type="hidden" value="1"/>
		<input type="hidden" name="blackId"  value="${riskBlackorwhite.blackId }"/>
		<input type="hidden" id="beginCreTime" name="beginCreTime" value="<fmt:formatDate value="${riskBlackorwhite.beginCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="endCreTime" name="endCreTime" value="<fmt:formatDate value="${riskBlackorwhite.endCreTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="beginUpdTime" name="beginUpdTime" value="<fmt:formatDate value="${riskBlackorwhite.beginUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="endUpdTime" name="endUpdTime" value="<fmt:formatDate value="${riskBlackorwhite.endUpdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" name="name"  value="${riskBlackorwhite.name }"/>
		<input type="hidden" name="type"  value="${riskBlackorwhite.type }"/>
		<input type="hidden" name="status"  value="${riskBlackorwhite.status }"/>
		<input type="hidden" name="productCode"  value="${riskBlackorwhite.productCode }"/>
		<input type="hidden" name="category"  value="${riskBlackorwhite.category }"/>
</form:form>
</body>
</html>