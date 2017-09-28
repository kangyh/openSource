package com.heepay.manage.modules.merchant.utils;

import static org.apache.logging.log4j.web.WebLoggerContextUtils.getServletContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;

import com.heepay.enums.CostType;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.RateBusinessType;
import com.heepay.enums.SettleType;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 描述：导出商户信息
 * <p>
 * 创建者  ly
 * 创建时间 2017-01-18-14:34
 * 创建描述：
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Component
public class MerhchantExcel {
    private static final Logger logger= LogManager.getLogger();

    /**
     * @方法说明：商户信息导出方法
     * @时间：2017年1月18日14:43:34
     * @创建人：ly
     */
    @SuppressWarnings({"deprecation" })
    public void exportExcel(String fileName, List<Map<String, Object>> dataset,
                            String[] showField, HttpServletResponse response, HttpServletRequest request){
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //区分IE浏览器和其他浏览器
            if (request.getHeader("User-Agent").contains("MSIE")||request.getHeader("User-Agent").contains("Trident")) {
                fileName = java.net.URLEncoder.encode((fileName + ".xls"), "UTF-8");
            } else {
                fileName = new String((fileName + ".xls").getBytes("UTF-8"),"ISO-8859-1");
            }


            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            // 声明一个工作薄
//            HSSFWorkbook workbook = new HSSFWorkbook();
//            // 生成一个Sheet
//            HSSFSheet sheet = workbook.createSheet(sheetName);
//            //设置样式
//            HSSFCellStyle setBorder = workbook.createCellStyle();
//            setBorder.setFillBackgroundColor(HSSFColor.LAVENDER.index);//背景色
//            setBorder.setFillForegroundColor(HSSFColor.LAVENDER.index);
//            setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
//            //设置字体
//            HSSFFont font = workbook.createFont();
//            font.setFontName("微软雅黑");
//            font.setFontHeightInPoints((short) 10);//设置字体大小
//            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
//            setBorder.setFont(font);//选择需要用到的字体格式
//            sheet.setColumnWidth(3, 2000);
            //产生表格标题行
            //excel模板路径
            String path = getServletContext().getRealPath("/WEB-INF/");
            File fi=new File(path+"商户信息表样式.xls");
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
            //读取excel模板
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            // 生成一个Sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
//            for (short i = 0; i < headers.length; i++) {
//                HSSFCell cell = row.createCell(i);
////                cell.setCellStyle(setBorder);
//                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//                cell.setCellValue(text);
//            }

            int index = 0;
            //循环数据库中的数据信息
            for(Map<String,Object> valMap: dataset) {
                index++;
                HSSFRow row = sheet.getRow(index);
                //根据标题行循环数据库中的数据信息
                for (short i = 0; i < showField.length; i++) {
                    /**
                     * 数据库的数据存在Map中，Map中的key就是字段，
                     * 下面中翻译字段就是对应key翻译该值，之后再根据
                     * 标题行所对应的字段作为key取value值。
                     */
                    HSSFCell cell = row.getCell(i);
                    //翻译部分字段
                    cell.setCellValue(chageCellValue(valMap,showField,index,i));
                }
            }
            workbook.write(out);
        }catch (IOException e) {
            e.printStackTrace();
            logger.error("导出商家信息错误:IO出错{}", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出商家信息错误:{}", e);
        }finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                logger.error("导出商家信息错误{}",e);
            }
        }
    }

    private String chageCellValue(Map<String, Object> valueMap, String[] keys, int index, int i) {
        String cellValue = "";
        String key = keys[i];
        String value = valueMap.get(key) == null ? "" : valueMap.get(key).toString();
        switch (key){
            case "index" :
                cellValue = String.valueOf(index);
                break;
            case "merchantStatus":
                if(MerchantStatus.FREEZED.getValue().equals(value)){
                    cellValue = "禁用";
                }else if(MerchantStatus.NORMAL.getValue().equals(value)){
                    cellValue = "启用";
                }
                break;
            case "mccServer":
                cellValue = MccUtils.getIndustryChildName(value);
                break;
            case "mccDetail":
                cellValue = MccUtils.getMccName(value);
                break;
            case "inchargerId":
                cellValue = UserUtils.get(value) == null ? "":UserUtils.get(value).getName();
                break;
            case "agencyCompanyName":
                cellValue = "";
                break;
            case "settleType":
                cellValue = SettleType.labelOf(value);
                break;
            case "businessType":
                cellValue = RateBusinessType.labelOf(value);
                break;
            case "chargeType":
                cellValue = CostType.labelOf(value);
                break;
            case "fee":
                if(CostType.COUNT.getValue().equals(valueMap.get("chargeType"))){
                    cellValue = valueMap.get("chargeFee").toString();
                }else if(CostType.COUNT.getValue().equals(valueMap.get("chargeType"))){
                    cellValue = valueMap.get("chargeRatio").toString();
                }
                break;
            default:
                cellValue = value;
                break;
        }
        return  cellValue;
    }
}
