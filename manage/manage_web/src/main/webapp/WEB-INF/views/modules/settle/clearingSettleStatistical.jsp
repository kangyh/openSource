<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>清结算管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/eCharts/echarts.js"></script>
</head>
<body>
	<div class="control-group">
		<div class="controls">
			<div style="text-align:center;padding-top: 20px;font-size: 2em;font-family: serif;font: bold;padding-bottom: 20px;">清结算数据分析统计</div>
		</div>
		<li><a id="url_id" href="${ctx}/settle/clearingSettleStatisticalQuery/"></a></li>
	</div>
	<div class="control-group">
		<div class="controls" align="left" style="padding-top: 20px;float: left;width:20%;">
			<label style="padding-left: 28px;">时间：</label>
			<select class="input-xlarge" id="check_date_id" name="checkDate">
				<option value="W" selected="selected">最近一周</option>
				<option value="M" >最近一个月</option>
				<option value="3M" >最近三个月</option>
			</select> 
			<label style="padding-left: 28px;">类型：</label>
			<select class="input-xlarge" id="check_remark_id" name="checkRemark" style="padding-top: 8px;">
				<option value="T" selected="selected">交易总金额</option>
				<option value="O" >出款金额</option>
				<option value="I" >入款金额</option>
				<option value="F" >差异金额</option>
			</select>
			<label style="padding-top: 8px;padding-left: 15px;">合作方：</label>
			<select id="channelCode" class="input-xlarge" style="padding-top: 8px;">
				<option value="">请选择</option>
				<c:forEach items="${channelCode}" var="wStatus">
					<option value="${wStatus.value}">${wStatus.name}</option>
				</c:forEach>
			</select>
			<div style="padding-top: 20px;" align="center">
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="onSubmit()" value="查询"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnClear" class="btn btn-primary" type="button" onclick="onClear()" value="清空"/>
			</div>
		</div>
		<div class="controls" align="center" style="float: left;width:80%;">
			<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
		    <div id="statistical_id" style="width: 95%;height:550%;"></div>
		    <div id="statistical_id_text" style="width: 60%;height:600%;color: red;font-size: 2em;"></div>
		</div>
    </div>
    
    <script type="text/javascript">
     	
		$(document).ready(function() {
			//默认查询总数和总金额
			onSubmit();
		});	
	
		//查询
		function onSubmit(){
			var checkDate = $("#check_date_id").find("option:selected").val();
			var checkRemark = $("#check_remark_id").find("option:selected").val();
			var checkChannelCode = $("#channelCode").find("option:selected").val();
			$.post($("#url_id").attr("href")+"getInfo",{
				checkDate : checkDate,
				checkRemark : checkRemark,
				channelCode : checkChannelCode
				},function(data){
					 // 基于准备好的dom，初始化echarts实例
					 var myChart = echarts.init(document.getElementById('statistical_id'));
					
					//根据不同的类型显示不同的图标
			    	var option = {
			   		    title : {
			   		    	text: '各通道的类型统计',
					        subtext: '数据来源：清算结算数据   单位：（元）'
			   		    },
			   		    tooltip : {
			   		        trigger: 'axis',
				  		    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				  	        	type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				  	        }
			   		    },
			   		    legend: {
			   		    	data:[]
			   		    },
			   		    toolbox: {
			   		        show : true,
			   		        feature : {
			   		            dataView : {show: true, readOnly: false},
			   		            magicType : {show: true, type: ['line', 'bar']},
			   		            restore : {show: true},
			   		            saveAsImage : {show: true}
			   		        }
			   		    },
			   		    calculable : true,
			   		    xAxis : [
			   		        {
			   		            type : 'category',
			   		         	axisLabel:{
									interval:0,  //类目全显
									rotate:30    //旋转角度
				   	            },
			   		         	data : []
			   		        }
			   		    ],
			   		    yAxis : [
			   		        {
			   		            type : 'value',
			   		         	splitNumber : 15,
			   		        }
			   		    ],
			   		    series : []
			   		};
					var legendArr = [];//图标标题数组(类型)
					var xAxisArr = [];//X轴的显示
					var seriesArr = [];//数据显示
					if(data != null && data != "" && data != undefined && data.length > 0){
						//驱动图表生成的数据内容，数组中每一项代表一个系列的特殊选项及数据
	                    for (var i = 0; i < data.length; i++) {
	                    	//给图标标题赋值
	                    	if(legendArr != null && legendArr != undefined && legendArr.indexOf(data[i].channelTypeName)!=-1){
		                    	
	                    	}else{
	                    		legendArr.push(data[i].channelTypeName);
	                    	}
	                    	if(xAxisArr != null && xAxisArr != undefined && xAxisArr.indexOf(data[i].channelName)!=-1){
	                    		
	                    	}else{
	                    		xAxisArr.push(data[i].channelName);
	                    	}
	                    }
	                    if(checkRemark == 'T'){
	                    	seriesArr = assignment(data,xAxisArr,legendArr,seriesArr,checkRemark);
	                 	}else if(checkRemark == 'O'){
	                 		seriesArr = assignment(data,xAxisArr,legendArr,seriesArr,checkRemark);
	                 	}else if(checkRemark == 'I'){
	                 		seriesArr = assignment(data,xAxisArr,legendArr,seriesArr,checkRemark);
	                 	}else if(checkRemark == 'F'){
	                 		seriesArr = assignment(data,xAxisArr,legendArr,seriesArr,checkRemark);
	                 	}
	                    option.xAxis[0].data = xAxisArr;
	                    option.legend.data = legendArr;
	                    option.series = seriesArr;
	                    //单独的通道查询时，限制柱状图的宽度
	                    if(xAxisArr.length==1){
		                    option.barWidth = 70;
	                    }
	                    //过渡控制，隐藏loading（读取中）
	                    myChart.hideLoading();
	                    $("#statistical_id_text").text("");
					}else{
						option.series = seriesArr;
						$("#statistical_id_text").text("无数据！");
					}
                    // 为echarts对象加载数据
					myChart.setOption(option);
				}
			);
		}
	
		//赋值
		function assignment(data,xAxisArr,legendArr,seriesArr,checkRemark){
			//X轴的值
			if(legendArr != null && legendArr != ''){
				for(var i = 0; i < legendArr.length; i++){
					var temp = {};
					//该类型的所有数据
					var tempArr = [];//数据数组
					for(var j = 0; j < xAxisArr.length; j++){
						var valArr = checkLegendArr(legendArr[i],xAxisArr[j],data);
						for(var k = 0; k < valArr.length; k++){
							if(xAxisArr[j] == valArr[k].channelName){
								temp.name = legendArr[i];
								temp.type = 'bar';
								if(checkRemark == 'T'){
									tempArr[j] = valArr[k].totalAmount;
			                 	}else if(checkRemark == 'O'){
			                 		tempArr[j] = valArr[k].outTotalAmount;
			                 	}else if(checkRemark == 'I'){
			                 		tempArr[j] = valArr[k].inTotalAmount;
			                 	}else if(checkRemark == 'F'){
			                 		tempArr[j] = valArr[k].errorTotalAmount;
			                 	}
								temp.markPoint = {};
								temp.markPoint.data = [{type : 'max', name: '最大值'},{type : 'min', name: '最小值'}];
								temp.markLine = {};
								temp.markLine.data = [{type : 'average', name: '平均值'}];
							}
						}
					}
					temp.data = tempArr;
					seriesArr.push(temp);
				}
			}
			return seriesArr;
		}
		
		//比较X轴
		function checkLegendArr(legend,xAxis,data){
			var valArr = [];
			for (var i = 0; i < data.length; i++) {
				if(legend == data[i].channelTypeName && xAxis == data[i].channelName){
					valArr.push(data[i]);
				}
			}
			return valArr;
		}
		
		//清空
		function onClear(){
			
			$("#check_date_id").find("option").removeAttr("selected");
			$("#check_date_id").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(0)").text($("#check_date_id option[selected]").text());
			
			$("#check_remark_id").find("option").removeAttr("selected");
			$("#check_remark_id").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(1)").text($("#check_remark_id option[selected]").text());
			
			$("#channelCode").find("option").removeAttr("selected");
			$("#channelCode").find("option:eq(0)").attr("selected","selected");
			$(".select2-chosen:eq(2)").text($("#channelCode option[selected]").text());
		}
     	
    </script>
</body>
</html>