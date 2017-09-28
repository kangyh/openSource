package com.heepay.prom.common.export;

import org.apache.poi.hssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * 描    述：
 * <p>
 * 创 建 者： 张俊新
 * 创建时间： 2016年8月19日 下午3:30:28
 * 创建描述：2016/9/5 15:58
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
public class PoiExportUtil<T> {

    public void exportExcel(String fileName, String[] headers, String[] beanAttrs, List<T> dataset, OutputStream out,int sheetMaxData) {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个Sheet
        HSSFSheet sheet = workbook.createSheet(fileName);

        //产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();

            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    String textValue = null;
                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    } else if (value instanceof Float) {
                        float fValue = (Float) value;
                        textValue = String.valueOf(fValue);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        double dValue = (Double) value;
                        textValue = String.valueOf(dValue);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        long longValue = (Long) value;
                        cell.setCellValue(longValue);
                    }else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        textValue = sdf.format(date);
                        cell.setCellValue(textValue);
                    }else{
                        textValue = value.toString();
                        cell.setCellValue(textValue);
                    }
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }catch (InvocationTargetException e){
                    e.printStackTrace();
                }catch (NoSuchMethodException e){
                    e.printStackTrace();
                }
            }
            try {
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
