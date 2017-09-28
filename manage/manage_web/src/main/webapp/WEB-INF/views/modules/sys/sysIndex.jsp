<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')}</title>
	<meta name="decorator" content="blank"/><c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>
    <c:if test="${tabmode eq '1'}"><link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />
    <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script></c:if>
	<style type="text/css">
		#main {padding:0;margin:0;} #main .container-fluid{padding:0 4px 0 6px;}
		#header {margin:0 0 8px;position:static;} #header li {font-size:14px;_font-size:12px;}
		#header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:26px;padding-left:33px;}
		#footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
		#footer, #footer a {color:#999;} #left{overflow-x:hidden;overflow-y:auto;} #left .collapse{position:static;}
		#userControl>li>a{/*color:#fff;*/text-shadow:none;} #userControl>li>a:hover, #user #userControl>li.open>a{background:transparent;}
		#ngp_cover{position: absolute;width:100%;height:100%;background-color:#000;opacity:0.28;z-index:1;display: none;}
		.ngp_pop_h,.ngp_pop_s,.ngp_pop_v{
		    font-size:16px;
		    color:#333;
		    margin-top:20px;
		    text-align: center;
		    border-bottom: solid 1px #d8d8d8;
		    padding-bottom: 20px;
		}
		.ngp_pop_botn{
		    background:#4287f5;
		    width:150px;
		    height:35px;
		    line-height: 35px;
		    text-align: center;
		    color: #fff;
		    margin: auto;
		    margin-top:40px;
		    font-size: 14px;
		    font-weight: 400;
		    cursor: pointer;
		}
		.ngp_pop_two,.ngp_pop_five,.ngp_pop_six{
		    position: absolute;
		    width:400px;
		    height:400px;
		    top:50%;
		    left:50%;
		    background-color:#fff;
		    z-index:8;
		    margin-left:-200px;
		    margin-top:-200px;
		    border-radius: 5px;
		    display: none;
		    border: solid 2px rgba(0,0,0,0.5);
		}
		.ngp_pop_two p,.ngp_pop_five p,.ngp_pop_six p{
		    margin-left: 40px;
		    margin-right: 40px;
		    margin-top: 25px;
		    font-size: 14px;
		    color: #333;
		}
		.ngp_pop_two h5,.ngp_pop_five h5,.ngp_pop_six h5{
		    margin-left: 40px;
		    margin-right: 40px;
		    margin-top: 10px;
		    font-size: 14px;
		    color: #333;
		}
		.ngp_pop_two h5 input,.ngp_pop_five h5 input,.ngp_pop_six h5 input{
		    border:1px solid #d8d8d8;
		    width:298px;
		    height:34px;
		    outline: none;
		    line-height: 36px;
		    padding-left: 10px;
		    font-size: 14px;
		    color: #333;
		 }
		.ngp_pop_four{position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
		    margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}

		.ngp_pop_foures{position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
			margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}

		.ngp_pop_fours{
			position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
			margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);
		}
		.ngp_pop_fours p{
			text-align: center;
		}
		.ngp_pop_fours p label{
			margin-right: 10px;
		}


		.ngp_pop_foursrate{
			position: absolute; width:500px;height:400px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
			margin-top:-150px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);
		}

		.ngp_pop_foursrate p span{
			width: 100px;
			font-size: 14px;
			display: inline-block;
			text-align: right;
			padding-right: 10px;

		}
		.rate_type{
			float:left;
			margin-top: 5px;
			font-size:14px;
			margin-right: 15px;
		}
		.ngp_pop_foursrate p label{
			margin-right: 10px;
		}

		.ngp_pop_foursrate p{
			margin-left: 20px;
		}

		.ngp_pop_seven{
			position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
			margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);
		}

		.ngp_pop_three{position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
		    margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}
		.ngp_pop_three_one{position: absolute; width:400px;height:80px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
			margin-top:-40px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}

		 .ngp_pop_threes{position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
		    margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}
	    .ngp_pop_three_ep{position: absolute; width:400px;height:220px;top:50%;left:50%;background-color:#fff;z-index:8;margin-left:-200px;
            margin-top:-110px;border-radius: 5px;display: none;border: solid 2px rgba(0,0,0,0.5);}
		.ngp_name_text,.status_changing{font-size: 16px;color: #333;text-align: center;margin-top: 30px;}
	    .bangk_number,.money_number,.account_name{font-size: 14px;color: #333;margin-top:20px;margin-left: 120px;}
	    .ngp_btn_kt{margin-top: 30px;text-align: center;}
	    .ngp_btn_kt span{background:#4287f5;width:120px; height:35px;line-height: 35px;text-align: center;color: #fff;
	        margin: auto;font-size: 14px;font-weight: 400; cursor: pointer;display: inline-block;margin-left: 10px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			var myctx = $("#myctx").val();
			sessionStorage["myctx"] = myctx;
			// <c:if test="${tabmode eq '1'}"> 初始化页签
			$.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: { 'height': $('#right').height() - tabTitleHeight },
                tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
            });//</c:if>
			// 绑定菜单单击事件
			$("#menu a.menu").click(function(){
				// 一级菜单焦点
				$("#menu li.menu").removeClass("active");
				$(this).parent().addClass("active");
				// 左侧区域隐藏
				if ($(this).attr("target") == "mainFrame"){
					$("#left,#openClose").hide();
					wSizeWidth();
					// <c:if test="${tabmode eq '1'}"> 隐藏页签
					$(".jericho_tab").hide();
					$("#mainFrame").show();//</c:if>
					return true;
				}
				// 左侧区域显示
				$("#left,#openClose").show();
				if(!$("#openClose").hasClass("close")){
					$("#openClose").click();
				}
				// 显示二级菜单
				var menuId = "#menu-" + $(this).attr("data-id");
				if ($(menuId).length > 0){
					$("#left .accordion").hide();
					$(menuId).show();
					// 初始化点击第一个二级菜单
					if (!$(menuId + " .accordion-body:first").hasClass('in')){
						$(menuId + " .accordion-heading:first a").click();
					}
					if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")){
						$(menuId + " .accordion-body a:first i").click();
					}
					// 初始化点击第一个三级菜单
					$(menuId + " .accordion-body li:first li:first a:first i").click();
				}else{
					// 获取二级菜单数据
					$.get($(this).attr("data-href"), function(data){
						if (data.indexOf("id=\"loginForm\"") != -1){
							alert('未登录或登录超时。请重新登录，谢谢！');
							top.location = "${ctx}";
							return false;
						}
						$("#left .accordion").hide();
						$("#left").append(data);
						// 链接去掉虚框
						$(menuId + " a").bind("focus",function() {
							if(this.blur) {this.blur()};
						});
						// 二级标题
						$(menuId + " .accordion-heading a").click(function(){
							$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
							if(!$($(this).attr('data-href')).hasClass('in')){
								$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
							}
						});
						// 二级内容
						$(menuId + " .accordion-body a").click(function(){
							$(menuId + " li").removeClass("active");
							$(menuId + " li i").removeClass("icon-white");
							$(this).parent().addClass("active");
							$(this).children("i").addClass("icon-white");
						});
						// 展现三级
						$(menuId + " .accordion-inner a").click(function(){
							var href = $(this).attr("data-href");
							if($(href).length > 0){
								$(href).toggle().parent().toggle();
								return false;
							}
							// <c:if test="${tabmode eq '1'}"> 打开显示页签
							return addTab($(this)); // </c:if>
						});
						// 默认选中第一个菜单
						$(menuId + " .accordion-body a:first i").click();
						$(menuId + " .accordion-body li:first li:first a:first i").click();
					});
				}
				// 大小宽度调整
				wSizeWidth();
				return false;
			});
			// 初始化点击第一个一级菜单
			$("#menu a.menu:first span").click();
			// <c:if test="${tabmode eq '1'}"> 下拉菜单以选项卡方式打开
			$("#userInfo .dropdown-menu a").mouseup(function(){
				return addTab($(this), true);
			});// </c:if>
			// 鼠标移动到边界自动弹出左侧菜单
			$("#openClose").mouseover(function(){
				if($(this).hasClass("open")){
					$(this).click();
				}
			});
			//打款认证取消按钮
			$("#ngp_cancel").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_four").hide();
			});
			//费率批量修改取消按钮
			$("#rate_cancel").on("click",function(){
				$("#ngp_cover").hide();
				$("#rate_div").hide();
			});
            //取消按钮
            $("#ngp_canceles").on("click",function(){
                $("#ngp_cover").hide();
                $(".ngp_pop_foures").hide();
            });
			//打款认证动态口令取消按钮
            $("#ngp_money_cancel").on("click",function(){
                $("#dynamic_word_money").val("");
                $("#ngp_cover").hide();
                $("#ngp_pop_money").hide();
            });
			//费率批量修改动态口令取消按钮
			$("#ngp_rate_cancel").on("click",function(){
				$("#dynamic_word_rate").val("");
				$("#ngp_cover").hide();
				$("#ngp_pop_rate").hide();
			});
			//动态口令取消按钮
			$("#ngp_dynamic_cancel").on("click",function(){
				$("#dynamic_word").val("");
				$("#ngp_cover").hide();
				$("#ngp_pop_fours").hide();
			});
			//打款认证动态口令取消按钮
			$("#ngp_rate_cancel").on("click",function(){
				$("#dynamic_word_rate").val("");
				$("#ngp_cover").hide();
				$("#ngp_pop_money").hide();
			});
			//充值登录密码取消按钮
			$("#user_cancel").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_two").hide();
			});
			//禁用账号取消按钮
			$("#status_cancel").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_five").hide();
			});
			//重置绑定手机号取消按钮
			$("#Unbundling_cancel").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_six").hide();
			});
			//弹窗确定按钮
			$("#ngp_ok").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_three").hide();
			});
			
			//反欺诈专用
			$("#ngp_ok_s").on("click",function(){
                $("#ngp_cover").hide();
                $(".ngp_pop_threes").hide();
                var url = $("#pbc_url").val();
                var id = $(".curholder").find("iframe").attr("id");
                document.getElementById(id).contentWindow.location.href=url;

            });

            $("#ngp_payes").on("click",function(){
                $("#ngp_cover").hide();
                $(".ngp_pop_foures").hide();
                alert(111);
                //var url = $("#pbc_url").val();
                //var id = $(".curholder").find("iframe").attr("id");
                //document.getElementById(id).contentWindow.location.href=url;

            });


			//弹窗确定按钮
			$("#ngp_ok_ep").on("click",function(){
                $("#ngp_cover").hide();
                $(".ngp_pop_three_ep").hide();
                var transNo = $("#transNo").val();
				var transType = $("#transType").val();
				var id = $(".curholder").find("iframe").attr("id");
                document.getElementById(id).contentWindow.location.href="${ctx}/payment/exceptionRecord/list?fromUrl=sysIndex&transNo="+transNo+"&transType="+transType;
				//window.location.href = "${ctx}/payment/exceptionRecord/list";
                //history.go(-1);
            });
			//资金平衡校验-确定按钮
			$("#ngp_confirm").on("click",function(){
				$("#ngp_cover").hide();
				$(".ngp_pop_seven").hide();
			});
			//批量修改确认按钮(ly)
			$("#rate_ok").on("click",function(){
				var checkedstr = $("#rateBanks").val();
				var rateId = $("#rateId").val();
				var chargeType = $("#chargeType").val();
				var maxFee = $("#maxFee").val();
				var minFee = $("#minFee").val();
				//阶梯(1)
				var rateFee = $("#rateFee").val();
				var rateRatio = $("#rateRatio").val();
				var chargeMax = $("#chargeMax").val();
				var chargeMin = $("#chargeMin").val();
				//阶梯(2)
				var rateFee2 = $("#rateFee2").val();
				var rateRatio2 = $("#rateRatio2").val();
				var chargeMax2 = $("#chargeMax2").val();
				var chargeMin2 = $("#chargeMin2").val();
				//阶梯(3)
				var rateFee3 = $("#rateFee3").val();
				var rateRatio3 = $("#rateRatio3").val();
				var chargeMax3 = $("#chargeMax3").val();
				var chargeMin3 = $("#chargeMin3").val();
				if(chargeType == ""){
					alert("计费类型不能为空");
					return;
				}
				if(chargeType == "COUNTD"){
					if(rateFee == ""){
						alert("手续费费用不能为空");
						return;
					}
				}else{
					if(rateRatio == ""){
						alert("手续费费用不能为空");
						return;
					}
					if(maxFee == ""){
						alert("最大值不能为空");
						return;
					}
					if(minFee == ""){
						alert("最小值不能为空");
						return;
					}
					if(parseFloat(maxFee) < parseFloat(minFee)){
						alert("手续费最大值必须大于等于手续费最小值");
						return;
					}
				}
				$("#ngp_cover").hide();
				$("#rate_div").hide();
				var somerateType = $("#somerateType").val();
				$.ajax({
					type: "GET",
					url: "${ctx}/merchant/merchantRateNew/editRateSome",
					data: {'checkedstr':encodeURI(checkedstr),'rateId':rateId,
						'chargeType':chargeType,'maxFee':maxFee,'minFee':minFee,'somerateType':somerateType,
						'chargeFee':rateFee,'chargeRatio':rateRatio,'chargeMax':chargeMax,'chargeMin':chargeMin,
						'chargeFee2':rateFee2,'chargeRatio2':rateRatio2,'chargeMax2':chargeMax2,'chargeMin2':chargeMin2,
						'chargeFee3':rateFee3,'chargeRatio3':rateRatio3,'chargeMax3':chargeMax3,'chargeMin3':chargeMin3},
					dataType: 'json',
					success: function(data){
						var id = $(".curholder").find("iframe").attr("id");
						document.getElementById(id).contentWindow.location.href = "${ctx}/merchant/merchantRateNew?rateId="+data;
					}
				});
			});

			//打款认证确认按钮(ly)
			$("#ngp_pay").on("click",function(){
				var id = $("#bank_id").val();
				var amount = $("#money_num").text();
				$.ajax({
			           type: "POST",
			           url: "${ctx}/merchant/merchantBankCardAuthentication/payMoney",
					   data: {'id':id,'payAmount':amount},
					   dataType: 'json',
					   success: function(date){
						   var id = $(".curholder").find("iframe").attr("id");
						   document.getElementById(id).contentWindow.location.reload(true);
						   $("#ngp_cover").hide();
						   $(".ngp_pop_four").hide();
						   if(date=="error"){
							   alert("打款金额被篡改!");
						   }
					   }
			    });	
				
			});
            //动态口令确认按钮(ly)
            $("#ngp_dynamic").on("click",function(){
                if($("#ngp_dynamic").hasClass('submintIn')){ return false; }//避免重复提交
                var password = $("#dynamic_word").val();
                var url=$("#url_text").text();

                var key = url.substring(0, url.lastIndexOf("/"));
                $("#ngp_dynamic").text("提交中...").addClass('submintIn');
                $.ajax({
                    type: "POST",
                    url: "${ctx}/util/dynamic",
                    data: {'DynamicW':password,'url':key},
                    dataType: 'json',
                    success: function(flag){
                        console.log(flag);
                        $("#ngp_cover").hide();
                        $(".ngp_pop_fours").hide();
                        $("#dynamic_word").val("");
                        if(flag=="success"){
                            $("#ngp_dynamic").text("确定").removeClass('submintIn');
                            var id = $(".curholder").find("iframe").attr("id");
                            document.getElementById(id).contentWindow.location.href=url;
                        } else{
                            //alert("动态口令校验失败");
                            showThree();
                            changeThreeName("动态口令校验失败");
                            $("#ngp_dynamic").text("确定").removeClass('submintIn');
                        }
                    },
                    error: function(){
                        console.log("请求失败!");
                    }
                });

            });
			//费率批量修改动态口令确认按钮(ly)
			$("#ngp_dynamic_rate").on("click",function(){
				if($("#ngp_dynamic_rate").hasClass('submintIn')){ return false; }//避免重复提交
				var password = $("#dynamic_word_rate").val();
				$("#ngp_dynamic_rate").text("提交中...").addClass('submintIn');
				$.ajax({
					type: "POST",
					url: "${ctx}/util/dynamic",
					data: {'DynamicW':password},
					dataType: 'json',
					success: function(flag){
						$("#ngp_cover").hide();
						$(".ngp_pop_fours").hide();
						$("#dynamic_word_rate").val("");
						if(flag=="success"){
							$("#ngp_dynamic_rate").text("确定").removeClass('submintIn');
							showRate();
						} else{
							//alert("动态口令校验失败");
							showThree();
							changeThreeName("动态口令校验失败");
							$("#ngp_dynamic_rate").text("确定").removeClass('submintIn');
						}
					},
					error: function(){
						console.log("请求失败!");
					}
				});

			});
			//打款认证动态口令确认按钮(ly)
            $("#ngp_dynamic_money").on("click",function(){
            	if($("#ngp_dynamic_money").hasClass('submintIn')){ return false; }//避免重复提交
                var password = $("#dynamic_word_money").val();
                var url=$("#url_text").text();
                $("#ngp_dynamic_money").text("提交中...").addClass('submintIn');
                $.ajax({
                    type: "POST",
                    url: "${ctx}/util/dynamic",
                    data: {'DynamicW':password},
                    dataType: 'json',
                    success: function(flag){
                        $("#ngp_cover").hide();
                        $(".ngp_pop_fours").hide();
                        $("#dynamic_word_money").val("");
                        if(flag=="success"){
                        	$("#ngp_dynamic_money").text("确定").removeClass('submintIn');
                        	showNgp();
                        } else{
                            //alert("动态口令校验失败");
                            showThree();
                            changeThreeName("动态口令校验失败");
                            $("#ngp_dynamic_money").text("确定").removeClass('submintIn');
                        }
                    },
                    error: function(){
                        console.log("请求失败!");
                    }
                });

            });

			//充值登录密码确认按钮
		 	$("#user_send").on("click",function(){
				var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})$/;
				var email = $("#email").val();
				if(!reg.test(email)){
					$(".email_error").show();
					return false;
				}
		 		var loginName = $("#login_name").val();
		 		var password = $("#password1").val();
				var resetWhat = $(".ngp_pop_h").text();
				$.ajax({
			           type: "POST",
			           url: "${ctx}/merchant/merchantUser/sendEmailAddress",
					   data: {'emailAddress':email,
						      'password':password,
						      'loginName':loginName,
						      'resetWhat':resetWhat
					   },
					   dataType: 'json',
					   success:function(result){
						   if(result=="success"){
							   alert("处理成功");
							   var id = $(".curholder").find("iframe").attr("id");
							   document.getElementById(id).contentWindow.location.reload(true);
						   }else if(result=="fail"){
							   alert("填写邮箱地址");
						   }else if(result=="errorPassword"){
							   alert("密码错误");
						   }else{
							   alert("请求失败");
					       }
						},
			           error: function(){
			               console.log("请求失败!");
			           }
			    });	
				$("#ngp_cover").hide();
				$(".ngp_pop_two").hide();
			}); 
			//禁用账号确认禁用按钮
		 	$("#change_status").on("click",function(){
		 		var status = $("#status").val();
		 		var id = $("#merchant_id").val();
		 		var password = $("#password2").val();
				$.ajax({
			           type: "POST",
			           url: "${ctx}/merchant/merchantUser/status",
					   data: {'password':password,
						      'status':status,
						      'id':id
					   },
					   dataType: 'json',
					   success:function(result){
						   if(result=="success"){
							   alert("处理成功");
							   var id = $(".curholder").find("iframe").attr("id");
							   document.getElementById(id).contentWindow.location.reload(true);
						   }else if(result=="errorPassword"){
							   alert("密码错误");
						   }else{
							   alert("请求失败");
					       }
						},
			            error: function(){
			               console.log("请求失败!");
			            }
			    });	
				$("#ngp_cover").hide();
				$(".ngp_pop_five").hide();
			}); 
			//重置绑定手机确认按钮
		 	$("#Unbundling").on("click",function(){
		 	    var id = $("#merchant_id").val(); 
		 	   var password = $("#password3").val();
				$.ajax({
			           type: "POST",
			           url: "${ctx}/merchant/merchantUser/unbundling",
					   data: {'password':password,
						      'id':id
					   },
					   dataType: 'json',
					   success:function(result){
						   if(result=="success"){
							   alert("处理成功");
							   var id = $(".curholder").find("iframe").attr("id");
							   document.getElementById(id).contentWindow.location.reload(true);
						   }else if(result=="errorPassword"){
							   alert("密码错误");
						   }else{
							   alert("请求失败");
						   }
					    }, 
			           error: function(){
			               console.log("请求失败!");
			           }
			    });	
				$("#ngp_cover").hide();
				$(".ngp_pop_six").hide();
			}); 
		});
		
		// <c:if test="${tabmode eq '1'}"> 添加一个页签
		function addTab($this, refresh){
			$(".jericho_tab").show();
			$("#mainFrame").hide();
			$.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $this.text(),
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('href')
                }
            }).loadData(refresh);
			return false;
		}// </c:if>
		//显示打款认证弹窗方法(ly)
		function showNgp(){
			$("#ngp_cover").show();
			$(".ngp_pop_four").show();
		}
		//显示费率批量修改弹窗方法(ly)
		function showRate(){
			$("#ngp_cover").show();
			$("#rate_div").show();
			$("#maxFeeP").hide();
			$("#minFeeP").hide();
			$("#maxFee").val("");
			$("#minFee").val("");
			//阶梯(1)
			$("#rateFeeP").hide();
			$("#rateRatioP").hide();
			$("#ladderP").hide();
			$("#rateFee").val("");
			$("#rateRatio").val("");
			$("#chargeMin").val("");
			$("#chargeMax").val("");
			//阶梯(2)
			$("#rateFeeP2").hide();
			$("#rateRatioP2").hide();
			$("#ladderP2").hide();
			$("#rateFee2").val("");
			$("#rateRatio2").val("");
			$("#chargeMin2").val("");
			$("#chargeMax2").val("");
			//阶梯(3)
			$("#rateFeeP3").hide();
			$("#rateRatioP3").hide();
			$("#ladderP3").hide();
			$("#rateFee3").val("");
			$("#rateRatio3").val("");
			$("#chargeMin3").val("");
			$("#chargeMax3").val("");
			$("#chargeType option").removeAttr("selected");
			$("#chargeTypeP .select2-chosen").text("请选择");
		}
		//切换名称方法(ly)
		function changeName(a,b,c,d){
			$("#ngp_pay_text").text(a);
			$("#bank_num").text(b);
			$("#money_num").text(c);
			$("#bank_id").val(d);
		}

        //显示错误弹窗
        function showThreees(){
            $("#ngp_cover").show();
            $(".ngp_pop_foures").show();
        }
        function changeNamees(a){
            $("#ngp_pay_textes").text(a);
        }
        function show_one(){
            $("#ngp_cover").show();
        }
		//显示错误弹窗(ly)
		function showThree(){
			$("#ngp_cover").show();
			$(".ngp_pop_three").show();
		}

        function showThree_one(){
            $("#ngp_cover").show();
            $(".ngp_pop_three_one").show();
        }

		//反欺诈专用
		function showThrees(){
			$("#ngp_cover").show();
			$(".ngp_pop_threes").show();
		}

		//弹出动态口令框(ly)
		function showDynamicPa(){
			$("#ngp_cover").show();
			$("#ngp_pop_fours").show();
			$("#url_text").hide();
			$("#dynamic_word").focus();
		}
		//弹出打款认证动态口令框(ly)
        function showDynamicMoney(){
            $("#ngp_cover").show();
            $("#ngp_pop_money").show();
			$("#dynamic_word_money").focus();
        }
		//弹出费率批量修改动态口令框(ly)
		function showDynamicRate(checkedstr,rateId,somerateType){
			$("#rateBanks").val(JSON.stringify(checkedstr));
			$("#rateId").val(rateId);
			$("#somerateType").val(somerateType);
			$("#ngp_cover").show();
			$("#ngp_pop_rate").show();
			$("#dynamic_word_rate").focus();
		}
		//保存当前动态口令要访问的地址(ly)
		function enterDynamicPa(url){
			$("#url_text").text(url);
		}

		function showThreeEp(){
            $("#ngp_cover").show();
            $(".ngp_pop_three_ep").show();
        }
		//修改弹窗显示内容(ly)
		function changeThreeName(a){
			$("#three_text").text(a);
		}

        function changeThreeName_one(a){
            $("#three_text_one").text(a);
        }
		//
		function changeThreeNames(a,c){
			$("#three_texts").text(a);
			$("#pbc_url").val(c);
			
		}
		function changeThreeNameEp(a,transType,transNo){
			$("#transNo").val(transNo);
			$("#transType").val(transType);
            $("#three_text_ep").text(a);
        }
		//设置资金平衡显示需要的值
		function setCheckBalanceVal(a,b,c){
			 $("#check_title").text(a);
			 $("#totalDebitAmount").text(b);
			 $("#totalCreditAmount").text(c);
		}
		//显示资金平衡校验结果
		function showCheckBalanceWindow(){
			$("#ngp_cover").show();
			$(".ngp_pop_seven").show();
		}
		//显示用户弹窗
		function showUser(){
			$("#ngp_cover").show();
			$(".ngp_pop_two").show();
			$(".email_error").hide();
		}
		//显示发邮件弹窗
		function sendEmail(a,b,c){
			$("#login_name").val(a);
			 $(".ngp_pop_h").text(b);
			 $(".ngp_pop_text").text(c);
		}
		//显示禁用弹窗
		function showStatus(){
			$("#ngp_cover").show();
			$(".ngp_pop_five").show();
		} 
		//修改禁用状态弹窗内容
		function changeStatus(a,b,c,d,e,f){
			$("#l_name").text(a);
			$(".ngp_pop_s").text(b);
			$(".status_changing").text(c);
			$("#status").val(d);
			$("#change_status").text(e);
			$("#merchant_id").val(f);
		}
		//显示重置绑定手机号弹窗
     	function showUnbundling(){
			$("#ngp_cover").show();
			$(".ngp_pop_six").show();
		} 
		//修改重置弹窗内容
    	function setLoginName(a,b){
    		$("#logi_name").text(a);
    		$("#merchant_id").val(b);
		}
    	//验证金额14位整数 2位小数
    	function amountCheck(obj){
			var regex = /^\.{0,1}[0-9]{0,2}$/;
		    var regex1 = /^([1-9]\d{0,13}|0)$/;
	        var str = $(obj).val();
	        if(str.indexOf(".") == -1){
	            if(!regex1.test($(obj).val())){
	                 $(obj).val("");
	            }
	        }else{
	           var arr = str.split(".");
	            console.log(arr[0]);
	           if(!regex1.test(arr[0])){
	               $(obj).val("");
	           }else if(!regex.test(arr[1])){
	                $(obj).val("");
	           }
	        }
		}
		function rateChargeType(value){
			if("RATIOD" == value){
				$(".rateSpan").html("手续费费用(%):");
				$("#maxFeeP").show();
				$("#minFeeP").show();
				//阶梯(1)
				$("#rateFeeP").hide();
				$("#ladderP").show();
				$("#rateRatioP").show();
				$("#rateFee").val("");
				$("#chargeMax").val("");
				$("#chargeMin").val("");
				//阶梯(2)
				$("#rateFeeP2").hide();
				$("#ladderP2").show();
				$("#rateRatioP2").show();
				$("#rateFee2").val("");
				$("#chargeMax2").val("");
				$("#chargeMin2").val("");
				//阶梯(3)
				$("#rateFeeP3").hide();
				$("#ladderP3").show();
				$("#rateRatioP3").show();
				$("#rateFee3").val("");
				$("#chargeMax3").val("");
				$("#chargeMin3").val("");
			}else if("COUNTD" == value){
				$(".rateSpan").html("手续费费用(元):");
				$("#maxFeeP").hide();
				$("#minFeeP").hide();
				$("#maxFee").val("");
				$("#minFee").val("");
				//阶梯(1)
				$("#rateFeeP").show();
				$("#rateRatioP").hide();
				$("#ladderP").show();
				$("#rateRatio").val("");
				$("#chargeMax").val("");
				$("#chargeMin").val("");
				//阶梯(2)
				$("#rateFeeP2").show();
				$("#rateRatioP2").hide();
				$("#ladderP2").show();
				$("#rateRatio2").val("");
				$("#chargeMax2").val("");
				$("#chargeMin2").val("");
				//阶梯(3)
				$("#rateFeeP3").show();
				$("#rateRatioP3").hide();
				$("#ladderP3").show();
				$("#rateRatio3").val("");
				$("#chargeMax3").val("");
				$("#chargeMin3").val("");
			}
		}
	</script>
