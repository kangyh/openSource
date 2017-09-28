/**
 * 
 */
package com.heepay.manage.modules.risk.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.pbc.service.Excel2003POI;
import com.heepay.manage.modules.pbc.service.Excel2013POI;
import com.heepay.manage.modules.pcac.dao.PcacMerchantReportDeleteDao;
import com.heepay.manage.modules.pcac.dao.PcacPersonReportDao;
import com.heepay.manage.modules.pcac.entity.PcacPersonReport;
import com.heepay.manage.modules.risk.dao.RiskBlackorwhiteItemListDao;
import com.heepay.manage.modules.risk.dao.RiskBlackorwhiteListDao;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteItemList;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteList;
import com.heepay.manage.modules.settle.service.util.ExcelService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * @author Administrator
 *
 */
@Service
public class RiskBlackorwhiteListService   extends CrudService<RiskBlackorwhiteListDao, RiskBlackorwhiteList> {
	
	private static final Logger logger = LogManager.getLogger("RiskBlackorwhiteListService");
	@Autowired
	private RiskBlackorwhiteListDao riskBlackorwhiteListDao;
	/**
	 * @方法说明：导出Excel,获取全部数据
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public RiskBlackorwhiteList getRiskBlackorwhiteListById(Integer blackId) {
    	return riskBlackorwhiteListDao.getRiskBlackorwhiteListById(blackId);
    }
    /**
	 * @方法说明：插入黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    @Transactional(readOnly = false)
    public int insertRiskBlackorwhiteList(RiskBlackorwhiteList record){
    	
    	return riskBlackorwhiteListDao.insertRiskBlackorwhiteList(record);
    }
    
    /**
	 * @方法说明：删除黑白名单信息及子表信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    @Transactional(readOnly = false)
    public void deleteRiskBlackorwhiteList(Integer blackId){
    	riskBlackorwhiteListDao.deleteRiskBlackorwhiteList(blackId);
    	riskBlackorwhiteListDao.deleteRiskBlackorwhiteCascadeItem(blackId);
    }
    /**
	 * @方法说明：更新黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    @Transactional(readOnly = false)
    public int updateRiskBlackorwhiteList(RiskBlackorwhiteList record){
    	return riskBlackorwhiteListDao.updateRiskBlackorwhiteList(record);
    }
    
    /**
	 * @方法说明：分页查询黑名单信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	public Model findPageList(Page<RiskBlackorwhiteList> page, 
			RiskBlackorwhiteList riskBlackorwhite,
										  Model model,String pageNo) {
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		page.setPageSize(10);
		
		Page<RiskBlackorwhiteList> ppReport = findPage(page,riskBlackorwhite);
		ppReport.setPageSize(10);
		model.addAttribute("riskBlackorwhite", riskBlackorwhite);
		model.addAttribute("page", ppReport);
		return model;
	}
	public int checkIfRepeatCateAndProd(RiskBlackorwhiteList record) {
		return riskBlackorwhiteListDao.checkIfRepeatCateAndProd(record);
	}
    
    public int checkIfRepeatName(String name){
    	return riskBlackorwhiteListDao.checkIfRepeatName(name);
    }
	
}
