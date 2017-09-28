package com.heepay.manage.modules.hgms.util;

import com.heepay.common.util.FastDFSUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描    述：文件上传工具类
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-06-07 10:19:39
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
public class UploadUtil {

    /**
     * 上传文件返回文件地址
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String upLoadFile(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            //图片上传返回图片地址（不包含域名）
            return FastDFSUtils.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
        }
        return "";
    }
}
