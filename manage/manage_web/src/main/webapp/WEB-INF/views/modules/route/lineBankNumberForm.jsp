<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联行号管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
		//联动
		$(function(){
   		//选择分类
		function showLocation(flag) {
			var title	= ['选择省份' , '选择城市'];
			$.each(title , function(k , v) {
				title[k]	= '<option value="">'+v+'</option>';
			})
			$("#provinceCode,#cityCode,#openBankCode").select2();
			$('#provinceCode').change(function() {
				$('#cityCode').empty();
				$('#cityCode').append(title[1]);
				var _province_id = $('#provinceCode').val();
				if(_province_id!=''){
					get_cate_list('cityCode' , $('#provinceCode').val(),"2");
				}
				$('#cityCode').change()
			})
			$('#cityCode').change(function() {
				var _city_id = $('#cityCode').val();
				$('input[name=location_id]').val(_city_id);
			})
			
			if(flag){
				get_cate_list('provinceCode' , '',"1");	
			}
		}
		showLocation(true);
	    function get_cate_list(el_id , loc_id,role,bankNo) {
			var el	= $('#'+el_id);
			var bankNo = $("#bankNo").val();
			$.ajax({
				   async: false, 
		           type: "GET",
		           url: "${ctx}/route/city/select",
				   data: {'id':loc_id,'role':role,'bankNo':bankNo},
				   dataType: 'jsonp',
	               jsonp:'callback',
		           success: function(data){
		               //console.log(data);
					 $.each(data , function(k , v) {
							var option	= '<option value="'+v.id+'">'+v.name+'</option>';
							el.append(option);
					})
				   },
	            error: function(){
	                console.log("请求失败!");
	            }
		    });	
		}
		if($('#provinceCode1').val() != '' && $('#provinceCode1').val() != null){
		    $("#provinceCode").find("option[value='"+$('#provinceCode1').val()+"']").attr("selected",true);
	    	//showLocation();
			var province = $('#provinceCode').val();
			$('#cityCode').empty();
			if(province != ''){
	    		get_cate_list('cityCode' , province,"2");
			}
			if($('#cityCode1').val() != '' && $('#cityCode1').val() != null){
		    	$("#cityCode").find("option[value='"+$('#cityCode1').val()+"']").attr("selected",true);
		    	SelchangeOpenBank();
		    	if($("#openBankCode1").val()!= '' && $("#openBankCode1").val()!= null){
		    		$("#openBankCode").find("option[value='"+$("#openBankCode1").val()+"']").attr("selected",true);
			    }
			}
	    	showLocation(false);
	    }
	})    
		
		function SelchangeBank(a){
		    $("#bankName").val(a);
		    SelchangeOpenBank();    
		}
		
		function SelchangeProvince(a){
		    $("#provinceName").val(a);
		}
		
		function SelchangeCity(a){
		    $("#cityName").val(a);
		    SelchangeOpenBank();
		}
		
		function SelOpenBank(a){
            $("#openBankName").val(a.text);
            var bankNo = $("#bankNo").val();
            var city = $("#cityCode").val();
            $("#associateLineNumber").val(bankNo+city+a.value);
        }
		
		
		
		function SelchangeOpenBank(){
            var bank= $("#bankNo").val();
            var city= $("#cityCode").val();
            if(bank != '' && city != ''){
            	console.log(bank+"  city:"+city);
            	$.ajax({
                    async: false, 
                    type: "GET",
                    url: "${ctx}/route/city/getOpenBank",
                    data: {'city':city,'bankNo':bank},
                    dataType: 'json',
                    success: function(data){
                        //console.log(data);
                      $("#openBankCode").empty();
                      var optionDefault  = '<option value="">选择开户行</option>';
                      $('#openBankCode').append(optionDefault);
                      $.each(data , function(k , v) {
                             var option  = '<option value="'+v.id+'">'+v.name+'</option>';
                             $("#openBankCode").append(option);
                     })
                    },
	                 error: function(){
	                     console.log("请求失败!");
	                 }
	             }); 
            }
           /*  var openBank= $("#openBankCode").val();
			if(!reg.test(openBank)){
				$("#openBankCode").val("");
				$("#associateLineNumber").val(bank+city);
			}else{
				$("#associateLineNumber").val(bank+city+openBank);
			} */
		}
			
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/route/lineBankNumber?cache=1">联行号列表</a></li>
		<li class="active"><a href="${ctx}/route/lineBankNumber/form?id=${lineBankNumber.id}">联行号<shiro:hasPermission name="route:lineBankNumber:edit">${not empty lineBankNumber.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="route:lineBankNumber:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="lineBankNumber" action="${ctx}/route/lineBankNumber/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<input type="hidden" id="provinceCode1" value="${lineBankNumber.provinceCode}"/>
		<input type="hidden" id="cityCode1" value="${lineBankNumber.cityCode}"/>
		<input type="hidden" id="openBankCode1" value="${lineBankNumber.openBankCode}"/>
		<div class="control-group"  >
			<div class="controls" style="display: none" >
				<form:input path="bankName" id="bankName"  htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls" >
				<form:select id="bankNo" path="bankNo"  class="input-xlarge required" onchange="SelchangeBank(this.options[this.options.selectedIndex].text);" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getBankInfoList()}" itemLabel="bankName" itemValue="bankNo" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none"  >
			<div class="controls">
				<form:input path="provinceName" id="provinceName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份名称：</label>
			<div class="controls">
				<form:select id="provinceCode" path="provinceCode" class="input-xlarge required" onchange="SelchangeProvince(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="选择省份"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<div class="controls">
				<form:input path="cityName" id="cityName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市名称：</label>
			<div class="controls">
				<form:select path="cityCode" id="cityCode" class="input-xlarge required"  onchange="SelchangeCity(this.options[this.options.selectedIndex].text);">
					<form:option value="" label="选择城市"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">开户银行名称：</label>
			<div class="controls">
				<form:select path="openBankCode" id="openBankCode" class="input-xlarge required"  onchange="SelOpenBank(this.options[this.options.selectedIndex]);">
                    <form:option value="" label="选择开户行"/>
                </form:select>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<form:hidden path="openBankName" id="openBankName"  htmlEscape="false"/>
		<div class="control-group">
			<label class="control-label">联行号：</label>
			<div class="controls">
				<form:input id="associateLineNumber" path="associateLineNumber" htmlEscape="false" maxlength="12" class="input-xlarge required"  readonly="true" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="route:lineBankNumber:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>