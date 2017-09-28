package com.heepay.manage.modules.route.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.*;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.route.service.PayChannelService;
import com.heepay.manage.modules.sys.utils.DictUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 描述：
 * <p>
 * 创建者  ly
 * 创建时间 2017-03-08-15:43
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
@Controller
@RequestMapping(value = "${adminPath}/route/payChannel")
public class ImportExcelController {

    /**
     * Excel 2003
     */
    private final static String XLS = "xls";
    /**
     * Excel 2007
     */
    private final static String XLSX = "xlsx";

    /**
     * 分隔符
     */
    private final static String SEPARATOR = ",";

    @Autowired
    private BankInfoService bankInfoService;

    @Autowired
    private PayChannelService payChannelService;

    /**
     * 由Excel文件的Sheet导出至List
     *
     * @param file
     * @param sheetNum
     * @return
     */
    public static List<String> exportListFromExcel(File file, int sheetNum) throws IOException {

        return exportListFromExcel(new FileInputStream(file),FilenameUtils.getExtension(file.getName()), sheetNum);
    }

    /**
     * 由Excel流的Sheet导出至List
     *
     * @param is
     * @param extensionName
     * @param sheetNum
     * @return
     * @throws IOException
     */
    public static List<String> exportListFromExcel(InputStream is, String extensionName, int sheetNum) throws IOException {

        Workbook workbook = null;

        if (extensionName.toLowerCase().equals(XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (extensionName.toLowerCase().equals(XLSX)) {
            workbook = new XSSFWorkbook(is);
        }

        return exportListFromExcel(workbook, sheetNum);
    }

    /**
     * 由指定的Sheet导出至List
     *
     * @param workbook
     * @param sheetNum
     * @return
     * @throws IOException
     */
    private static List<String> exportListFromExcel(Workbook workbook, int sheetNum) {

        Sheet sheet = workbook.getSheetAt(sheetNum);

        // 解析公式结果
        FormulaEvaluator evaluator = workbook.getCreationHelper()
                .createFormulaEvaluator();

        List<String> list = new ArrayList<String>();

        //获取行数
        int minRowIx = sheet.getFirstRowNum() == 0 ? 1 : 0;
        int maxRowIx = sheet.getLastRowNum();
        //循环行数
        for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
            Row row = sheet.getRow(rowIx);
            StringBuilder sb = new StringBuilder();
            //获取列数
            short minColIx = row.getFirstCellNum();
            short maxColIx = row.getLastCellNum();
            //循环列数
            for (short colIx = minColIx; colIx <= maxColIx; colIx++) {
                //获取单元格
                Cell cell = row.getCell(new Integer(colIx));
                //获取单元格值
                CellValue cellValue = evaluator.evaluate(cell);
                if (cellValue == null) {
                    sb.append(SEPARATOR);
                    continue;
                }
                // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
                // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
                switch (cellValue.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                        sb.append(cellValue.getBooleanValue() + SEPARATOR);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        // 这里的日期类型会被转换为数字类型，需要判别后区分处理
                        if (DateUtil.isCellDateFormatted(cell)) {
                            sb.append(cell.getDateCellValue() + SEPARATOR);
                        } else {
                            sb.append(NumberToTextConverter.toText(cellValue.getNumberValue()) + SEPARATOR);
                        }
                        break;
                    case Cell.CELL_TYPE_STRING:
                        sb.append(cellValue.getStringValue() + SEPARATOR);
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        break;
                    default:
                        break;
                }
            }
            list.add(sb.toString());
        }
        return list;
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, Model model) {
        List<PayChannel> payChannels = Lists.newArrayList();
        try {
            //读取excel数据
            List<String> strings = exportListFromExcel(file.getInputStream(), FilenameUtils.getExtension(file.getOriginalFilename()), 0);
            //将Excel数据转换成paychannel
            payChannels = changeExcel(strings);
            //插入(返回插入成功数据)
            payChannels = insert(payChannels);
            payChannels = EnumView.PayChannelExcel(payChannels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("list", payChannels);
        return "modules/route/payChannelExcelList";
    }

    private List<PayChannel> changeExcel(List<String> strings) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        List<PayChannel> payChannels = Lists.newArrayList();
        for (String string : strings) {
            String[] split = string.split(SEPARATOR);
            PayChannel payChannel = new PayChannel();
            if (split.length <= 0) {
                continue;
            }
            for (int i = 0; i < split.length; i++) {
                switch (i) {
                    case 0://通道合作方
                        payChannel.setChannelPartnerName(split[i]);
                        payChannel.setChannelPartnerCode(DictUtils.getDictValue(split[i], Constants.CHANNEL_PARTNER, ""));
                        break;
                    case 1://支付通道类型
                        payChannel.setChannelTypeName(split[i]);
                        payChannel.setChannelTypeCode(DictUtils.getDictValue(split[i], Constants.CHANNEL_TYPE, ""));
                        break;
                    case 2://对公对私标识
                        if (StringUtils.isBlank(split[i])) {
                            payChannel.setAccountType(AccountType.COMMON.getValue());
                        } else {
                            payChannel.setAccountType(AccountType.getLabel(split[i]));
                        }
                        break;
                    case 3://付款类型
                        if (StringUtils.isBlank(split[i])) {
                            payChannel.setBusinessType(BusinessType.OWNBAK.getValue());
                        } else {
                            payChannel.setBusinessType(BusinessType.getLabel(split[i]));
                        }
                        break;
                    case 4://手续费扣除方式
                        payChannel.setChargeDeductType(ChargeDeductType.getLabel(split[i]));
                        break;
                    case 5://是否退还手续费
                        if ("否".equals(split[i])) {
                            payChannel.setChargeReturnTag("0");
                        }
                        payChannel.setChargeReturnTag("1");
                        break;
                    case 6://有效开始时间
                        payChannel.setEffectStartDate(sdf1.parse(split[i]));
                        break;
                    case 7://有效结束时间
                        payChannel.setEffectEndDate(sdf1.parse(split[i]));
                        break;
                    case 8://通道结算周期
                        payChannel.setOrderSettlePeriod(split[i].replace("T+", ""));
                        break;
                    case 9://手续费结算周期
                        payChannel.setSettlePeriod(split[i]);
                        if (StringUtils.isNotBlank(SettleType.getLabel(split[i]))) {
                            payChannel.setSettleType(SettleType.getLabel(split[i]));
                        } else {
                            payChannel.setSettleType(SettleType.PERIOD.getValue());
                            payChannel.setSettlePeriod(SettlePeriod.getLabel(split[i]));
                        }
                        break;
                    case 10://成本类型
                        payChannel.setCostType(CostType.getLabel(split[i]));
                        break;
                    case 11://成本
                        if (CostType.RATE.getContent().equals(split[10])) {
                            payChannel.setCostRate(split[i]);
                        } else {
                            payChannel.setCostCount(split[i]);
                        }
                        break;
                    case 12://银行名称
                        payChannel.setBankName(split[i]);
                        BankInfo bankInfo = bankInfoService.getBankByBankName(split[i]);
                        System.out.println(bankInfo.getBankNo());
                        if (null != bankInfo) {
                            payChannel.setBankNo(bankInfo.getBankNo());
                        }
                        break;
                    case 13://银行卡类型
                        payChannel.setCardTypeName(split[i]);
                        payChannel.setCardTypeCode(RateBankcardType.getLabel(split[i]));
                        break;
                    case 14://单笔限额
                        payChannel.setPerlimitAmount(split[i]);
                        break;
                    case 15://单日限额
                        payChannel.setDaylimitAmount(split[i]);
                        break;
                    case 16://单月限额
                        if (StringUtils.isNotBlank(split[i])) {
                            payChannel.setMonlimitAmount(split[i]);
                        } else {
                            payChannel.setMonlimitAmount(null);
                        }
                        break;
                    case 17://优先级
                        if ("默认值".equals(split[i])) {
                            payChannel.setSort("1");
                        }
                        break;
                    case 18://所属主体
                        payChannel.setMerchantSubject(split[i]);
                        break;
                    case 19://通道侧商户号
                        payChannel.setMerchantNumber(split[i]);
                        break;
                    default:
                        break;
                }
            }
            payChannel.setStatus(CommonStatus.ENABLE.getValue());
            payChannels.add(payChannel);
        }
        return payChannels;
    }

    /**
     * 插入通道方法
     * ly 2017年3月10日17:23:47
     */
    private List<PayChannel> insert(List<PayChannel> payChannels) {
        List<PayChannel> payChannelSuccess = Lists.newArrayList();
        for (PayChannel payChannel : payChannels) {
            PayChannel payChannelInner = payChannelService.selectByPayChannel(payChannel);
            payChannel.setChannelCode("");
            if (null == payChannelInner) {
                //只有一条通道的优先级为默认
                payChannel = payChannelService.setChannelSort(payChannel);
                try {
                    payChannelService.savePayChannel(payChannel);
                    payChannelSuccess.add(payChannel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return payChannelSuccess;
    }
}
