/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.enums.*;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.entity.MerchantTransfer;
import com.heepay.manage.modules.account.rpc.IAccountClient;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.account.service.MerchantTransferService;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.util.FastDFSUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static org.apache.logging.log4j.web.WebLoggerContextUtils.getServletContext;


/**
 *
 * 描    述：账户转账Controller
 *
 * 创 建 者： @author zjx
 * 创建时间：
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/authSpl")
public class SplAuthAccountController extends BaseController {

	@Autowired
	private IAccountClient iAccountClient;

	private static Logger logger = LogManager.getLogger();


	/**
	 * 跳转至鉴权补账页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("account:authSpl:view")
	@RequestMapping(value = "toMerchantUpload")
	public String toMerchantUpload(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/account/authSplAccountUpload";
	}


	/**
	 * 鉴权补商户账务
	 * @param file
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:authSpl:view")
	@RequestMapping(value = "uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
							HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		Workbook workbook = null;
		try {
			InputStream is = file.getInputStream();
			String extensionName = FilenameUtils.getExtension(file.getOriginalFilename());
			if (extensionName.toLowerCase().equals("xls")) {
				workbook = new HSSFWorkbook(is);
			} else if (extensionName.toLowerCase().equals("xlsx")) {
				workbook = new XSSFWorkbook(is);
			}
		}catch (IOException e){
			logger.info("读取鉴权补商户账文件异常,{}",e);
		}
		if(workbook == null){
			redirectAttributes.addAttribute("retMsg","文件上传格式错误");
			return "modules/account/authSplAccountUpload";
		}

		Sheet sheet = workbook.getSheetAt(0);
		int minRowIx = sheet.getFirstRowNum()==0?1:0;
		int maxRowIx = sheet.getLastRowNum();

		if(maxRowIx > 100){
			redirectAttributes.addAttribute("retMsg","最大允许100条");
			return "modules/account/authSplAccountUpload";
		}

		List<String> exRowList = new ArrayList<String>();
		for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
			Row row = sheet.getRow(rowIx);
			short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();
//			merchant_id	auth_record_id	trans_no	            trans_type	fee_amount
//			100011	    1000043	        26171100001498896955	AUTHEN	     0.1
			StringBuilder sb = new StringBuilder();
			int getLength =0;
			for (short colIx = minColIx; colIx < maxColIx; colIx++) {
				Cell cell = row.getCell(new Integer(colIx));
				try {
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							getLength++;
							if(StringUtil.notBlank(sb.toString())){
								sb=sb.append(",");
							}
							sb=sb.append(cell.getStringCellValue());
							break;
						default:
							break;
					}
				}catch (Exception e){
					logger.info("鉴权补商户账务解析行异常,{}",row.getRowNum());
				}
			}
			logger.info("鉴权补商户账务,SB:{}",sb.toString());
			if(getLength == 5){
				String[] st = sb.toString().split(",");
				//merchantId, transType, paymentId, transNo, settleId,channelCode, feeAmount, channelFeeAmount, accountMark, operationSource
				String accountMark = AccountMark.AUTHENTICATION_FEE.getValue();
				if(TransType.MOBILE_CERTIFICATION.getValue().equals(st[3])){
					accountMark = AccountMark.MOBILE_AUTH_FEE.getValue();
				}

				String reCode = iAccountClient.authentication(Long.parseLong(st[0]),st[3],st[2],st[1],"","",st[4],"0",accountMark,AllowSystemType.MANAGE_WEB.getValue());
				if(StringUtil.isBlank(reCode) || !String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(reCode)){
					logger.info("鉴权补商户账务失败，商户:{},交易类型:{},交易号:{}",st[0],st[3],st[1]);
					exRowList.add(row.getRowNum() + ":" + reCode);
				}
			}else{
				exRowList.add(String.valueOf(row.getRowNum()));
				logger.info("鉴权补商户账务解析行异常,{}",row.getRowNum());
			}
		}
		if(exRowList.size() > 0){
			logger.info("鉴权补商户账务异常行数据:{}",exRowList.toString());
		}
		return "redirect:"+ Global.getAdminPath()+"/authSpl/toMerchantUpload/?repage";
	}

	/**
	 * 鉴权补通道账务
	 * @param file
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("account:authSpl:view")
	@RequestMapping(value = "uploadChannelFile")
	public String uploadChannelFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
							 HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		Workbook workbook = null;
		try {
			InputStream is = file.getInputStream();
			String extensionName = FilenameUtils.getExtension(file.getOriginalFilename());
			if (extensionName.toLowerCase().equals("xls")) {
				workbook = new HSSFWorkbook(is);
			} else if (extensionName.toLowerCase().equals("xlsx")) {
				workbook = new XSSFWorkbook(is);
			}
		}catch (IOException e){
			logger.info("读取鉴权补通道账文件异常,{}",e);
		}
		if(workbook == null){
			redirectAttributes.addAttribute("retMsg","文件上传格式错误");
			return "modules/account/authSplAccountUpload";
		}

		Sheet sheet = workbook.getSheetAt(1);
		int minRowIx = sheet.getFirstRowNum()==0?1:0;
		int maxRowIx = sheet.getLastRowNum();

		if(maxRowIx > 100){
			redirectAttributes.addAttribute("retMsg","最大允许100条");
			return "modules/account/authSplAccountUpload";
		}

		List<String> exRowList = new ArrayList<String>();
		for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
			Row row = sheet.getRow(rowIx);
			short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();
//			merchant_id	auth_record_id	trans_no	            trans_type	channel_fee_amount channelCode
//			100011	    1000043	        26171100001498896955	AUTHEN	     0.1		       QAREDQ
			StringBuilder sb = new StringBuilder();
			int getLength =0;
			for (short colIx = minColIx; colIx < maxColIx; colIx++) {
				Cell cell = row.getCell(new Integer(colIx));
				try {
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							getLength++;
							if(StringUtil.notBlank(sb.toString())){
								sb=sb.append(",");
							}
							sb=sb.append(cell.getStringCellValue());
							break;
						default:
							break;
					}
				}catch (Exception e){
					logger.info("鉴权补通道账务解析行异常,{}",row.getRowNum());
				}
			}
			logger.info("鉴权补通道账务,SB:{}",sb.toString());
			if(getLength == 6){
				String[] st = sb.toString().split(",");
				//merchantId, transType, paymentId, transNo, settleId,channelCode, feeAmount, channelFeeAmount, accountMark, operationSource
				String accountMark = AccountMark.AUTHENTICATION_PASSAGEWAY.getValue();
				if(TransType.MOBILE_CERTIFICATION.getValue().equals(st[3])){
					accountMark = AccountMark.MOBILE_AUTH_PASSAGEWAY.getValue();
				}
				String reCode = iAccountClient.authentication(Long.parseLong(st[0]),st[3],st[2],st[1],"",st[5],"0",st[4],accountMark,AllowSystemType.MANAGE_WEB.getValue());
				if(StringUtil.isBlank(reCode) || !String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(reCode)){
					logger.info("鉴权补通道账务失败，商户:{},交易类型:{},交易号:{}",st[0],st[3],st[1]);
					exRowList.add(row.getRowNum() + ":" + reCode);
				}
			}else{
				exRowList.add(String.valueOf(row.getRowNum()));
				logger.info("鉴权补通道账务解析行异常,{}",row.getRowNum());
			}
		}
		if(exRowList.size() > 0){
			logger.info("鉴权补通道账务异常行数据:{}",exRowList.toString());
		}
		return "redirect:"+ Global.getAdminPath()+"/authSpl/toMerchantUpload/?repage";
	}

	/**
	 * 下载鉴权补账模板文件
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 */
	@RequiresPermissions("account:authSpl:view")
	@RequestMapping(value = "downloadTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		try {
			String path = getServletContext().getRealPath("/WEB-INF/鉴权补账模板.xlsx");
			InputStream inStream = new FileInputStream(path);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml;charset=UTF-8");
			String fileName = new String("鉴权补账模板.xlsx".getBytes("utf-8"), "iso-8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			byte[] b = new byte[100];
			int len;
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			inStream.close();
		} catch (IOException e) {
			logger.info("读取鉴权补账模板文件异常:{}",e.toString());
		}finally {

		}
	}
}