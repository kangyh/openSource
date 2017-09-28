/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.rpc.service.FileStorageService;
import com.heepay.manage.rpc.service.Response;
import com.heepay.manage.rpc.utils.FastDFSUtils;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

/**
 *
 * 描述：
 *
 * 创建者  B.HJ
 * 创建时间 2017-01-03-17:37
 * 创建描述：
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Component
@RpcService(name = "fileStorageServiceImpl", processor = FileStorageService.Processor.class)
public class FileStorageServiceImpl implements FileStorageService.Iface{

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();


    @Override
    public Response WriteFile(String fileName, String schema, ByteBuffer writeBuffer, long length) throws TException {
        Response response = new Response();
        String path;
        try {
            path = FastDFSUtils.uploadPic(writeBuffer.array(), fileName, length);
        } catch (Exception e) {
            logger.error("上传图片时出错",e);
            response.setCode("1001");
            response.setMsg("上传时异常，请查看日志或联系相关开发人员");
            return response;
        }
        response.setCode("1000");
        response.setMsg(path);
        return response;
    }
}
