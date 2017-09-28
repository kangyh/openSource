package com.heepay.manage.modules.reconciliation.web.util;

import com.heepay.codec.Md5;
import com.heepay.common.util.IpUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.cookie.CookieUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.redis.JedisClusterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 *
 *
 * 描    述：redis 保存查询条件
 *
 * 创 建 者： wangl
 * 创建时间：  2017-06-1210:09 AM
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
public class SaveConditions {

    private static final Logger logger = LogManager.getLogger();

    /**
     * @param name     redis的名称 Object当前映射的对象
     * @param request
     * @param response
     * @return
     */
    public static Object result(Object object, String name, HttpServletRequest request, HttpServletResponse response) {
        //使用redis保存查询条件
        try {
            String cache = request.getParameter("cache");
            JsonMapperUtil json = new JsonMapperUtil();
            name = UserUtils.getUser().getId() + IpUtil.getIpAddr(request) + name;

            name = Md5.encode(name);
            if (StringUtils.isNotBlank(cache)) {
                String value = JedisClusterUtil.getValue(name);
                logger.info("redis读取查询条件--->{参数}" + value + "--->{名称}" + name);
                if (StringUtils.isNotBlank(value)) {
                    object = json.fromJson(value, object.getClass());
                }
            }
            String jsonStr = json.toJson(object);
            JedisClusterUtil.setValue(name, jsonStr, 600);

        } catch (Exception e) {
            logger.error("redis转换异常--->{参数}" + object + "--->{名称}" + name);
        }
        return object;
    }
}
