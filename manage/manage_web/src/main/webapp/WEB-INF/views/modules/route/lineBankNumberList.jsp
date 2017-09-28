<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/sys/validation.jsp"%>
<html>
<head>
	<title>联行号管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//联动
		$(function(){
			
			   //选择分类
				function showLocation(province , city , town) {
					var title	= ['选择省份' , '选择城市' ];
					$.each(title , function(k , v) {
						title[k]	= '<option value="">'+v+'</option>';
					})
					
					$("#provinceCode,#cityCode").select2();
					$('#provinceCode').change(function() {
						$('#cityCode').empty();
						$('#cityCode').append(title[1]);
						var _province_id = $('#provinceCode').val();
						if(_province_id!=''){
							get_cate_list('cityCode' , $('#provinceCode').val(),"2");
						}
						$('#cityCode').change()
					})
					get_cate_list('provinceCode' , '',"1");	
				}
				showLocation();
			    function get_cate_list(el_id , loc_id,role) {
					var el	= $('#'+el_id); 
					$.ajax({
				           type: "GET",
				           url: "${ctx}/route/city/select",
						   data: {'id':loc_id,'role':role},
						   dataType: 'jsonp',
			               jsonp:'callback',
			               async:false,
				           success: function(data){
				              //console.log(data);
							 $.each(data , function(k , v) {
									var option	= '<option value="'+v.id+'"  >'+v.name+'</option>';
									el.append(option);
							})
						   },
			            error: function(){
			                console.log("请求失败!");
			            }
				    });
				}
			    	$("#provinceCode").find("option[value='"+$('#provinceCodeTemp').val()+"']").attr("selected",true);
			    	showLocation();
					var province = $('#provinceCode').val();
			    	get_cate_list('cityCode' , province,"2");
			    	$("#cityCode").find("option[value='"+$('#cityCodeTemp').val()+"']").attr("selected",true);
			    	showLocation();
			})
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/route/lineBankNumber/">联行号列表</a></li>
		<shiro:hasPermission name="route:lineBankNumber:edit"><li><a href="${ctx}/route/lineBankNumber/form">联行号添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="lineBankNumber" action="${ctx}/route/lineBankNumber/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="provinceCodeTemp" name="provinceCodeTemp" type="hidden" value="${provinceCodeTemp}"/>
		<input id="cityCodeTemp" name="cityCodeTemp" type="hidden" value="${cityCodeTemp}"/>
		<ul class="ul-form"  >
			 <li><label>所属银行：</label>
		    	<form:select id="bankNo" path="bankNo" class="input-medium" >
		    	    <option selected value="" >请选择</option>
				    <form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false" />
			    </form:select>
	        </li>
			 <li><label>所在省份：</label>
		    	<form:select id="provinceCode" path="provinceCode" class="input-medium">
		    	    <form:option value="" label="选择省份"/>
			    </form:select>
	        </li>

			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<form:select id="cityCode" path="cityCode"  class="input-medium" >
					<option selected value="">选择城市</option>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select id="status" path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getEnumList('commonStatus')}" itemLabel="name" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="display:none">id</th>
				<th>银行名称</th>
				<th>省份</th>
				<th>城市</th>
				<th>开户银行名称</th>
				<th>联行号</th>
				<th>状态</th>
				<th>修改人</th>
				<shiro:hasPermission name="route:lineBankNumber:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="lineBankNumber"  >
			<tr>
				<td style="display:none">${lineBankNumber.id}</td>
				<td>${lineBankNumber.bankName}</td>
				<td>${lineBankNumber.provinceName}</td>
				<td>${lineBankNumber.cityName}</td>
				<td>${lineBankNumber.openBankName}</td>
				<td>${lineBankNumber.associateLineNumber}</td>
				<td>${lineBankNumber.status}</td>
				<td>${lineBankNumber.updateName}</td>
				<shiro:hasPermission name="route:lineBankNumber:edit"><td>
					<c:if test="${lineBankNumber.status == '启用'}">
						<a href="${ctx}/route/lineBankNumber/status?id=${lineBankNumber.id}&status=DISABL" onclick="return confirmx('确认要禁用吗？', this.href)">禁用</a>
					</c:if>
					<c:if test="${lineBankNumber.status == '禁用'}">
						<a href="${ctx}/route/lineBankNumber/status?id=${lineBankNumber.id}&status=ENABLE" onclick="return confirmx('确认要启用吗？', this.href)">启用</a>
					</c:if>
    				<a href="${ctx}/route/lineBankNumber/details?id=${lineBankNumber.id}">详情</a>
					<a style="cursor:pointer;" class="checkPass" value-url="${ctx}/route/lineBankNumber/form?id=${lineBankNumber.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>