function validateNum(merchantId) {
	if(isNaN(merchantId)){
		$("#messageBox").text("商户编码请输入数字!");
        return false;
	}
	return true;
}

function validateNum(val,errorMessage) {
	if(isNaN(val)){
		$("#messageBox").text(errorMessage);
		return false;
	}
	return true;
}

/**
 * 防注入
 * @param val
 * @param errorMessage
 * @returns {boolean}
 */
function validatePreventInject(val,errorMessage){
	var inj_str = ") ' * % < > & ; script cookie expression";
	var inj_stra = inj_str.split(" ");
	var hasError = false;
	for (var i = 0; i < inj_str.length; i++) {
		if (val.indexOf(inj_stra[i]) >= 0) {
			hasError = true;
			break;
		}
	}
	if(hasError){
		$("#messageBox").text(errorMessage);
		return false;
	}
	return true;
}

/**
 * 校验小数
 * @param val
 * @param errorMessage
 */
function validateAmount(val,errorMessage){
	if(val.length !=0 && !/^\d+\.\d+$/.test(val)){
		$("#messageBox").text(errorMessage);
		return false;
	}
	return true;
}

