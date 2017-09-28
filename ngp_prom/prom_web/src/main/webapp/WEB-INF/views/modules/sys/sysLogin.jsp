<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
      html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
      .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
        	-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
      .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
      .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
      .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
      .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
      .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
      label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
	  #_ocx_password_str embed{
		  width: 313px;
		  height: 51px;
		  border: solid 1px #ccc;
		  border-radius: 4px;
	  }
	  #_ocx_password_str object{
		  width: 313px;
		  height: 51px;
		  border: solid 1px #ccc;
		  border-radius: 4px;
	  }
	  #_ocx_password_str div{
		  height: 0px;
	  }
    </style>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.8.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/crypto-js.js"></script>
	<script src="${pageContext.request.contextPath}/static/PassGuardCtrl.js"></script>
	<script type="text/javascript">
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
	<script type="text/javascript">
		var pgeditor = new $.pge({
			pgePath : "${downloadAddress}",// 控件下载目录，可以指定绝对路径，如"http://www.baidu.com/download/"
			pgeId : "_ocx_password",// 控件ID
			pgeEdittype : 0,// 控件显示类型,0(星号),1(明文)
			pgeEreg1 : "[\\s\\S]*",// 输入过程中字符类型限制，如"[0-9]*"表示只能输入数字
			pgeEreg2 : "[0-9a-zA-Z]{6,12}", // 输入完毕后字符类型判断条件，与pgeditor.pwdValid()方法对应
			pgeMaxlength : 20,// 允许最大输入长度
			pgeTabindex : 2,// tab键顺序
			pgeClass : "input-block-level",// 控件css样式    ocx_style
			pgeInstallClass : "ocx_style_two", // 针对安装或升级的css样式
			pgeOnkeydown : "FormSubmit()",// 回车键响应函数，需焦点在控件中才能响应
			tabCallback : "input2",// 火狐tab键回调函数,设置要跳转到的对象ID
			// windows10相关
			pgeWindowID : "password" + new Date().getTime() + 1,
			pgeRZRandNum : "${sKey}",
			pgeRZDataB : "${enStr}"
		});
		window.pgeCtrl = pgeditor;
		function FormSubmit(){
	        $("#manage_login").trigger("click");
	    }
		$(function(){
			$("#username").focus();
			$('#manageCode').keydown(function(e){
				if(e.keyCode==13){
					FormSubmit(); //处理事件
				}
			});
		})

	</script>
	<script type="text/javascript">
		// 获取随机因子，生成密文提交
		function setcurite() {
			var validateCode = $("#validateCode").val();
			if(validateCode == ""){
				$("#loginError").html("验证码为空");
				return;
			}
			var manageCode = $("#manageCode").val();
			if(manageCode == ""){
				$("#messageBox").removeClass("hide");
				$("#loginError").html("口令为空");
				return;
			}
			// 设置32位长度随机因子,用于AES密钥转换
			$.ajax({
				url : "${pageContext.request.contextPath}/srand_num?" + new Date().getTime(),// get_time()返回当前时间戳
				type : "GET",
				async: false,
				success : function(srand_num) {
					pgeditor.pwdSetSk(srand_num);// 设置32长度随机因子
				}
			});
			// 获得密文及账号
			var email = $("#login_name").val();
			var pwd = pgeditor.pwdResult();
			var network = pgeditor.machineNetwork();
			var disk = pgeditor.machineDisk();
			var cpu = pgeditor.machineCPU();
			$("#password").val(pwd);
			$("#network").val(network);
			$("#disk").val(disk);
			$("#cpu").val(cpu);
			$("#loginForm").submit();
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
		<label id="loginError" class="error">${message}</label>
	</div>
	</div>
	<h1 class="form-signin-heading">${fns:getConfig('productName')}</h1>
	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
		<label class="input-label" for="username">登录名</label>
		<input type="text" id="username" tabindex="1" name="username" class="input-block-level required" value="${username}">
		<label class="input-label" for="password">密码</label>
		<div id="_ocx_password_str">
			<script>pgeditor.generate();</script>
		</div>
		<c:if test="${isCheck == 'yes'}">
			<label class="input-label" for="manageCode">口令</label>
			<input type="text" id="manageCode" tabindex="3" name="manageCode" class="input-block-level required">
		</c:if>
		<input type="hidden" id="password" name="pwd" >
		<input type="hidden" id="network" name="network" >
		<input type="hidden" id="disk" name="disk" >
		<input type="hidden" id="cpu" name="cpu" >
		<c:if test="${isValidateCodeLogin}"><div class="validateCode">
			<label class="input-label mid" for="validateCode">验证码</label>
			<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
		</div></c:if>
		<input id="manage_login" class="btn btn-large btn-primary" onclick="setcurite();" type="button" value="登 录"  tabindex="4"/>&nbsp;&nbsp;
		<div id="themeSwitch" class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a>
			<ul class="dropdown-menu">
			  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
			</ul>
			<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
		</div>
		
	</form>
	<div class="footer">
		Copyright &copy; 2012-${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a> - Powered By <a href="https://mch.heepay.com" target="_blank">汇付宝</a> ${fns:getConfig('version')} 
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>