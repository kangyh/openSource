/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.common;

import com.alibaba.fastjson.JSONObject;
import com.heepay.manage.modules.util.FastDFSUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;


/**
 * 名称：上传Controller
 * <p/>
 * 创建者  白恒剑
 * 创建时间 2016-08-12
 * 创建描述：上传图片到FastDFS
 * <p/>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p/>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Controller
public class UploadController {

    @Value("#{task['fastdfs.domain']}")
    private String domain;

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * 测试上传图片
     *
     * @param file     图片
     * @param response 响应对象
     * @throws Exception
     */
    @RequestMapping(value = "upload")
    public void uploadPic(@RequestParam(required = false) MultipartFile file, HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
/*        String merchantId = judgeOnline.isOnline(response, request);*/
        JSONObject jo = new JSONObject();
        //上传图片
        String path;
        try {
            path = FastDFSUtils.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
        } catch (Exception e) {
            /*logger.error("{}上传图片时出错", merchantId, e);*/
            jo.put("code", "0");
            response.getWriter().write(jo.toString());
            return;
        }
        jo.put("path", path);
        jo.put("allUrl", domain + File.separator + path);
        /*logger.info("用户{}上传了一张图片，地址为{}", merchantId, domain + File.separator + path);*/
        //设置成json
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jo.toString());
    }


}
