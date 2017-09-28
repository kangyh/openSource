/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.web;

import com.heepay.codec.Base64;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromMerchantManage;
import com.heepay.prom.modules.prom.entity.PromWechat;
import com.heepay.prom.modules.prom.enums.MerchantType;
import com.heepay.prom.modules.prom.enums.PayType;
import com.heepay.prom.modules.prom.enums.SourceType;
import com.heepay.prom.modules.prom.enums.Status;
import com.heepay.prom.modules.prom.service.PromMerchantManageService;
import com.heepay.prom.modules.prom.service.PromWechatService;
import com.heepay.prom.modules.prom.utils.AesUtils;
import com.heepay.prom.modules.prom.utils.HttpClientUtil;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 *
 * 描    述：用户管理（微信）Controller
 *
 * 创 建 者： @author wangdong
 * 创建时间：
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/prom/promWechat")
public class PromWechatController extends BaseController {

    private static PropertiesLoader loader = new PropertiesLoader("prom.properties");

    private static final Logger logger = LogManager.getLogger();

	@Autowired
	private PromWechatService promWechatService;

	@Autowired
    private PromMerchantManageService promMerchantManageService;
	
	@ModelAttribute
	public PromWechat get(@RequestParam(required=false) String id) {
		PromWechat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = promWechatService.get(id);
		}
		if (entity == null){
			entity = new PromWechat();
		}
		return entity;
	}

    @RequiresPermissions("prom:promWechat:view")
    @RequestMapping(value = "form")
    public String form(PromWechat promWechat, Model model) {
        if (null != promWechat && promWechat.getOpenid() != null){
            promWechat = get(promWechat.getOpenid());
            promWechat.setSubscribeTime(timeStamp2Date(promWechat.getSubscribeTime(),null));
            model.addAttribute("promWechat", promWechat);
        }
        return "modules/prom/promWechatForm";
    }

    /*
     * 将时间戳转换为时间
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }

    @RequestMapping(value = "/app/blank",method = RequestMethod.GET)
    public String blank(String _wx_data) {
        logger.info("推广用户 跳转页");

        try {
            logger.info("推广用户注册--->{}",_wx_data);
            if(StringUtils.isNoneBlank(_wx_data)) {
                //解密
                String jsonString = AesUtils.Aes256Decode(Base64.decode(_wx_data), loader.getProperty("key").getBytes());
                //转化为Map
                Map<String, String> requestHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(jsonString, Map.class);
                if (null != requestHeaderMap && requestHeaderMap.size() > 0) {
                    PromWechat promWechat = new PromWechat();
                    PromMerchantManage promMerchantManage = new PromMerchantManage();
                    //插入用户表
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Openid"))) {
                        promWechat.setOpenid(requestHeaderMap.get("Openid"));
                        promMerchantManage.setWechatNo(requestHeaderMap.get("Openid"));
                        promMerchantManage.setMerSource(SourceType.SOURCE_TYPE_WECHAT.getValue());
                        promMerchantManage.setPayType(PayType.PAY_TYPE_ENVELO.getValue());
                        promMerchantManage.setMerchantType(MerchantType.MERCHANT_TYPE_CUSTOM.getValue());
                        promMerchantManage.setStatus(Status.STATUS_ENABLE.getValue());
                        promMerchantManage.setMerchantName(requestHeaderMap.get("Nickname"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Nickname"))) {
                        promWechat.setNickname(requestHeaderMap.get("Nickname"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Headimgurl"))) {
                        promWechat.setHeadimgurl(requestHeaderMap.get("Headimgurl"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Unionid"))) {
                        promWechat.setUnionid(requestHeaderMap.get("Unionid"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("AppId"))) {
                        promWechat.setAppId(requestHeaderMap.get("AppId"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Subscribe"))) {
                        promWechat.setSubscribe(requestHeaderMap.get("Subscribe"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Sex"))) {
                        promWechat.setSex(requestHeaderMap.get("Sex"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Language"))) {
                        promWechat.setLanguage(requestHeaderMap.get("Language"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("City"))) {
                        promWechat.setCity(requestHeaderMap.get("City"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Province"))) {
                        promWechat.setProvince(requestHeaderMap.get("Province"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("Country"))) {
                        promWechat.setCountry(requestHeaderMap.get("Country"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("SubscribeTime"))) {
                        promWechat.setSubscribeTime(requestHeaderMap.get("SubscribeTime"));
                    }
                    if (StringUtils.isNotBlank(requestHeaderMap.get("UserId"))) {
                        promWechat.setUserId(requestHeaderMap.get("UserId"));
                    }
                    Integer count = promWechatService.findExit(promWechat);
                    if (count > 0) {
                        int up = promWechatService.update(promWechat);
                        logger.info("推广注册用户---更新成功条数--->{}", up);
                    } else {
                        promWechatService.save(promWechat);
                        logger.info("推广注册用户---保存成功");
                    }
                    //插入商户信息
                    PromMerchantManage retPromMerchant = promMerchantManageService.getWechatNo(promMerchantManage);
                    if(null != retPromMerchant) {
                        promMerchantManage.setMerchantId(retPromMerchant.getMerchantId());
                    }
                    promMerchantManageService.autoSave(promMerchantManage);
                }
            }
        }catch (Exception e){
            logger.error("推广注册用户异常--->{}",e);
        }

        return "redirect:"+loader.getProperty("zhonganpromurl");
    }

    /*@RequestMapping(value = "/app/insert",method = RequestMethod.GET)
    public String appInsert(String _wx_data) {
        try {
            logger.info("推广用户注册--->{}",_wx_data);
            //解密
            String jsonString = AesUtils.Aes256Decode(Base64.decode(_wx_data),loader.getProperty("key").getBytes());
            //转化为Map
            Map<String, String> requestHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(jsonString, Map.class);
            if(null != requestHeaderMap && requestHeaderMap.size() > 0) {
                PromWechat promWechat = new PromWechat();
                PromMerchantManage promMerchantManage = new PromMerchantManage();
                //插入用户表
                if(StringUtils.isNotBlank(requestHeaderMap.get("Openid"))){
                    promWechat.setOpenid(requestHeaderMap.get("Openid"));
                    promMerchantManage.setWechatNo(requestHeaderMap.get("Openid"));
                    promMerchantManage.setMerSource(SourceType.SOURCE_TYPE_WECHAT.getValue());
                    promMerchantManage.setPayType(PayType.PAY_TYPE_ENVELO.getValue());
                    promMerchantManage.setMerchantType(MerchantType.MERCHANT_TYPE_CUSTOM.getValue());
                    promMerchantManage.setStatus(Status.STATUS_ENABLE.getValue());
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("Nickname"))){
                    promWechat.setNickname(requestHeaderMap.get("Nickname"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("Headimgurl"))){
                    promWechat.setHeadimgurl(requestHeaderMap.get("Headimgurl"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("Unionid"))){
                    promWechat.setUnionid(requestHeaderMap.get("Unionid"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("AppId"))){
                    promWechat.setAppId(requestHeaderMap.get("AppId"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("Subscribe"))){
                    promWechat.setSubscribe(requestHeaderMap.get("Subscribe"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("Sex"))){
                    promWechat.setSex(requestHeaderMap.get("Sex"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("Language"))){
                    promWechat.setLanguage(requestHeaderMap.get("Language"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("City"))){
                    promWechat.setCity(requestHeaderMap.get("City"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("Province"))){
                    promWechat.setProvince(requestHeaderMap.get("Province"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("Country"))){
                    promWechat.setCountry(requestHeaderMap.get("Country"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("SubscribeTime"))){
                    promWechat.setSubscribeTime(requestHeaderMap.get("SubscribeTime"));
                }
                if(StringUtils.isNotBlank(requestHeaderMap.get("UserId"))){
                    promWechat.setUserId(requestHeaderMap.get("UserId"));
                }
                Integer count = promWechatService.findExit(promWechat);
                if(count>0){
                    int up = promWechatService.update(promWechat);
                    logger.info("推广注册用户---更新成功条数--->{}",up);
                }else{
                    promWechatService.save(promWechat);
                    logger.info("推广注册用户---保存成功");
                }
                //插入商户信息
                promMerchantManageService.save(promMerchantManage);
                return "";
            }
        }catch (Exception e){
            logger.error("推广注册用户异常--->{}",e);
        }
        return "";
    }*/
    @RequestMapping(value = "/app/insert",method = RequestMethod.GET)
    public String appInsert(String _wx_data) {
        return "";
    }

    @RequestMapping(value = "/app/authorization",method = RequestMethod.GET)
    public String authorization(String _aid) throws UnsupportedEncodingException {
        String redirect_url = loader.getProperty("tiaozhuanurl");//
        redirect_url = URLEncoder.encode(redirect_url,"utf-8");
        if(StringUtils.isNoneBlank(_aid)){
            redirect_url = "_aid="+_aid+"&user_info_level=3&redirect_url="+redirect_url;
            logger.info("授权的参数--->{}",redirect_url);
            redirect_url = HttpClientUtil.sendGet(loader.getProperty("promurl"),redirect_url);
            logger.info("授权返回重定向的地址--->{}",redirect_url);
            JSONObject jsonObject = JSONObject.fromObject(redirect_url);
            logger.info("授权返回重定向的地址【截取】--->{}",jsonObject.get("ComData").toString());
            return "redirect:"+jsonObject.get("ComData").toString();
        }else{
            logger.info("授权 appid--->{}",_aid);
            return "参数错误";
        }
    }
}