package com.heepay.warning.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.codec.MD5Util;
import com.heepay.common.util.Constants;
import com.heepay.common.util.HttpClientUtils;
import com.heepay.common.util.SmsUtils;

public class SendSms {
	 /**
     * 定义日志对象
     */
    private static final Logger logger = LogManager.getLogger();

    private static Properties pros = null;

    /**
     * Loading file while class loading.The operation inside of static block
     * will be run once.
     * 配置文件的名字必须为    shortmessage.properties    并且在classpath下
     * 配置文件需要如下写法
     * #这是配置的一个id号#
     * sms.agentID=27652
     * #这是配置的版本号#
     * sms.version=1
     * #这是配置的密码#
     * sms.password=|||7A3A4A4283E62CB
     * #这是配置的地址#
     * sms.requestAddress=http://192.168.2.95/Service/InnerService/SendSMS.aspx
     * #这是短信内容前缀。#
     * sms.massage.prefix=【汇付宝】验证码：
     * #这是短信内容后缀#
     * sms.massage.suffix=，任何人索取验证码均为诈骗，为了您的资金安全切勿泄露!
     */
    static {
        pros = new Properties();
        try {
            pros.load(SmsUtils.class
                    .getResourceAsStream("/shortmessage.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取短信配置文件失败", e);
        }
    }

    private SendSms() {
    }

    /**
     * 发送短信
     *
     * @param phone 电话号
     * @param msg   信息内容
     * @param type  类型
     * @return 处理的信息
     * @throws InterruptedException 
     */
    public static String sendSMS(String phone, String msg, String type) {
    	try {
			Thread.sleep(60000L);
		} catch (InterruptedException e1) {
		}
        StringBuilder sbParam = new StringBuilder();
        StringBuilder sbMd5 = new StringBuilder();
        StringBuilder url = new StringBuilder();

        if (org.apache.commons.lang.StringUtils.isBlank(phone)) {
            return "电话号不能为空!";
        }

        if (org.apache.commons.lang.StringUtils.isBlank(msg)) {
            return "发送的信息内容不能为空！";
        }

        try {
            sbParam.append("?agentID=").append(pros.getProperty(Constants.SMS.AGENTID));
            //3快捷支付 ,40注册，41登录，42找回密码，43修改手机号，44绑定手机，45授权开通，46提现，47实名，48 重置密保49修改邮箱，50手机充值，51修改密码,52手机客户端下载,53支付密码修改
            sbParam.append("&type=").append(type).append("&phone=").append(phone).append("&msg=")
                    .append(URLEncoder.encode(pros.getProperty(Constants.SMS.PREFIX)+msg+pros.getProperty(Constants.SMS.SUFFIX), "UTF-8"))
                    .append("&version=").append(pros.getProperty(Constants.SMS.VERSION));
            
            sbMd5.append("agentID=").append(pros.getProperty(Constants.SMS.AGENTID)).append("&type=").append(type)
                    .append("&phone=").append(phone).append("&version=").append(pros.getProperty(Constants.SMS.VERSION)).append(pros.getProperty(Constants.SMS.PASSWORD));

            logger.info("加密前sign=" + sbMd5.toString());

            String md5Str = MD5Util.md5(sbMd5.toString(), "UTF-8");


            logger.info("加密后sign=" + md5Str);
            //组织请求URl,请求时
            url.append(pros.getProperty(Constants.SMS.REQUESTADDRESS))
                    .append(sbParam.toString())
                    .append("&sign=")
                    .append(md5Str.toUpperCase());


            Integer result=HttpUtils.requestByGetMethod(url.toString());
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送短信异常", e);
        }

        return "信息发送成功！";
    }

    /**
     * 发送短信，短信的内容不附加前后缀
     *
     * @param phone 电话号
     * @param msg   信息内容
     * @return 处理的信息
     */
    public static String sendSms(String phone, String msg, String type) {
        StringBuilder sbParam = new StringBuilder();
        StringBuilder sbMd5 = new StringBuilder();
        StringBuilder url = new StringBuilder();

        if (org.apache.commons.lang.StringUtils.isBlank(phone)) {
            return "电话号不能为空!";
        }

        if (org.apache.commons.lang.StringUtils.isBlank(msg)) {
            return "发送的信息内容不能为空！";
        }

        try {
            sbParam.append("?agentID=").append(pros.getProperty(Constants.SMS.AGENTID));
            //3快捷支付 ,40注册，41登录，42找回密码，43修改手机号，44绑定手机，45授权开通，46提现，47实名，48 重置密保49修改邮箱，50手机充值，51修改密码,52手机客户端下载,53支付密码修改
            sbParam.append("&type=").append(type).append("&phone=").append(phone).append("&msg=")
                    .append(URLEncoder.encode(msg, "UTF-8"))
                    .append("&version=").append(pros.getProperty(Constants.SMS.VERSION));

            sbMd5.append("agentID=").append(pros.getProperty(Constants.SMS.AGENTID)).append("&type=").append(type)
                    .append("&phone=").append(phone).append("&version=").append(pros.getProperty(Constants.SMS.VERSION)).append(pros.getProperty(Constants.SMS.PASSWORD));

            System.out.println("加密前sign=" + sbMd5.toString());

            String md5Str = MD5Util.md5(sbMd5.toString(), "UTF-8");


            System.out.println("加密后sign=" + md5Str);
            //组织请求URl,请求时
            url.append(pros.getProperty(Constants.SMS.REQUESTADDRESS))
                    .append(sbParam.toString())
                    .append("&sign=")
                    .append(md5Str.toUpperCase());


            Integer result=HttpClientUtils.requestByGetMethod(url.toString());
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送短信异常", e);
        }

        return "信息发送成功！";
    }
}
