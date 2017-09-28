package com.heepay.manage.modules.hgms.service;

import com.heepay.common.util.IdcardUtils;
import com.heepay.enums.CertificateType;
import com.heepay.enums.MerchantType;
import com.heepay.manage.common.enums.HgmsBankcardOwnerType;
import com.heepay.manage.common.enums.HgmsCompanyType;
import com.heepay.manage.common.enums.HgmsIndustryTypes;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantInfo;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantInfoParseResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 描    述：资金归集商户批量导入Service
 * <p>
 * 创 建 者： @author 郭正新
 * 创建时间：2017年3月25日14:58:16
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

@Service
@Transactional(readOnly = true)
public class HgmsMerchantUploadService {

    @Autowired
    private HgmsMerchantInfoService hgmsMerchantInfoService;

    /**
     * 解析批量导入商户
     *
     * @param excelFile
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public HgmsMerchantInfoParseResult readHgmsMerchantInfoXls(MultipartFile excelFile) throws IOException, ParseException {

        //解析xls文件
        InputStream is = excelFile.getInputStream();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        HSSFFont font = hssfWorkbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);//设置字体大小
        //数据错误 背景样式
        HSSFCellStyle color1 = hssfWorkbook.createCellStyle();
        HSSFPalette palette = hssfWorkbook.getCustomPalette();
        color1.setFillForegroundColor(HSSFColor.RED.index);// 设置背景色 255,0,0 -- 黄色
        color1.setFillPattern((short) 1);//设置填充模式
        color1.setBorderBottom((short) 1);  //下边框
        color1.setBorderLeft((short) 1);// 左边框
        color1.setBorderTop((short) 1);// 上边框
        color1.setBorderRight((short) 1);// 右边框
        color1.setAlignment((short) 2);
        color1.setFont(font); //设置字体
        //设置单元格为文本格式
        HSSFDataFormat format = hssfWorkbook.createDataFormat();
        color1.setDataFormat(format.getFormat("@"));
        //返回结果
        HgmsMerchantInfoParseResult result = new HgmsMerchantInfoParseResult();
        //数据重复 背景样式
        HSSFCellStyle color2 = hssfWorkbook.createCellStyle();
        color2.setBorderBottom((short) 1);  //下边框
        color2.setBorderLeft((short) 1);// 左边框
        color2.setBorderTop((short) 1);// 上边框
        color2.setBorderRight((short) 1);// 右边框
        color2.setAlignment((short) 2);
        color2.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色 196 215 155
        color2.setFillPattern((short) 1);//设置填充模式
        color2.setFont(font); //设置字体
        //解析后的订单列表
        List<HgmsMerchantInfo> list = new ArrayList<HgmsMerchantInfo>();
        //错误提示列表
        List<String> errorList = new ArrayList<>();
        //商户信息重复判断
        HashMap<String, Integer> loginNameMap = new HashMap<>();
        //批量导入商户信息错误的信息
        List<String> errorMsgs = new ArrayList<>();
        for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = sheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            if (!validateData(hssfRow)) {
                continue;
            }
            HgmsMerchantInfo hgmsMerchantInfo = createHgmsMerchantInfo(hssfRow, color1, sheet, rowNum, errorMsgs);
            //验证商户登录名 和 原始已加载的商户 是否存在重复,并且验证商户登录名是否和数据库的商户名重复
            String loginName = hgmsMerchantInfo.getLoginName();
            if (StringUtils.isEmpty(loginName)) {
                String message = "本行记录用户:" + loginName + "数据为空, 请检查，并重新填写";
                //设置背景色
                setHSSFCellBackgroudColor(sheet.getRow(rowNum).getCell(1), color2);
                //添加批注
                setErrorComment(sheet, hssfRow.getCell(1), color2, message, rowNum, 1);
                errorList.add(message);
                continue;
            }
            if (loginNameMap.get(loginName) == null) {
                if (hgmsMerchantInfoService.queryEmailExist(loginName)) {
                    loginNameMap.put(loginName, rowNum);
                } else {
                    String message = "本行记录用户:" + loginName + "与数据库公司账号重复, 请检查，并重新填写";
                    //设置背景色
                    setHSSFCellBackgroudColor(sheet.getRow(rowNum).getCell(1), color2);
                    //添加批注
                    setErrorComment(sheet, hssfRow.getCell(1), color2, message, rowNum, 1);
                    errorList.add(message);
                }
            } else {
                String message = "本行记录用户:" + loginName + "与第" + (rowNum) + "公司账号重复, 请检查";
                //设置背景色
                setHSSFCellBackgroudColor(sheet.getRow(rowNum).getCell(1), color2);
                //添加批注
                setErrorComment(sheet, hssfRow.getCell(1), color2, message, rowNum, 1);
                errorList.add(message);
            }
            list.add(hgmsMerchantInfo);
        }
        //判断文件是否正确, 存在错误
        if (errorList.size() > 0) {
            result.setHssfWorkbook(hssfWorkbook);
            result.setErrorList(errorList);
            result.setResult(false);
            result.setMsg("文件存在错误");
            result.setCreateErrorFile(true);
            return result;
        }
        //判断文件是否正确, 存在错误
        if (errorMsgs.size() > 0) {
            result.setHssfWorkbook(hssfWorkbook);
            result.setErrorList(errorMsgs);
            result.setResult(false);
            result.setMsg("文件存在错误");
            result.setCreateErrorFile(true);
            return result;
        }
        result.setOrderFormNoList(loginNameMap);
        result.setList(list);
        result.setHssfWorkbook(hssfWorkbook);
        return result;
    }

    /**
     * 生成订单对象
     *
     * @param hssfRow
     * @param redBackgroud
     * @param sheet
     * @param rowNum
     * @param errorMsgs
     * @return
     * @throws ParseException
     */
    private HgmsMerchantInfo createHgmsMerchantInfo(HSSFRow hssfRow, HSSFCellStyle redBackgroud, HSSFSheet sheet, int rowNum, List<String> errorMsgs) throws ParseException {
        //构建对象
        HgmsMerchantInfo hgmsMerchantInfo = new HgmsMerchantInfo();
        //异常提示信息
        StringBuilder msg = new StringBuilder();
        //从第二列开始读取
        int colNum = 1;
        //公司账号
        HSSFCell cell = hssfRow.getCell(colNum++);
        String loginName = cell == null ? "" : cell.toString().trim();
        if (StringUtils.isEmpty(loginName)) {
            setErrorPrompt(sheet, cell, redBackgroud, "公司账号数据为空", rowNum, colNum - 1);
            msg.append("第" + colNum + "公司账号数据错误|");

        } else if (!Validator.isEmail(loginName)) {
            setErrorPrompt(sheet, cell, redBackgroud, "公司账号数据非邮箱类型", rowNum, colNum - 1);
            msg.append("第" + colNum + "公司账号数据错误|");
        } else if (!hgmsMerchantInfoService.queryEmailExist(loginName)) {
            setErrorPrompt(sheet, cell, redBackgroud, "公司账号数据已存在", rowNum, colNum - 1);
            msg.append("第" + colNum + "公司账号数据错误|");
        }
        //证件类型
        cell = hssfRow.getCell(colNum++);
        CertificateType certificateType = CertificateType.getBean(cell == null ? "" : cell.toString().trim());
        if (StringUtils.isEmpty(certificateType)) {
            setErrorPrompt(sheet, cell, redBackgroud, "证件类型数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列证件类型数据错误|");
        }
        //单位类型
        cell = hssfRow.getCell(colNum++);
        MerchantType merchantType = MerchantType.getBean(cell == null ? "" : cell.toString().trim());
        if (StringUtils.isEmpty(merchantType)) {
            setErrorPrompt(sheet, cell, redBackgroud, "单位类型数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列单位类型数据错误|");
        }
        //公司名称
        cell = hssfRow.getCell(colNum++);
        String companyName = cell == null ? "" : cell.toString().trim();
        if (!Validator.isCompanyName(companyName)) {
            setErrorPrompt(sheet, cell, redBackgroud, "公司名称数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "公司名称型数据错误|");
        }
        //事业类型
        cell = hssfRow.getCell(colNum++);
        HgmsIndustryTypes industryTypes = HgmsIndustryTypes.getBean(cell == null ? "" : cell.toString().trim());
        if (StringUtils.isEmpty(industryTypes)) {
            setErrorPrompt(sheet, cell, redBackgroud, "事业类型数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列事业类型数据错误|");
        }
        //公司网址
        cell = hssfRow.getCell(colNum++);
        String website = cell == null ? "" : cell.toString().trim();
        if (!StringUtils.isEmpty(website) && !Validator.isUrl2(website)) {
            setErrorPrompt(sheet, cell, redBackgroud, "公司联系电话数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列公司联系电话数据错误|");
        }
        //公司联系电话
        cell = hssfRow.getCell(colNum++);
        String companyPhone = cell == null ? "" : cell.toString().trim();
        if (!Validator.isPhone(companyPhone)) {
            setErrorPrompt(sheet, cell, redBackgroud, "公司联系电话数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列公司联系电话数据错误|");
        }
        //营业执照号码
        cell = hssfRow.getCell(colNum++);
        String businessLicenseNo = cell == null ? "" : cell.toString().trim();
        if (!Validator.isInteger(businessLicenseNo)) {
            setErrorPrompt(sheet, cell, redBackgroud, "营业执照号码数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列营业执照号码数据错误|");
        }
        //营业期限
        cell = hssfRow.getCell(colNum++);
        String businessLicenseEndTime = cell == null ? "" : cell.toString().trim();
        if (!Validator.isDate(businessLicenseEndTime)) {
            setErrorPrompt(sheet, cell, redBackgroud, "营业期限数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列营业期限数据错误|");
        }
        //组织机构代码和税务登记证
        String organizationCode = null;
        String taxRegistrationCertificateNo = null;
        if (!CertificateType.MULTIPLE.getValue().equals(certificateType.getValue())) {
            //组织机构代码
            cell = hssfRow.getCell(colNum++);
            organizationCode = cell == null ? "" : cell.toString().trim();
            if (Validator.isChinese(organizationCode)) {
                setErrorPrompt(sheet, cell, redBackgroud, "组织机构代码数据错误", rowNum, colNum - 1);
                msg.append("第" + colNum + "列组织机构代码数据错误|");
            }
            //税务登记证号码
            cell = hssfRow.getCell(colNum++);
            taxRegistrationCertificateNo = cell == null ? "" : cell.toString().trim();
            if (!Validator.isInteger(taxRegistrationCertificateNo)) {
                setErrorPrompt(sheet, cell, redBackgroud, "税务登记证号码数据错误", rowNum, colNum - 1);
                msg.append("第" + colNum + "列税务登记证号码数据错误|");
            }
        } else {
            //组织机构代码
            colNum++;
            //税务登记证号码
            colNum++;
        }

        //ICP备案号
        cell = hssfRow.getCell(colNum++);
        String ipcNo = cell == null ? "" : cell.toString().trim();
        if (!Validator.isInteger(ipcNo)) {
            setErrorPrompt(sheet, cell, redBackgroud, "ICP备案号数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列ICP备案号数据错误|");
        }
        //员工人数
        cell = hssfRow.getCell(colNum++);
        String employeeAccount = cell == null ? "" : cell.toString().trim();
        if (!Validator.isInteger(employeeAccount)) {
            setErrorPrompt(sheet, cell, redBackgroud, "员工人数数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列员工人数数据错误|");
        }
        //经营范围
        cell = hssfRow.getCell(colNum++);
        String businessScope = cell == null ? "" : cell.toString().trim();
        if (StringUtils.isEmpty(businessScope)) {
            setErrorPrompt(sheet, cell, redBackgroud, "经营范围数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列经营范围数据错误|");
        }
        //公司注册地址
        cell = hssfRow.getCell(colNum++);
        String businessAddress = cell == null ? "" : cell.toString().trim();
        if (StringUtils.isEmpty(businessAddress)) {
            setErrorPrompt(sheet, cell, redBackgroud, "公司注册地址数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列公司注册地址数据错误|");
        }
        //法人姓名
        cell = hssfRow.getCell(colNum++);
        String legalRepresentative = cell == null ? "" : cell.toString().trim();
        if (!Validator.isChinese(legalRepresentative)) {
            setErrorPrompt(sheet, cell, redBackgroud, "法人姓名数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列法人姓名数据错误|");
        }
        //法人手机号
        cell = hssfRow.getCell(colNum++);
        String legalMobile = cell == null ? "" : cell.toString().trim();
        if (!Validator.isMobile(legalMobile)) {
            setErrorPrompt(sheet, cell, redBackgroud, "法人手机号数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列法人手机号数据错误|");
        }
        //法人代表身份证号
        cell = hssfRow.getCell(colNum++);
        String legalIdcard = cell == null ? "" : cell.toString().trim();
        if (!IdcardUtils.validateCard(legalIdcard)) {
            setErrorPrompt(sheet, cell, redBackgroud, "法人代表身份证号数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列法人代表身份证号数据错误|");
        }
        //法人证件有效期
        cell = hssfRow.getCell(colNum++);
        String legalCertificateEndTime = cell == null ? "" : cell.toString().trim();
        if (!Validator.isDate(legalCertificateEndTime)) {
            setErrorPrompt(sheet, cell, redBackgroud, "法人证件有效期数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列法人证件有效期数据错误|");
        }
        //法人电子邮箱
        cell = hssfRow.getCell(colNum++);
        String legalPersionEmail = cell == null ? "" : cell.toString().trim();
        if (!Validator.isEmail(legalPersionEmail)) {
            setErrorPrompt(sheet, cell, redBackgroud, "法人电子邮箱数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列法人电子邮箱数据错误|");
        }
        //法人联系地址
        cell = hssfRow.getCell(colNum++);
        String legalPersionAddress = cell == null ? "" : cell.toString().trim();
        if (StringUtils.isEmpty(legalPersionAddress)) {
            setErrorPrompt(sheet, cell, redBackgroud, "法人联系地址数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列法人联系地址数据错误|");
        }

        //联系人姓名
        cell = hssfRow.getCell(colNum++);
        String contactor = cell == null ? "" : cell.toString().trim();
        if (!Validator.isChinese(contactor)) {
            setErrorPrompt(sheet, cell, redBackgroud, "联系人姓名数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列联系人姓名数据错误|");
        }
        //联系人手机号
        cell = hssfRow.getCell(colNum++);
        String contactorPhone = cell == null ? "" : cell.toString().trim();
        if (!Validator.isMobile(contactorPhone)) {
            setErrorPrompt(sheet, cell, redBackgroud, "联系人手机号数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列联系人手机号数据错误|");
        }
        //联系人代表身份证号
        cell = hssfRow.getCell(colNum++);
        String contactorIdcardNo = cell == null ? "" : cell.toString().trim();
        if (!IdcardUtils.validateCard(contactorIdcardNo)) {
            setErrorPrompt(sheet, cell, redBackgroud, "联系人代表身份证号数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列联系人代表身份证号数据错误|");
        }
        //联系人证件有效期
        cell = hssfRow.getCell(colNum++);
        String contactorCertificateEndTime = cell == null ? "" : cell.toString().trim();
        if (!Validator.isDate(contactorCertificateEndTime)) {
            setErrorPrompt(sheet, cell, redBackgroud, "联系人证件有效期数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列联系人证件有效期数据错误|");
        }
        //联系人电子邮箱
        cell = hssfRow.getCell(colNum++);
        String contactorPersionEmail = cell == null ? "" : cell.toString().trim();
        if (!Validator.isEmail(contactorPersionEmail)) {
            setErrorPrompt(sheet, cell, redBackgroud, "联系人电子邮箱数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列联系人电子邮箱数据错误|");
        }
        //联系人联系地址
        cell = hssfRow.getCell(colNum++);
        String contactorPersionAddress = cell == null ? "" : cell.toString().trim();
        if (StringUtils.isEmpty(contactorPersionAddress)) {
            setErrorPrompt(sheet, cell, redBackgroud, "联系人联系地址数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列联系人联系地址数据错误|");
        }
        //银行卡持卡人类型,默认为:1
        cell = hssfRow.getCell(colNum++);
        HgmsBankcardOwnerType bankcardOwnerType = HgmsBankcardOwnerType.getBean(StringUtils.isEmpty(cell.toString().trim()) ? "1" : cell.toString().trim());
        if (StringUtils.isEmpty(bankcardOwnerType)) {
            setErrorPrompt(sheet, cell, redBackgroud, "银行卡持卡人类型数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列银行卡持卡人类型数据错误|");
        }
        //银行卡号
        cell = hssfRow.getCell(colNum++);
        String bankcardNo = cell == null ? "" : cell.toString().trim();
        if (!Validator.isInteger(bankcardNo) || bankcardNo.length() <= 14 || bankcardNo.length() >= 21) {
            setErrorPrompt(sheet, cell, redBackgroud, "银行卡号数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列银行卡号数据错误|");
        }
        //银行预留手机号
        cell = hssfRow.getCell(colNum++);
        String bankcardOwnerMobile = cell == null ? "" : cell.toString().trim();
        if (!Validator.isMobile(bankcardOwnerMobile)) {
            setErrorPrompt(sheet, cell, redBackgroud, "银行预留手机号数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列银行预留手机号数据错误|");
        }
        //银行卡有效期
        cell = hssfRow.getCell(colNum++);
        String bankcardExpiredDate = cell == null ? "" : cell.toString().trim();
        if (!Validator.isInteger(bankcardExpiredDate)) {
            setErrorPrompt(sheet, cell, redBackgroud, "银行卡有效期数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列银行卡有效期数据错误|");
        }
        //银行卡持卡人名称
        cell = hssfRow.getCell(colNum++);
        String bankcardOwnerName = cell == null ? "" : cell.toString().trim();
        if (!Validator.isChinese(bankcardOwnerName)) {
            setErrorPrompt(sheet, cell, redBackgroud, "银行卡持卡人名称数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列银行卡持卡人名称数据错误|");
        }
        //此卡人身份证
        cell = hssfRow.getCell(colNum++);
        String bankcardOwnerIdcard = cell == null ? "" : cell.toString().trim();
        if (bankcardOwnerType == null) {
            setErrorPrompt(sheet, cell, redBackgroud, "银行卡持卡人类型数据错误", rowNum, colNum - 1);
            msg.append("第" + colNum + "列银行卡持卡人类型数据错误|");
        } else if ("0".equals(bankcardOwnerType.getValue())) {
            if (!IdcardUtils.validateCard(bankcardOwnerIdcard)) {
                setErrorPrompt(sheet, cell, redBackgroud, "此卡人身份证数据错误", rowNum, colNum - 1);
                msg.append("第" + colNum + "列此卡人身份证数据错误|");
            }
        }


        //校验数据完毕, 存在错误已做标记
        if (msg.length() > 0) {
            //返回商户登录名和错误信息
            errorMsgs.add(loginName + ":" + msg.toString());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hgmsMerchantInfo.setLoginName(loginName);
        hgmsMerchantInfo.setCertificateType(certificateType == null ? null : certificateType.getValue());
        hgmsMerchantInfo.setType(merchantType == null ? null : merchantType.getValue());
        hgmsMerchantInfo.setCompanyName(companyName);
        hgmsMerchantInfo.setIndustryTypes(industryTypes == null ? null : industryTypes.getValue());
        hgmsMerchantInfo.setWebsite(website);
        hgmsMerchantInfo.setCompanyPhone(companyPhone);
        hgmsMerchantInfo.setBusinessLicenseNo(businessLicenseNo);
        hgmsMerchantInfo.setBusinessLicenseEndTime(simpleDateFormat.parse(businessLicenseEndTime + " 00:00:00"));
        hgmsMerchantInfo.setOrganizationCode(organizationCode);
        hgmsMerchantInfo.setTaxRegistrationCertificateNo(taxRegistrationCertificateNo);
        hgmsMerchantInfo.setIpcNo(ipcNo);
        hgmsMerchantInfo.setEmployeeAccount(employeeAccount);
        hgmsMerchantInfo.setBusinessScope(businessScope);
        hgmsMerchantInfo.setBusinessAddress(businessAddress);
        hgmsMerchantInfo.setLegalRepresentative(legalRepresentative);
        hgmsMerchantInfo.setLegalMobile(legalMobile);
        hgmsMerchantInfo.setLegalIdcard(legalIdcard);
        hgmsMerchantInfo.setLegalCertificateEndTime(simpleDateFormat.parse(legalCertificateEndTime + " 00:00:00"));
        hgmsMerchantInfo.setLegalPersionEmail(legalPersionEmail);
        hgmsMerchantInfo.setLegalPersionAddress(legalPersionAddress);
        hgmsMerchantInfo.setContactor(contactor);
        hgmsMerchantInfo.setContactorPhone(contactorPhone);
        hgmsMerchantInfo.setContactorIdcardNo(contactorIdcardNo);
        hgmsMerchantInfo.setContactorCertificateEndTime(simpleDateFormat.parse(contactorCertificateEndTime + " 00:00:00"));
        hgmsMerchantInfo.setContactorPersionEmail(contactorPersionEmail);
        hgmsMerchantInfo.setContactorPersionAddress(contactorPersionAddress);
        hgmsMerchantInfo.setBankcardOwnerType(bankcardOwnerType == null ? null : bankcardOwnerType.getValue());
        hgmsMerchantInfo.setBankcardNo(bankcardNo);
        hgmsMerchantInfo.setBankcardOwnerMobile(bankcardOwnerMobile);
        hgmsMerchantInfo.setBankcardExpiredDate(bankcardExpiredDate);
        hgmsMerchantInfo.setBankcardOwnerName(bankcardOwnerName);
        hgmsMerchantInfo.setBankcardOwnerIdcard(bankcardOwnerIdcard);
        hgmsMerchantInfo.setCompanyType(HgmsCompanyType.ENTERPRISE.getValue());
        return hgmsMerchantInfo;
    }

    /**
     * 验证是否为有效数据行
     *
     * @param hssfRow 行
     * @return true 有效数据行,  false 无效行
     */
    private boolean validateData(HSSFRow hssfRow) {
        //验证本行是否为有效数据行
        int count = 0;
        for (int i = 0; i < 33; i++) {
            HSSFCell hssfCell = hssfRow.getCell(i);
            if (hssfCell == null || StringUtils.isEmpty(hssfCell.toString().trim())) {
                count++;
            }
        }
        return count < 30;
    }


    /**
     * 数据错误设置错误提示
     *
     * @param sheet         当前工作表
     * @param cell          单元格
     * @param cellStyle     样式
     * @param promptContent 提示内容
     * @param row           行
     * @param col           列
     */
    private void setErrorPrompt(HSSFSheet sheet, HSSFCell cell, HSSFCellStyle cellStyle, String promptContent, int row, int col) {
        setHSSFCellBackgroudColor(cell, cellStyle);
        //改为批注
        setCommentNotAppend(sheet, promptContent, row, col);
    }

    /**
     * @param sheet     表格页
     * @param cell      单元格
     * @param cellStyle 单元格样式
     * @param comment   批注内容
     * @param row       行
     * @param col       列
     */
    private void setErrorComment(HSSFSheet sheet, HSSFCell cell, HSSFCellStyle cellStyle, String comment, int row, int col) {
        setHSSFCellBackgroudColor(cell, cellStyle);
        setComment(sheet, comment, row, col);
    }

    /**
     * @param sheet 表格页
     * @param msg   批注信息
     * @param row   行
     * @param col   列
     */
    private void setComment(HSSFSheet sheet, String msg, int row, int col) {
        HSSFCell cell = sheet.getRow(row).getCell(col);
        Comment comment = cell.getCellComment();
        String content = msg;
        if (comment != null) {
            RichTextString str = comment.getString();
            if (!msg.equals(str.getString())) {
                content = str.getString() + ";\n" + content;
            }
            cell.removeCellComment();
        }
        HSSFPatriarch p = sheet.createDrawingPatriarch();
        //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
        HSSFComment c = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) col, row, (short) (col + 3), row + 5));
        //输入批注信息
        c.setString(new HSSFRichTextString(content));
        //添加作者,选中B5单元格,看状态栏
        c.setAuthor("资金归集:");
        cell.setCellComment(c);
    }

    /**
     * 设置单元格背景色
     *
     * @param cell      单元格
     * @param cellStyle 样式
     */
    private void setHSSFCellBackgroudColor(HSSFCell cell, HSSFCellStyle cellStyle) {
        cell.setCellStyle(cellStyle);
    }


    /**
     * @param sheet 表格页
     * @param msg   批注信息
     * @param row   行
     * @param col   列
     */
    private void setCommentNotAppend(HSSFSheet sheet, String msg, int row, int col) {
        HSSFCell cell = sheet.getRow(row).getCell(col);
        Comment comment = cell.getCellComment();
        if (comment != null) {
            cell.removeCellComment();
        }
        HSSFPatriarch p = sheet.createDrawingPatriarch();
        //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
        HSSFComment c = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) col, row, (short) (col + 3), row + 5));
        //输入批注信息
        c.setString(new HSSFRichTextString(msg));
        //添加作者,选中B5单元格,看状态栏
        c.setAuthor("汇聚财:");
        cell.setCellComment(c);
    }

    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return by;
    }
}
