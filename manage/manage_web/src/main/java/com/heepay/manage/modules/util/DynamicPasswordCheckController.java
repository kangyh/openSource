package com.heepay.manage.modules.util;

import com.heepay.codec.Md5;
import com.heepay.common.util.IpUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.DynamicPasswordUtil;

import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.redis.JedisClusterUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描    述：获取动态口令校验结果
 * <p/>
 * 创 建 者： L.M
 * 创建时间： 2016/12/14 15:33
 * 创建描述：
 * <p/>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p/>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Controller
@RequestMapping(value = "${adminPath}/util")
public class DynamicPasswordCheckController {

    /**
     * @方法说明：验证redis是否存在缓存
     * @时间： 2017-06-26 11:10 AM
     * @创建人：wangl
     */
    @RequestMapping(value = "/dynamic/validation")
    @ResponseBody
    public String validation(@RequestParam(value = "url") String url,
                             HttpServletRequest request) {

        String result = result(url, request);
        String value = JedisClusterUtil.getValue(result);
        return value;
    }

    @RequestMapping(value = "dynamic")
    @ResponseBody
    public String dynamic(String DynamicW, @RequestParam(value = "url",required = false) String url,
                          HttpServletRequest request) {

        if (Constants.DYNAMIC_STATUS.equals(DynamicPasswordUtil.check(DynamicW, UserUtils.getUser()))) {

            if(StringUtils.isNotBlank(url)){
                String result = result(url, request);
                String redisRunTime = DictUtils.getDictValue("RedisRunTime","RedisRunTime","");
                JedisClusterUtil.setValue(result,"true",Integer.parseInt(redisRunTime));
            }
            //成功
            return new JsonMapperUtil().toJson("success");
        } else {
            //失败
            return "";
        }
    }


    /**
     * @方法说明：key加密
     * @时间： 2017-06-26 04:23 PM
     * @创建人：wangl
     */
    private String result(String url, HttpServletRequest request){

        //String key = url.substring(0, url.lastIndexOf("/"));
        url =  url + "/" + UserUtils.getUser().getId() + "/" + IpUtil.getIpAddr(request) ;
        String encode = Md5.encode(url);
        return  encode;
    }
}
