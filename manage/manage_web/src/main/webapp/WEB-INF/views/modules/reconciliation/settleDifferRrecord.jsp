<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>对账管理</title>
	<meta name="decorator" content="default"/>
	<script language="JavaScript" src="${ctxStatic}/common/DateUtil.js"></script>
	<style>
        #main {
            margin: 50px;
        }
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkStatus = $("#checkStatus option[selected]").val();
			if(checkStatus=='Y'){
				$('#checkStatusY').css('display','block');
			}else{
				$('#checkStatusY').css('display','none');
			}
		});
		
		//验证搜索条件
		var validateFrom = {
			validate: function(form){
				
				var checkBathno = $("#checkBathno").val();
				var pattern = new RegExp("[`~!#$^@&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]") 
				if(pattern.test(checkBathno)){
					$("#messageBox").text("对账批次号输入不合法，请重新输入");
					return false;
				}
				
				var paymentIdid = $("#paymentIdid").val();
				if(isNaN(paymentIdid)){
					$("#messageBox").text("支付单号请输入数字类型");
					return false;
				}
				
				var transNoid = $("#transNoid").val();
				if(isNaN(transNoid)){
					$("#messageBox").text("交易订单号请输入数字类型");
					return false;
				}
				
				
				var bgTime = $("#beginCheckTime").val();
				var endTime = $("#endCheckTime").val();
				if(bgTime=="" && endTime!="" || bgTime!="" && endTime=="" ){
					$("#messageBox").text("请正确设置查询时间范围!");
					return ;
				}
				if( bgTime!="" && endTime!=""){
					if(compareDate(convertDateToJoinStr(bgTime),
									convertDateToJoinStr(endTime)) > 0){
						$("#messageBox").text("起始日期不能大于结束日期!");
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
			$("#pageNo").val(1);
			validateFrom.validate($("#searchForm"));
		}
		//清空
		function onClear(){
			$("#formBtn").submit();
		}
		
		
		
		//文件导出
		function onExport(){
            $("#searchForm").attr("action","${ctx}/reconciliation/SettleDifferRrecord/export");
			validateFrom.validate($("#searchForm"));
		}
		
		function Sel2change(field){
			$("#channelName").val(field);
		}
		
		
	 	function closeModal(modalID,bodyId){
        	$('#'+modalID).modal('hide');
        }
        
        function showImg(id) {
            $.ajaxSettings.async = false;
            $.post("${ctx}/reconciliation/SettleDifferRrecord/showImg", {"differId":id },
                function(data){
					if(data != "404"){
						$("#resultImg").attr("src",data);
                        $("#showMsg").text("");
					}else {
                        $("#resultImg").removeAttr("src");
						$("#showMsg").text("没有回执单");
					}
                });
            closeModal('fenpeiModal');
        }

        function closeModal(modalID){
            $('#fenpeiModal').modal('show');
            $(".pop").show();
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reconciliation/SettleDifferRrecord">对账差错记录列表</a></li>
	</ul>
	<form action="${ctx}/reconciliation/SettleDifferRrecord" method="post" id="formBtn"></form>
	<form:form id="searchForm" modelAttribute="settleDifferRecord" action="${ctx}/reconciliation/SettleDifferRrecord" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			 
			<li><label class="control-label">通道合作方：</label>
			    	<form:select id="channelCode" path="channelCode" class="input-xlarge" style="width:225px;" onchange="Sel2change(this.options[this.options.selectedIndex].text);">
						<form:option value="" label="-通道合作方-"/>
						<c:forEach items="${channelCode}" var="channelCodeFor">
							<form:option value="${channelCodeFor.channelCode}" label="${channelCodeFor.channelName}"/>
						</c:forEach>
					</form:select>
					<input type="hidden" name="channelName" id="channelName" class="input-xlarge required"> 
			</li>
			
			<li><label>支付通道类型：</label>
				<form:select id="channelType" path="channelType" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-支付通道类型-"/>
						<c:forEach items="${channelType}" var="channelTypeFor">
							<form:option value="${channelTypeFor.value}" label="${channelTypeFor.name}"/>
						</c:forEach>
				</form:select> 
			
			<li><label>交易类型：</label>
				<form:select id="transType" path="transType" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-选择交易类型-"/>
					<c:forEach items="${transType}" var="transTypeFor">
							<form:option value="${transTypeFor.value}" label="${transTypeFor.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			</ul>
			<ul class="ul-form">
			
			<li><label>差错类型：</label>
				<form:select id="differType" path="differType" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-选择差错类型-"/>
					<c:forEach items="${differType}" var="differTypeFor">
							<form:option value="${differTypeFor.value}" label="${differTypeFor.name}"/>
						</c:forEach>
				</form:select>
			</li>
			
			
			
			<li><label>差错状态：</label>
				<form:select id="handleMessage" path="handleMessage" style="width:225px;" class="input-xlarge">
					<form:option value="" label="-选择差错状态-"/>
					<c:forEach items="${handleMessage}" var="handleMessageFor">
							<form:option value="${handleMessageFor.value}" label="${handleMessageFor.name}"/>
						</c:forEach>
				</form:select>
			</li> 
			
			<li><label>支付单号：</label>
				<form:input path="paymentId" id="paymentIdid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入7位数字" style="width:210px;"/>
			</li> 
			
			</ul>
			<ul class="ul-form">
			<li><label>交易订单号：</label>
					<form:input path="transNo" id="transNoid" htmlEscape="false" maxlength="40" class="input-medium" placeholder="请输入20位数字" style="width:210px;"/>
			</li> 
			<li><label>对账批次号：</label>
					<form:input path="checkBathno" id="checkBathno" htmlEscape="false" maxlength="20" class="input-medium" placeholder="请输入查询条件" style="width:210px;"/>
			</li> 
			
			
			<li><label>差错日期：</label>
					<input id="beginCheckTime" name="beginCheckTime" type="text" readonly="readonly" maxlength="20"  style="width:95px;cursor: pointer" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferRecord.beginCheckTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input id="endCheckTime" name="endCheckTime" type="text" readonly="readonly" maxlength="20" style="width:95px;cursor: pointer" class="input-medium Wdate"
					value="<fmt:formatDate value="${settleDifferRecord.endCheckTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/></li>
			<li class="btns"><input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清除"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="onExport()" value="导出"/></li>
		</ul>
		<div id="messageBox" style="font-size: 15px; color: red;"></div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>通道合作方</th>
			<th>支付通道类型</th>
			<th>交易类型</th>
			<th>对账批次号</th>
			
			<th>差错日期</th>
			<th>差错类型</th>
			<th>支付单号</th>
			<th>交易订单号</th>
			
			<th>我方金额</th>
			<th>对方金额</th>
			
			<th>操作人</th>
			<th>操作日期</th>
			<th>分润标识</th>
			
			<th>差错状态</th>
			<shiro:hasPermission name="reconciliation:settleDifferRrecord:view"><th>操作</th></shiro:hasPermission>
			
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settleDifferRrecord">
			<tr>
				<td>${settleDifferRrecord.channelName}</td>
				<td>${settleDifferRrecord.channelType}</td>
				<td>${settleDifferRrecord.transType}</td>
				<td>${settleDifferRrecord.checkBathno}</td>
				
				<td><fmt:formatDate value="${settleDifferRrecord.errorDate}" type="both" pattern="yyyy-MM-dd"/></td>
				
				<td>${settleDifferRrecord.differType}</td>
				<td><a href="${ctx}/reconciliation/SettleDifferRrecord/more/${settleDifferRrecord.differId} ">${settleDifferRrecord.paymentId}</a></td>
				<td>${settleDifferRrecord.transNo}</td>
				
				<td>
					<fmt:formatNumber value="${settleDifferRrecord.requestAmount}" pattern="￥0.0000" />
				</td>
				
				<td>
					<fmt:formatNumber value="${settleDifferRrecord.successAmount}" pattern="￥0.0000" />
				</td>

				<td>${settleDifferRrecord.updateAuthor}</td>
				<td><fmt:formatDate value="${settleDifferRrecord.operationDate}" type="both" pattern="yyyy-MM-dd"/></td>
				<td>${settleDifferRrecord.isProfit}</td>
				
				<td>${settleDifferRrecord.handleMessage}</td>

				<shiro:hasPermission name="reconciliation:settleDifferRrecord:view"><td <c:choose>
			     		<c:when test="${settleDifferRrecord.BD=='YBD'}">
			     		style="background:#707c9b"	
			     		</c:when>
			     		<c:when test="${settleDifferRrecord.CD=='YCD'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		<c:when test="${settleDifferRrecord.QXCY=='YQXCY'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		<c:when test="${settleDifferRrecord.QT=='YQT'}">
			     		 style="background:#707c9b"
			     		</c:when>
			     		
			     		<c:when test="${settleDifferRrecord.BD=='BD'}">
			     		style="background:red"
			     		</c:when>
			     		<c:when test="${settleDifferRrecord.CD=='CD'}">
			     		 style="background:red"
			     		</c:when>
			     		<c:when test="${settleDifferRrecord.QXCY=='QXCY'}">
			     		 style="background:red"
			     		</c:when>
			     		<c:when test="${settleDifferRrecord.QT=='QT'}">
			     		 style="background:rebeccapurple"
			     		</c:when>
			     	</c:choose>>
				<c:choose>
					<c:when test="${settleDifferRrecord.BD=='BD'}">
						<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/reconciliation/SettleDifferRrecord/differences/addbd/${settleDifferRrecord.differId}">补单</a>
					</c:when>
					<c:when test="${settleDifferRrecord.BD=='YBD'}">
						已补单
					</c:when>

					<c:when test="${settleDifferRrecord.CD=='CD'}">
					<a style="cursor:pointer;" class="checkPass"   value-url="${ctx}/reconciliation/SettleDifferRrecord/differences/addcd/${settleDifferRrecord.differId}">撤单</a>
					</c:when>
					<c:when test="${settleDifferRrecord.CD=='YCD'}">
						已撤单
					</c:when>

					<c:when test="${settleDifferRrecord.QXCY=='QXCY'}">
					<a style="cursor:pointer;" class="checkPass"  value-url="${ctx}/reconciliation/SettleDifferRrecord/differences/agree/${settleDifferRrecord.differId}">取消差异</a>
					</c:when>
					<c:when test="${settleDifferRrecord.QXCY=='YQXCY'}">
						人工取消
					</c:when>

					<c:when test="${settleDifferRrecord.QT=='QT'}">
					<a id="qtAddId"  href="${ctx}/adjustMoney/settleDifferRrecord?adjust=1">跳去调账</a>
					</c:when>
					<c:when test="${settleDifferRrecord.QT=='YQT'}">
						<a onclick="showImg(${settleDifferRrecord.differId})" href="#" style="color:black">查看回执单</a>
					</c:when>

					<c:otherwise>
						--
					</c:otherwise>
				</c:choose>

				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

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
					<div id="showMsg" style="font-size: 15px; color: red;margin-left: 50%"></div>
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