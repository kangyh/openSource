/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * 名称：FastDFS工具类
 *
 * 创建者  B.HJ
 * 创建时间 2016-08-15-17:19
 * 创建描述：上传图片返回路径
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class FastDFSUtils {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    private FastDFSUtils() {
    }

    /**
     * 上传图片返回
     *
     * @param pic  图片二进制
     * @param name 名字
     * @param size 大小
     * @throws Exception
     * @return 图片的路径（不包括ip和端口）
     */
    //上传图片 返回图片路径
    public static String uploadPic(byte[] pic, String name, Long size) throws Exception {
        //全局设置IP 端口  参数 fdfs_client.conf
        ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
        //设置成功
        try {
            ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
        } catch (Exception e) {
            logger.error("读取fdfs配置文件失败！",e);
        }
        //ip 15683
        TrackerClient trackerClient = new TrackerClient();
        //创建连接
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            logger.error("创建tracker连接时失败！",e);
        }
        StorageServer storageServer = null;
        //连接小弟
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
        //扩展名
        String ext = FilenameUtils.getExtension(name);
        //名称  、大小 、扩展名
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("filename", name);
        metaList[1] = new NameValuePair("fileext", ext);
        metaList[2] = new NameValuePair("filesize", String.valueOf(size));
        //上传图片
        return storageClient1.upload_file1(pic, ext, metaList);
    }
}