</head>
<body>
	<input id="myctx" type="hidden" value="${ctx}"/>
	<input id="transNo" name="transNo" type="hidden" value=""/>
	<input id="transType" name="transType" type="hidden" value=""/>
	<div id="ngp_cover"></div>
	<div class="ngp_pop_fours" id="ngp_pop_fours">
		<p class="ngp_name_text">请输入动态口令</p>
		<p class="ngp_url" id="url_text"></p>
		<p><input type="text" id="dynamic_word"></p>
		<h3 class="ngp_btn_kt"><span id="ngp_dynamic">确定</span><span id="ngp_dynamic_cancel">取消</span></h3>
	</div>
    <div class="ngp_pop_fours" id="ngp_pop_money">
        <p class="ngp_name_text">请输入动态口令</p>
        <p><input type="text" id="dynamic_word_money"></p>
        <h3 class="ngp_btn_kt"><span id="ngp_dynamic_money">确定</span><span id="ngp_money_cancel">取消</span></h3>
    </div>
	<!--费率批量修改弹窗(ly)-->
	<div class="ngp_pop_fours" id="ngp_pop_rate">
		<p class="ngp_name_text">请输入动态口令</p>
		<p><input type="text" id="dynamic_word_rate"></p>
		<h3 class="ngp_btn_kt"><span id="ngp_dynamic_rate">确定</span><span id="ngp_rate_cancel">取消</span></h3>
	</div>

	<div class="ngp_pop_four">
        <p class="ngp_name_text" id="ngp_pay_text">认证打款</p>
        <p class="bangk_number">银行卡号：<span id="bank_num"></span></p>
        <p class="money_number">打款金额：<span id="money_num"></span></p>
        <input type="hidden" id="bank_id" />
        <h3 class="ngp_btn_kt"><span id="ngp_pay">确定</span><span id="ngp_cancel">取消</span></h3>
    </div>
	<div class="ngp_pop_foures">
		<p class="ngp_name_text" id="ngp_pay_textes">认证打款</p>
		<input type="hidden" id="bank_ides" />
		<h3 class="ngp_btn_kt"><span id="ngp_payes">确定</span><span id="ngp_canceles">取消</span></h3>
	</div>
    <div class="ngp_pop_three">
    	<p class="ngp_name_text" id="three_text"></p>
    	<h3 class="ngp_btn_kt"><span id="ngp_ok">确认</span></h3>
    </div>
	<div class="ngp_pop_three_one">
		<p class="ngp_name_text" id="three_text_one"></p>
	</div>
    <div class="ngp_pop_threes">
    	<p class="ngp_name_text" id="three_texts"></p>
    	<input type="hidden" id="pbc_url" />
    	<h3 class="ngp_btn_kt"><span id="ngp_ok_s">确定</span></h3>
    </div>
    <div class="ngp_pop_three_ep">
        <p class="ngp_name_text" id="three_text_ep"></p>
        <h3 class="ngp_btn_kt"><span id="ngp_ok_ep">确认</span></h3>
    </div>
	<!--费率批量修改弹窗(ly)-->
	<div class="ngp_pop_foursrate" id="rate_div">
		<div style="margin-left: 40px;">
		<p class="rate_type">计费类型</p>
		<p id="chargeTypeP" style="margin-top:30px;">
			<select id="chargeType"  class="input-large" onchange="rateChargeType(this.options[this.options.selectedIndex].value);">
				<option value="">请选择</option>
				<option value="RATIOD">按比例</option>
				<option value="COUNTD">按笔数</option>
			</select>
		</p>
		</div>
		<p id="ladderP"><span class="rateSpan">手续费费用(元):</span><input type="text" id="chargeMin" class="input-mini" onkeyup="amountCheck(this);">~<input type="text" id="chargeMax" class="input-mini" onkeyup="amountCheck(this);">
		<span id="rateFeeP"><input type="text" class="input-small" id="rateFee" onkeyup="amountCheck(this);"></span>
		<span id="rateRatioP"><input type="text" class="input-small" id="rateRatio" onkeyup="amountCheck(this);"></span>
		</p>
		<p id="ladderP2"><span class="rateSpan">手续费费用(元):</span><input type="text" id="chargeMin2" class="input-mini" onkeyup="amountCheck(this);">~<input type="text" id="chargeMax2" class="input-mini" onkeyup="amountCheck(this);">
		<span id="rateFeeP2"><input type="text" class="input-small" id="rateFee2" onkeyup="amountCheck(this);"></span>
		<span id="rateRatioP2"><input type="text" class="input-small" id="rateRatio2" onkeyup="amountCheck(this);"></span>
		</p>
		<p id="ladderP3"><span class="rateSpan">手续费费用(元):</span><input type="text" id="chargeMin3" class="input-mini" onkeyup="amountCheck(this);">~<input type="text" id="chargeMax3" class="input-mini" onkeyup="amountCheck(this);">
		<span id="rateFeeP3"><input type="text" class="input-small" id="rateFee3" onkeyup="amountCheck(this);"></span>
		<span id="rateRatioP3"><input type="text" class="input-small" id="rateRatio3" onkeyup="amountCheck(this);"></span>
		</p>
		<p id="maxFeeP"><span>最大值(元):</span><input type="text" id="maxFee" onkeyup="amountCheck(this);"></p>
		<p id="minFeeP"><span>最小值(元):</span><input type="text" id="minFee" onkeyup="amountCheck(this);"></p>
		<input type="hidden" id="rateBanks">
		<input type="hidden" id="rateId">
		<input type="hidden" id="somerateType">
		<h3 class="ngp_btn_kt"><span id="rate_ok">确定</span><span id="rate_cancel">取消</span></h3>
	</div>

    <div class="ngp_pop_two">
        <h6 class="ngp_pop_h">重置登录密码</h6>
        <p class="ngp_pop_text">发送重置【登录密码】链接邮箱：</p>
        <h5 class="pop_text_input">
			<input type="text" id="email" placeholder="电子邮箱">
			<h6 style="color:red;font-size:12px;display: none;margin-left:40px;" class="email_error">请输入有效邮箱</h6>
		</h5>
        <p class="pop_pswd_name">操作员密码：</p>
        <h5 class="pop_text_pswd"><input type="password" id="password1" placeholder="操作员密码"></h5>
        <input type="hidden" id="login_name" />
        <h3 class="ngp_btn_kt"><span id="user_send">确定下发</span><span id="user_cancel">取消</span></h3>
    </div>
    <div class="ngp_pop_five">
        <h6 class="ngp_pop_s">禁用账号</h6>
        <p class="status_changing">正在禁用</p>
        <p class="account_name">账号：<span id="l_name">xx</span></p>
        <p class="pop_pswd_name">操作员密码：</p>
        <h5 class="pop_text_pswd"><input type="password" id="password2" placeholder="操作员密码"></h5>
        <input type="hidden" id="status" />
        <h3 class="ngp_btn_kt"><span id="change_status">确定禁用</span><span id="status_cancel">取消</span></h3>
    </div>
     <div class="ngp_pop_six">
        <h6 class="ngp_pop_v">重置绑定手机号</h6>
        <p class="reset_phone">正在为：<span id="logi_name">xx</span><span>解绑手机号</span></p>
        <p class="pop_pswd_name">操作员密码：</p>
        <h5 class="pop_text_pswd"><input type="password" id="password3" placeholder="操作员密码"></h5>
          <input type="hidden" id="merchant_id" />
        <h3 class="ngp_btn_kt"><span id="Unbundling">确定解绑</span><span id="Unbundling_cancel">取消</span></h3>
    </div>
    <div class="ngp_pop_seven">
        <p class="ngp_name_text" id="check_title"></p>
        <p class="bangk_number">借方总金额：<span id="totalDebitAmount"></span></p>
        <p class="money_number">贷方总金额：<span id="totalCreditAmount"></span></p>
        <input type="hidden" id="check_amt_id" />
        <h3 class="ngp_btn_kt"><span id="ngp_confirm">确定</span></h3>
    </div>
	<div id="main">
		<div id="header" class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="brand"><span id="productName">${fns:getConfig('productName')}</span></div>
				<ul id="userControl" class="nav pull-right">
					<%-- <li><a href="${pageContext.request.contextPath}${fns:getFrontPath()}/index-${fnc:getCurrentSiteId()}.html" target="_blank" title="访问网站主页"><i class="icon-home"></i></a></li> --%>
					<li id="themeSwitch" class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
						<ul class="dropdown-menu">
							<c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
							<li><a href="javascript:cookie('tabmode','${tabmode eq '1' ? '0' : '1'}');location=location.href">${tabmode eq '1' ? '关闭' : '开启'}页签模式</a></li>
						</ul>
						<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
					</li>
					<li id="userInfo" class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好, ${fns:getUser().name}&nbsp;<span id="notifyNum" class="label label-info hide"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
							<li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
							<%-- <li><a href="${ctx}/oa/oaNotify/self" target="mainFrame"><i class="icon-bell"></i>&nbsp;  我的通知 <span id="notifyNum2" class="label label-info hide"></span></a></li> --%>
						</ul>
					</li>
					<li><a href="${ctx}/logout" title="退出登录">退出</a></li>
					<li>&nbsp;</li>
				</ul>
				<%-- <c:if test="${cookie.theme.value eq 'cerulean'}">
					<div id="user" style="position:absolute;top:0;right:0;"></div>
					<div id="logo" style="background:url(${ctxStatic}/images/logo_bg.jpg) right repeat-x;width:100%;">
						<div style="background:url(${ctxStatic}/images/logo.jpg) left no-repeat;width:100%;height:70px;"></div>
					</div>
					<script type="text/javascript">
						$("#productName").hide();$("#user").html($("#userControl"));$("#header").prepend($("#user, #logo"));
					</script>
				</c:if> --%>
				<div class="nav-collapse">
					<ul id="menu" class="nav" style="*white-space:nowrap;float:none;">
						<c:set var="firstMenu" value="true"/>
						<c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
							<c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
								<li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
									<c:if test="${empty menu.href}">
										<a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}"><span>${menu.name}</span></a>
									</c:if>
									<c:if test="${not empty menu.href}">
										<a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame"><span>${menu.name}</span></a>
									</c:if>
								</li>
								<c:if test="${firstMenu}">
									<c:set var="firstMenuId" value="${menu.id}"/>
								</c:if>
								<c:set var="firstMenu" value="false"/>
							</c:if>
						</c:forEach><%--
						<shiro:hasPermission name="cms:site:select">
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fnc:getSite(fnc:getCurrentSiteId()).name}<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<c:forEach items="${fnc:getSiteList()}" var="site"><li><a href="${ctx}/cms/site/select?id=${site.id}&flag=1">${site.name}</a></li></c:forEach>
							</ul>
						</li>
						</shiro:hasPermission> --%>
					</ul>
				</div><!--/.nav-collapse -->
			</div>
	    </div>
	    <div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="left"><%-- 
					<iframe id="menuFrame" name="menuFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe> --%>
				</div>
				<div id="openClose" class="close">&nbsp;</div>
				<div id="right">
					<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
				</div>
			</div>
		    <div id="footer" class="row-fluid">
	            Copyright &copy; 2012-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By <a href="https://mch.heepay.com" target="_blank">汇付宝</a> ${fns:getConfig('version')}
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		var leftWidth = 160; // 左侧窗口大小
		var tabTitleHeight = 33; // 页签的高度
		var htmlObj = $("html"), mainObj = $("#main");
		var headerObj = $("#header"), footerObj = $("#footer");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var minHeight = 500, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":strs[0] < minHeight ? "auto" : "hidden"});
			mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
			$("#openClose").height($("#openClose").height() - 5);// <c:if test="${tabmode eq '1'}"> 
			$(".jericho_tab iframe").height($("#right").height() - tabTitleHeight); // </c:if>
			wSizeWidth();
		}
		function wSizeWidth(){
			if (!$("#openClose").is(":hidden")){
				var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
				$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			}else{
				$("#right").width("100%");
			}
		}// <c:if test="${tabmode eq '1'}"> 
		function openCloseClickCallBack(b){
			$.fn.jerichoTab.resize();
		} // </c:if>
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
	
</body>
</html>