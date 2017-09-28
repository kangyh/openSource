/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * 描述：通过html模板生成合同工具类
 *
 * 创建者  B.HJ
 * 创建时间 2017-05-12-10:16
 * 创建描述：通过html模板生成合同工具类
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class GenerateContract {

    private static final String HTML = "/configs/compact/";
    private static final String FONT = !System.getProperty("os.name").toLowerCase().contains("window")?"/usr/share/fonts/simhei.ttf":"C:\\Windows\\Fonts\\simhei.ttf";
    private static final String LOGO_PATH = "file://"+ PathUtil.getCurrentPath()+ "/compact/logo.png";

    private static Configuration freemarkerCfg = null;

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    static {
        freemarkerCfg =new Configuration();
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath()));
            logger.info("字体路径："+FONT);
            logger.info("logo图片路径："+LOGO_PATH);
        } catch (IOException e) {
            logger.error("freemarker的模板目录设置时出错",e);
        }
    }

    /**
     *
     * @param data      数据
     * @throws DocumentException
     * @throws com.lowagie.text.DocumentException
     * @throws IOException
     */
    public static Map<String,String> generate(Map<String,Object> data,String contractId) throws DocumentException, com.lowagie.text.DocumentException, IOException {
        Map<String,String> map = new HashMap<>();
        // 加入变量，指定模板，获取渲染后的内容
        String content = freeMarkerRender(data,HTML+contractId+".html");
        // 将需要生成的内容创建到指定目录
        String replace = UUID.randomUUID().toString().replace("-", "")+".pdf";
        String path = PathUtil.getCurrentPath() + File.separator + replace;
        createPdf(content,path);
        map.put("content",content);
        map.put("path",path);
        return map;
    }
    /**
     * freemarker渲染html
     */
    private static String freeMarkerRender(Map<String, Object> data, String htmlTemp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTemp,"UTF-8");
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通过模版获取合同内容错误{}",e);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 创建pdf文件
     * @param content       内容
     * @param dest          目标目录
     * @throws IOException
     * @throws DocumentException
     * @throws com.lowagie.text.DocumentException
     */
    private static void createPdf(String content,String dest) throws IOException, DocumentException, com.lowagie.text.DocumentException {
        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 解析html生成pdf
        render.setDocumentFromString(content);
        //解决图片相对路径的问题
        render.getSharedContext().setBaseURL(LOGO_PATH);
        render.layout();
        render.createPDF(new FileOutputStream(dest));
    }
}
