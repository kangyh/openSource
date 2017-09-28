package com.heepay.manage.common.config;

import com.google.common.collect.Maps;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;

import java.util.Map;

/**
 * 描    述：
 *
 * 创 建 者： 张俊新
 * 创建时间： 2016年8月19日 下午3:30:28
 * 创建描述：2016/9/5 17:58
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
public class PoiExporExcel {


    /**
     * 当前对象实例
     */
    private static PoiExporExcel poiExporExcel = new PoiExporExcel();


    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader("exportExcel.properties");

    public static final String EXPORT ="export";
    public static final String SPLIT =".";
    public static final String FILE_NAME ="filename";
    public static final String SHEET_NAME ="sheetName";
    public static final String SHEET_MAX_NUMBER ="sheetMaxNumber";
    public static final String HEADER_NAMES ="headerNames";
    public static final String BEAN_ATTRS ="beanAttrs";
    /**
     * 获取当前对象实例
     */
    public static PoiExporExcel getInstance() {
        return poiExporExcel;
    }


    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null){
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }



}
