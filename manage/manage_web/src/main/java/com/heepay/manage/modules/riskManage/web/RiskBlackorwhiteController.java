/**
 * 
 */
package com.heepay.manage.modules.riskManage.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.RegExpression;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.RiskBwCategory;
import com.heepay.manage.common.enums.RiskBwStatus;
import com.heepay.manage.common.enums.RiskBwType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.merchant.rpc.client.RiskServiceClient;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteItemList;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteItemVo;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteList;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteVo;
import com.heepay.manage.modules.risk.service.RiskBlackorwhiteItemService;
import com.heepay.manage.modules.risk.service.RiskBlackorwhiteListService;
import com.heepay.manage.modules.sys.utils.UserUtils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * @author 李震
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/risk/riskBlackorwhite/view")
public class RiskBlackorwhiteController extends BaseController {
private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private ProductCService productCService;
	@Autowired
	private RiskBlackorwhiteListService riskBlackorwhiteListService;
	@Autowired
	private RiskBlackorwhiteItemService riskBlackorwhiteItemService;
	@Autowired
	private RiskServiceClient riskServiceClient;
	private static JsonConfig jsonConfig ;
	static {
		jsonConfig = new JsonConfig();
        PropertyFilter filter = new PropertyFilter() {
                public boolean apply(Object object, String fieldName, Object fieldValue) {
                	return null == fieldValue || "".equals(fieldValue);
                }
        };
        jsonConfig.setJsonPropertyFilter(filter);
	}
	/**
	 * 
	 * @方法说明：
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskBlackorwhiteList riskBlackorwhite, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		try {
			String pageNo = request.getParameter("pageNo");
			String searchFlag = request.getParameter("searchFlag");
			if( "1".equals(searchFlag)){
				riskBlackorwhite = new RiskBlackorwhiteList();
				
				riskBlackorwhite.setBeginCreTime(DateUtil.stringToDate(request.getParameter("beginCreTime")));
				riskBlackorwhite.setEndCreTime(DateUtil.stringToDate(request.getParameter("endCreTime")));
				riskBlackorwhite.setBeginUpdTime(DateUtil.stringToDate(request.getParameter("beginUpdTime")));
				riskBlackorwhite.setEndUpdTime(DateUtil.stringToDate(request.getParameter("endUpdTime")));
				riskBlackorwhite.setBlackId(StringUtil.isBlank(request.getParameter("blackId"))?null:Integer.parseInt(request.getParameter("blackId")));
				riskBlackorwhite.setName(request.getParameter("name"));
				riskBlackorwhite.setType(request.getParameter("type"));
				riskBlackorwhite.setStatus(request.getParameter("status"));
				riskBlackorwhite.setProductCode(request.getParameter("productCode"));
				riskBlackorwhite.setCategory(request.getParameter("category"));
			}
				model = riskBlackorwhiteListService.findPageList(new Page<RiskBlackorwhiteList>(request, response), riskBlackorwhite,
						model,pageNo);
			
			model.addAttribute("enumMap", this.getEnumMapForQuery());
			model.addAttribute("riskBlackorwhite", riskBlackorwhite);
			return "modules/riskManage/riskBlackorwhiteQuery";
		} catch (Exception e) {
			logger.error("PcacPersonReportController list has a error:{查询黑白名单信息异常}", e);
			throw new Exception(e);
		}
	}
	/**
	 * 
	 * @方法说明：初始化添加页面
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value = "saveInit")
	public String saveInit( HttpServletRequest request,HttpServletResponse response ) throws Exception{
		String ifItem = request.getParameter("ifItem");
		request.setAttribute("enumMap", this.getEnumMapForQuery());
		String blackItemValue = request.getParameter("blackItemValue_q");
		String beginCreTime = request.getParameter("beginCreTime_q");
		String endCreTime = request.getParameter("endCreTime_q");
		String beginUpdTime = request.getParameter("beginUpdTime_q");
		String endUpdTime = request.getParameter("endUpdTime_q");
		String blackId = request.getParameter("blackId_q");

		String name = request.getParameter("name_q");
		String type = request.getParameter("type_q");
		String status = request.getParameter("status_q");
		String productCode = request.getParameter("productCode_q");
		String category = request.getParameter("category_q");
		if( "no".equals(ifItem) ){
			RiskBlackorwhiteList riskBlackorwhite = null;
			riskBlackorwhite = new RiskBlackorwhiteList();
			riskBlackorwhite.setBeginCreTime(DateUtil.stringToDate(beginCreTime));
			riskBlackorwhite.setEndCreTime(DateUtil.stringToDate(endCreTime));
			riskBlackorwhite.setBeginUpdTime(DateUtil.stringToDate(beginUpdTime));
			riskBlackorwhite.setEndUpdTime(DateUtil.stringToDate(endUpdTime));
			riskBlackorwhite.setBlackId(StringUtil.isBlank(blackId)?null:Integer.parseInt(blackId));
			riskBlackorwhite.setName(name);
			riskBlackorwhite.setType(type);
			riskBlackorwhite.setStatus(status);
			riskBlackorwhite.setProductCode(productCode);
			riskBlackorwhite.setCategory(category);
			request.setAttribute("riskBlackorwhite", riskBlackorwhite);
			
			
			return "modules/riskManage/riskBlackorwhiteAdd";
		}else{
			request.setAttribute("rbwInfo", riskBlackorwhiteListService.getRiskBlackorwhiteListById(  Integer.parseInt( blackId ) ) ) ;
			RiskBlackorwhiteItemList riskBlackorwhiteItem = null;
			riskBlackorwhiteItem = new RiskBlackorwhiteItemList();
			riskBlackorwhiteItem.setBlackItemValue(blackItemValue);
			riskBlackorwhiteItem.setBeginCreTime(DateUtil.stringToDate(beginCreTime));
			riskBlackorwhiteItem.setEndCreTime(DateUtil.stringToDate(endCreTime));
			riskBlackorwhiteItem.setBeginUpdTime(DateUtil.stringToDate(beginUpdTime));
			riskBlackorwhiteItem.setEndUpdTime(DateUtil.stringToDate(endUpdTime));
			riskBlackorwhiteItem.setBlackId(Integer.parseInt(blackId));
			riskBlackorwhiteItem.setStatus(status);
			request.setAttribute("riskBlackorwhiteItem", riskBlackorwhiteItem);
			
			
			return "modules/riskManage/riskBlackorwhiteItemAdd";
		}
	}
	/**
	 * 
	 * @方法说明：初始化更新页面
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value = "updateInit")
	public String updateInit( HttpServletRequest request,HttpServletResponse response ) throws Exception{
		String ifItem = request.getParameter("ifItem");
		request.setAttribute("enumMap", this.getEnumMapForQuery());
		String blackItemValue = request.getParameter("blackItemValue_q");
		String beginCreTime = request.getParameter("beginCreTime_q");
		String endCreTime = request.getParameter("endCreTime_q");
		String beginUpdTime = request.getParameter("beginUpdTime_q");
		String endUpdTime = request.getParameter("endUpdTime_q");
		String blackId = request.getParameter("blackId_q");
		String blackId_key = request.getParameter("blackId");
		String name = request.getParameter("name_q");
		String type = request.getParameter("type_q");
		String status = request.getParameter("status_q");
		String productCode = request.getParameter("productCode_q");
		String category = request.getParameter("category_q");
		if( "no".equals(ifItem) ){
			RiskBlackorwhiteList riskBlackorwhite = null;
			riskBlackorwhite = new RiskBlackorwhiteList();
			riskBlackorwhite.setBeginCreTime(DateUtil.stringToDate(beginCreTime));
			riskBlackorwhite.setEndCreTime(DateUtil.stringToDate(endCreTime));
			riskBlackorwhite.setBeginUpdTime(DateUtil.stringToDate(beginUpdTime));
			riskBlackorwhite.setEndUpdTime(DateUtil.stringToDate(endUpdTime));
			riskBlackorwhite.setBlackId(StringUtil.isBlank(blackId)?null:Integer.parseInt(blackId));
			riskBlackorwhite.setName(name);
			riskBlackorwhite.setType(type);
			riskBlackorwhite.setStatus(status);
			riskBlackorwhite.setProductCode(productCode);
			riskBlackorwhite.setCategory(category);
			request.setAttribute("riskBlackorwhite", riskBlackorwhite);
			RiskBlackorwhiteList riskBlackorwhite_u = null;
			riskBlackorwhite_u = riskBlackorwhiteListService.getRiskBlackorwhiteListById(Integer.parseInt(blackId_key));
			request.setAttribute("riskBlackorwhite_u", riskBlackorwhite_u);
			return "modules/riskManage/riskBlackorwhiteUpdate";
		}else{
			String blackItemId = request.getParameter("blackItemId");
			RiskBlackorwhiteItemList riskBlackorwhiteItem = null;
			riskBlackorwhiteItem = new RiskBlackorwhiteItemList();
			riskBlackorwhiteItem.setBlackItemValue(blackItemValue);
			riskBlackorwhiteItem.setBeginCreTime(DateUtil.stringToDate(beginCreTime));
			riskBlackorwhiteItem.setEndCreTime(DateUtil.stringToDate(endCreTime));
			riskBlackorwhiteItem.setBeginUpdTime(DateUtil.stringToDate(beginUpdTime));
			riskBlackorwhiteItem.setEndUpdTime(DateUtil.stringToDate(endUpdTime));
			riskBlackorwhiteItem.setBlackId(Integer.parseInt(blackId));
			riskBlackorwhiteItem.setStatus(status);
			request.setAttribute("riskBlackorwhiteItem", riskBlackorwhiteItem);
			RiskBlackorwhiteItemList riskBlackorwhiteItem_u = null;
			riskBlackorwhiteItem_u = riskBlackorwhiteItemService.getRiskBlackorwhiteItemById(Integer.parseInt(blackItemId));
			request.setAttribute("riskBlackorwhiteItem_u", riskBlackorwhiteItem_u);
			request.setAttribute("rbwInfo", riskBlackorwhiteListService.getRiskBlackorwhiteListById( riskBlackorwhiteItem_u.getBlackId() ) ) ;
			return "modules/riskManage/riskBlackorwhiteItemUpdate";
		}
	}
	/**
	 * 
	 * @方法说明：添加黑白名单
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save( HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
			logger.info("黑白名单------>{添加}----->{开始}");
			RiskBlackorwhiteVo riskBlackorwhite = null; riskBlackorwhite = new RiskBlackorwhiteVo();
			String currentDate = DateUtil.getTodayYYYYMMDD_HHMMSS();
			String username = UserUtils.getUser().getName();
			riskBlackorwhite.setCreateTime(  currentDate );
			riskBlackorwhite.setCreateAuthor(username);
			riskBlackorwhite.setUpdateTime(currentDate);
			riskBlackorwhite.setUpdateAuthor(username);
			riskBlackorwhite.setCategory(request.getParameter("category"));
			riskBlackorwhite.setName(  request.getParameter("name") );
			riskBlackorwhite.setType(request.getParameter("type"));
			riskBlackorwhite.setProductCode(request.getParameter("productCode"));
			riskBlackorwhite.setStatus(request.getParameter("status"));
			riskBlackorwhite.setDesc( request.getParameter("desc") );
			
			RiskBlackorwhiteList checkVo = null; checkVo = new RiskBlackorwhiteList();
			checkVo.setCategory(riskBlackorwhite.getCategory());
			checkVo.setProductCode(riskBlackorwhite.getProductCode());
			int ifRepCp = riskBlackorwhiteListService.checkIfRepeatCateAndProd(checkVo);
			if(ifRepCp > 0) {
				return  "添加失败!产品名称或名单分类重复";
			}
			int ifRepName = riskBlackorwhiteListService.checkIfRepeatName(riskBlackorwhite.getName());
			if(ifRepName > 0) {
				return  "添加失败!名单名称不能重复";
			}
			
			String returnStr = riskServiceClient.addRiskBlackorwhite(JSONObject.fromObject(riskBlackorwhite).toString());
			//int num = riskBlackorwhiteListService.insertRiskBlackorwhiteList(riskBlackorwhite);
			logger.info("黑白名单------>{添加}----->{结束}");
			String msg = "";
			if( "1".equals(returnStr)){
				msg = "添加成功!";
			}else{
				msg = "添加失败!";
			}
			return msg;
	}
	/**
	 * 
	 * @方法说明：更新黑白名单
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public String update( HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
			logger.info("黑白名单------>{更新}----->{开始}");
			RiskBlackorwhiteVo riskBlackorwhite = null; riskBlackorwhite = new RiskBlackorwhiteVo();
			String currentDate =  DateUtil.getTodayYYYYMMDD_HHMMSS() ;
			String username = UserUtils.getUser().getName();
			String blackId = request.getParameter("blackId");
			if( StringUtil.isBlank(blackId)) {
				return "更新失败！参数为空";
			}
			RiskBlackorwhiteList temp = null;
			temp = riskBlackorwhiteListService.getRiskBlackorwhiteListById(Integer.parseInt(blackId));
			riskBlackorwhite.setCategory(temp.getCategory());
			riskBlackorwhite.setCreateAuthor(temp.getCreateAuthor());
			riskBlackorwhite.setCreateTime( DateUtil.dateToString(temp.getCreateTime())   );
			riskBlackorwhite.setProductCode(temp.getProductCode());
			riskBlackorwhite.setType(temp.getType());
			riskBlackorwhite.setName(temp.getName());
			//下面是更新的字段
			riskBlackorwhite.setBlackId(  Integer.parseInt( blackId )  );
			riskBlackorwhite.setStatus(request.getParameter("status"));
			riskBlackorwhite.setDesc(request.getParameter("desc"));
			riskBlackorwhite.setUpdateAuthor(username);
			riskBlackorwhite.setUpdateTime(currentDate);
			
			
			String jsonStr = JSONObject.fromObject(riskBlackorwhite,jsonConfig).toString();
			String returnStr = riskServiceClient.updateRiskBlackorwhite(  jsonStr  );
//			int num = riskBlackorwhiteListService.updateRiskBlackorwhiteList(riskBlackorwhite);
			logger.info("黑白名单------>{更新}----->{结束}");
			String msg = "";
			if( "1".equals(returnStr) ){
				msg = "更新成功!";
			}else{
				msg = "更新失败!";
			}
			return msg;
	}
	/**
	 * 
	 * @方法说明：删除黑白名单
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete( HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
			logger.info("黑白名单------>{删除}----->{开始}");
			String blackId = request.getParameter("blackId");
			if( StringUtil.isBlank(blackId)){
				logger.info("黑白名单------>{删除}----->{结束}删除失败!编号为空");
				return  "删除失败!编号为空";
			}
			//级联删除
			try {
				RiskBlackorwhiteList temp = null;
				temp = riskBlackorwhiteListService.getRiskBlackorwhiteListById(Integer.parseInt(blackId));
				RiskBlackorwhiteVo riskBlackorwhite = null; 
				riskBlackorwhite = new RiskBlackorwhiteVo();
				riskBlackorwhite.setBlackId(Integer.parseInt(blackId));
				riskBlackorwhite.setCategory(temp.getCategory());
				riskBlackorwhite.setCreateAuthor(temp.getCreateAuthor());
				riskBlackorwhite.setDesc(temp.getDesc());
				riskBlackorwhite.setCreateTime( DateUtil.dateToString(temp.getCreateTime())   );
				riskBlackorwhite.setProductCode(temp.getProductCode());
				riskBlackorwhite.setStatus(temp.getStatus());
				riskBlackorwhite.setType(temp.getType());
				riskBlackorwhite.setUpdateAuthor(temp.getUpdateAuthor());
				riskBlackorwhite.setUpdateTime( DateUtil.dateToString(temp.getUpdateTime() ) );
				riskBlackorwhite.setName(temp.getName());
				String jsonStr = JSONObject.fromObject(riskBlackorwhite,jsonConfig).toString();
				String returnStr = riskServiceClient.deleteRiskBlackorwhite(  jsonStr  );
//				riskBlackorwhiteListService.deleteRiskBlackorwhiteList(   Integer.parseInt(blackId)    );
				if( "1".equals(returnStr)){
					logger.info("黑白名单------>{删除}----->{结束}成功");
					return "删除成功!";
				}else{
					logger.info("黑白名单------>{删除}----->{结束}失败");
					return "删除失败!";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("黑白名单------>{删除}----->{结束}失败");
				return "删除失败!";
			}
			
	}
	
	
	/**
	 * 
	 * @方法说明：上传文件入库初始页面
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value ="readyUploadFile")
	public String readyUploadFile( HttpServletRequest request , HttpServletResponse response, Model model) throws IOException{
		logger.info("黑白名单元素值上传初始化");
		String blackItemValues = request.getParameter("blackItemValues");
		String ifInit = request.getParameter("ifInit");
		HttpSession session = request.getSession();
		if(!StringUtil.isBlank(blackItemValues)){
			String pageNo = request.getParameter("pageNo");
			RiskBlackorwhiteItemList riskBlackorwhiteItem = null;
			riskBlackorwhiteItem = new RiskBlackorwhiteItemList();
			String[] array = blackItemValues.split(",");
			riskBlackorwhiteItem.setBlackItemValues(array);
			riskBlackorwhiteItem.setBlackId(Integer.parseInt(request.getParameter("blackId")) );
			riskBlackorwhiteItem.setStatus(request.getParameter("status"));
			model = riskBlackorwhiteItemService.findBwItemPageList(new Page<RiskBlackorwhiteItemList>(request, response), riskBlackorwhiteItem,model,pageNo);
		}
		if(  "no".equals(ifInit) ) {
			RiskBlackorwhiteItemList item =  (RiskBlackorwhiteItemList)session.getAttribute("itemCondition");
			model.addAttribute("riskBlackorwhiteItem",  item);
		}else{
			RiskBlackorwhiteItemList sessionObj = (RiskBlackorwhiteItemList)session.getAttribute("itemCondition");
			if(sessionObj!=null){
				session.removeAttribute("itemCondition");
			}
			sessionObj = new RiskBlackorwhiteItemList();
			sessionObj.setBlackId( Integer.parseInt(  request.getParameter("blackId")   ) );
			sessionObj.setBlackItemValue( request.getParameter("blackItemValue_q"));
			sessionObj.setBeginCreTime( DateUtil.stringToDate( request.getParameter("beginCreTime_q")  )   );
			sessionObj.setEndCreTime( DateUtil.stringToDate( request.getParameter("endCreTime_q")  )  );
			sessionObj.setBeginUpdTime( DateUtil.stringToDate( request.getParameter("beginUpdTime_q")  )  );
			sessionObj.setEndUpdTime( DateUtil.stringToDate( request.getParameter("endUpdTime_q")  )  );
			sessionObj.setStatus(  request.getParameter("status_q")    );
			request.setAttribute("riskBlackorwhiteItem", sessionObj);
			session.setAttribute("itemCondition", sessionObj);
		}
		
		
		
		logger.info("黑白名单元素值上传结束");
		model.addAttribute("enumMap", this.getEnumMapForQuery());
		model.addAttribute("blackItemValues", blackItemValues);
		model.addAttribute("rbwInfo", riskBlackorwhiteListService.getRiskBlackorwhiteListById(  Integer.parseInt(request.getParameter("blackId")) ) ) ;
		logger.info("黑白名单元素上传结束");
		return "modules/riskManage/riskBlackorwhiteItemUpload";
	}
	/**
	 * 
	 * @方法说明：上传文件入库
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value ="loadModel")
	public String loadModel(RedirectAttributes redirectAttributes,@RequestParam("file") MultipartFile file,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String blackId = request.getParameter("blackId");
		RiskBlackorwhiteList rbwInfo = riskBlackorwhiteListService.getRiskBlackorwhiteListById(Integer.parseInt(blackId));
		logger.info("黑白名单元素值----->{上传文件入库}--->{applicationId}=");
			List<Map> readExcel = riskBlackorwhiteItemService.readExcel(file);
			logger.info("文件读取结果:"+readExcel);
			Map<String,String> resultMap = new HashMap<String,String>();
			
			
			String currentDate =  DateUtil.getTodayYYYYMMDD_HHMMSS() ;
			StringBuffer blackItemValues = new StringBuffer("");
			RiskBlackorwhiteItemList temp = null;
			RiskBlackorwhiteItemVo vo = null;
			int count = 0;
			int errorIndex = 0;
			String returnStr= "0";
			for (int i=0;i<(readExcel==null?0:readExcel.size());i++) {
				errorIndex = i;
				if(readExcel.get(i)!=null){
					if(readExcel.get(i).get("blackItemValue")==null ){
						resultMap.put("msg","文件上传入库失败!黑名单字段值不能为空！");
						break;
					}
					vo = new RiskBlackorwhiteItemVo();
					vo.setBlackItemValue(readExcel.get(i).get("blackItemValue").toString());
					vo.setStatus(RiskBwStatus.ENABLE.getValue());
					vo.setBlackId(  Integer.parseInt( blackId ) );
					vo.setCreateAuthor( UserUtils.getUser().getName() );
					vo.setCreateTime(currentDate);
					vo.setUpdateAuthor(UserUtils.getUser().getName());
					vo.setUpdateTime(currentDate);
					Map checkMap = riskBlackorwhiteItemService.checkUploadData( vo , (i+1), rbwInfo );
					if( !"校验通过".equals(checkMap.get("message")) ){
						resultMap.put("msg","文件上传入库失败,"+checkMap.get("message"));
						break;
					}
					temp = new RiskBlackorwhiteItemList();
					temp.setBlackId( rbwInfo.getBlackId() );
					temp.setBlackItemValue(readExcel.get(i).get("blackItemValue").toString());
					count = riskBlackorwhiteItemService.getCountByItemValue(temp);
					if( count==0  ){//如果没有记录则新增，否则不操作
						returnStr = riskServiceClient.addRiskBlackorwhiteItem(JSONObject.fromObject(vo).toString());
					}else{//如果有记录则更新状态
						String blackItemId = riskBlackorwhiteItemService.getItemIdByValueAndBlackId(temp);
						RiskBlackorwhiteItemVo upd = null;
						upd = new RiskBlackorwhiteItemVo();
						upd.setBlackItemId(  Integer.parseInt(blackItemId) );
						upd.setStatus(RiskBwStatus.ENABLE.getValue());
						upd.setUpdateTime(currentDate);
						upd.setUpdateAuthor(UserUtils.getUser().getName());
						String jsonStr = JSONObject.fromObject(upd,jsonConfig).toString();
						returnStr = riskServiceClient.updateRiskBlackorwhiteItem(  jsonStr );
					}
				}
				if(  "0".equals(returnStr) && count==0 ){
					resultMap.put("msg","文件上传入库失败,请查看第"+errorIndex+"行数据");
					break;
				}
				resultMap.put("msg","文件上传入库成功！");
				blackItemValues.append(readExcel.get(i).get("blackItemValue")+",");
			}
		if(resultMap==null ||  StringUtil.isBlank(resultMap.get("msg"))) {
			resultMap.put("msg","文件上传失败！数据为空");
		}
		addMessage(redirectAttributes, resultMap.get("msg") );
		//添加页面操作
		return "redirect:"+Global.getAdminPath()+"/risk/riskBlackorwhite/view/readyUploadFile?blackItemValues="+blackItemValues.toString()+"&blackId="+blackId + "&ifInit=no"; 
		
	}
	/**
	 * 
	 * @方法说明：查询黑白名单明细列表
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:view")
	@RequestMapping(value = { "itemlist"})
	public String itemlist(RiskBlackorwhiteItemList riskBlackorwhiteItem, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		try {
//			if(riskBlackorwhiteItem==null){
//				riskBlackorwhiteItem = new RiskBlackorwhiteItemList();
//				riskBlackorwhiteItem.setBlackId(   Integer.parseInt(request.getParameter("blackId")) );
//			}
			String pageNo = request.getParameter("pageNo");
			String searchFlag = request.getParameter("searchFlag");
			if( "1".equals(searchFlag)){
				riskBlackorwhiteItem = new RiskBlackorwhiteItemList();
				riskBlackorwhiteItem.setBlackItemValue(request.getParameter("blackItemValue"));
				riskBlackorwhiteItem.setBeginCreTime(DateUtil.stringToDate(request.getParameter("beginCreTime")));
				riskBlackorwhiteItem.setEndCreTime(DateUtil.stringToDate(request.getParameter("endCreTime")));
				riskBlackorwhiteItem.setBeginUpdTime(DateUtil.stringToDate(request.getParameter("beginUpdTime")));
				riskBlackorwhiteItem.setEndUpdTime(DateUtil.stringToDate(request.getParameter("endUpdTime")));
				riskBlackorwhiteItem.setBlackId(Integer.parseInt(request.getParameter("blackId")));
				riskBlackorwhiteItem.setStatus(request.getParameter("status"));
			}else if( "2".equals(searchFlag)) {
				RiskBlackorwhiteList riskBlackorwhite = null;
				riskBlackorwhite = new RiskBlackorwhiteList();
				riskBlackorwhite.setBeginCreTime(DateUtil.stringToDate(request.getParameter("beginCreTime_c") ));
				riskBlackorwhite.setEndCreTime(DateUtil.stringToDate(request.getParameter("endCreTime_c") ));
				riskBlackorwhite.setBeginUpdTime(DateUtil.stringToDate(request.getParameter("beginUpdTime_c") ));
				riskBlackorwhite.setEndUpdTime(DateUtil.stringToDate(request.getParameter("endUpdTime_c") ));
				riskBlackorwhite.setBlackId(StringUtil.isBlank(request.getParameter("blackId_c"))?null:Integer.parseInt(request.getParameter("blackId_c")));
				riskBlackorwhite.setName(request.getParameter("name_c") );
				riskBlackorwhite.setType(request.getParameter("type_c") );
				riskBlackorwhite.setStatus(request.getParameter("status_c") );
				riskBlackorwhite.setProductCode(request.getParameter("productCode_c") );
				riskBlackorwhite.setCategory(request.getParameter("category_c"));
				request.setAttribute("riskBlackorwhite", riskBlackorwhite);
			}
			
				model = riskBlackorwhiteItemService.findBwItemPageList(new Page<RiskBlackorwhiteItemList>(request, response), riskBlackorwhiteItem,
						model,pageNo);
			model.addAttribute("enumMap", this.getEnumMapForQuery());
			model.addAttribute("riskBlackorwhiteItem", riskBlackorwhiteItem);
			request.setAttribute("rbwInfo", riskBlackorwhiteListService.getRiskBlackorwhiteListById(  riskBlackorwhiteItem.getBlackId() ) ) ;
			return "modules/riskManage/riskBlackorwhiteItemQuery";
		} catch (Exception e) {
			logger.error("RiskBlackorwhiteController list has a error:{查询黑白名单明细信息异常}", e);
			throw new Exception(e);
		}
	}
	/**
	 * 
	 * @方法说明：添加黑白名单明细
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value = "saveItem")
	@ResponseBody
	public String saveItem( HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
			logger.info("黑白名单明细------>{添加}----->{开始}");
			RiskBlackorwhiteItemVo riskBlackorwhiteItem = null; riskBlackorwhiteItem = new RiskBlackorwhiteItemVo();
			String blackId = request.getParameter("blackId");
			String blackItemValue = request.getParameter("blackItemValue");
			if ( StringUtil.isBlank(blackId)  || StringUtil.isBlank(blackItemValue)  ) {
				return "添加失败，参数为空！";
			}
			//检验元素值begin
			RiskBlackorwhiteList rbwForCheck = null;rbwForCheck= new RiskBlackorwhiteList();
			rbwForCheck = riskBlackorwhiteListService.getRiskBlackorwhiteListById( Integer.parseInt( blackId ));
			String checkResult = checkCategoryValue(rbwForCheck.getCategory()  ,  blackItemValue);
			if( !"suc".equals(checkResult) ){
				return "添加失败!"+checkResult;
			}
			
			String currentDate =  DateUtil.getTodayYYYYMMDD_HHMMSS();
			String username = UserUtils.getUser().getName();
			riskBlackorwhiteItem.setBlackItemValue(blackItemValue);
			riskBlackorwhiteItem.setBlackId(   Integer.parseInt( blackId )  );
			riskBlackorwhiteItem.setStatus(RiskBwStatus.ENABLE.getValue());
			riskBlackorwhiteItem.setCreateTime(currentDate);
			riskBlackorwhiteItem.setCreateAuthor(username);
			riskBlackorwhiteItem.setUpdateAuthor(username);
			riskBlackorwhiteItem.setUpdateTime(currentDate);
			
			RiskBlackorwhiteItemList temp = null;
			temp = new RiskBlackorwhiteItemList();
			temp.setBlackId( riskBlackorwhiteItem.getBlackId() );
			temp.setBlackItemValue( riskBlackorwhiteItem.getBlackItemValue());
			int count = riskBlackorwhiteItemService.getCountByItemValue(temp);
			String returnStr = "0";
			if( count==0  ){//如果没有记录则新增，否则不操作
				returnStr = riskServiceClient.addRiskBlackorwhiteItem(JSONObject.fromObject(riskBlackorwhiteItem).toString());
			}else{
				return "添加失败，记录已经存在";
			}
//			int num = riskBlackorwhiteItemService.insertRiskBlackorwhiteItem(riskBlackorwhiteItem);
			logger.info("黑白名单明细------>{添加}----->{结束}");
			String msg = "";
			if( "1".equals(returnStr) ){
				msg = "添加成功!";
			}else{
				msg = "添加失败!";
			}
			return msg;
	}
	/**
	 * 
	 * @方法说明：更新黑白名单明细
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value = "updateItem")
	@ResponseBody
	public String updateItem( HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
			logger.info("黑白名单明细------>{更新}----->{开始}");
			RiskBlackorwhiteItemVo riskBlackorwhiteItem = null; riskBlackorwhiteItem = new RiskBlackorwhiteItemVo();
			String currentDate =  DateUtil.getTodayYYYYMMDD_HHMMSS() ;
			String username = UserUtils.getUser().getName();
			String blackItemValue = request.getParameter("blackItemValue");
			String blackItemId = request.getParameter("blackItemId");
			String blackId = request.getParameter("blackId");
			if( StringUtil.isBlank(blackId) ||  StringUtil.isBlank(blackItemId)) {
				return "更新失败!参数为空";
			}
			
			
			//检验元素值begin
			RiskBlackorwhiteList rbwForCheck = null;rbwForCheck= new RiskBlackorwhiteList();
			rbwForCheck = riskBlackorwhiteListService.getRiskBlackorwhiteListById( Integer.parseInt( blackId ));
			String checkResult = checkCategoryValue(rbwForCheck.getCategory() , blackItemValue);
			if( !"suc".equals(checkResult) ){
				return "更新失败!"+checkResult;
			}
			
			
			RiskBlackorwhiteItemList temp = null;
			temp = riskBlackorwhiteItemService.getRiskBlackorwhiteItemById(Integer.parseInt(blackItemId));
			
			RiskBlackorwhiteItemList temp2 = null;
			temp2 = new RiskBlackorwhiteItemList();
			temp2.setBlackId( Integer.parseInt( blackId ) );
			temp2.setBlackItemValue( blackItemValue);
			int count = riskBlackorwhiteItemService.getCountByItemValue(temp2);
			if(count>0 && !blackItemValue.equals(temp.getBlackItemValue())) {
				return "更新失败!元素值重复";
			}
			
			
			
			riskBlackorwhiteItem.setCreateAuthor(temp.getCreateAuthor());
			riskBlackorwhiteItem.setCreateTime(DateUtil.dateToString(temp.getCreateTime()));
			
			
			//下面是可能更新的字段
			riskBlackorwhiteItem.setBlackItemId(  Integer.parseInt(blackItemId) );
			riskBlackorwhiteItem.setBlackItemValue(blackItemValue);
			riskBlackorwhiteItem.setUpdateTime(currentDate);
			riskBlackorwhiteItem.setUpdateAuthor(username);
			riskBlackorwhiteItem.setStatus(request.getParameter("status"));
			riskBlackorwhiteItem.setBlackId( Integer.parseInt( blackId ));
			String jsonStr = JSONObject.fromObject(riskBlackorwhiteItem,jsonConfig).toString();
			String returnStr = riskServiceClient.updateRiskBlackorwhiteItem(  jsonStr );
//			int num = riskBlackorwhiteItemService.updateRiskBlackorwhiteItem(riskBlackorwhiteItem);
			logger.info("黑白名单明细------>{更新}----->{结束}");
			String msg = "";
			if("1".equals(returnStr)){
				msg = "更新成功!";
			}else{
				msg = "更新失败!";
			}
			return msg;
	}
	/**
	 * 
	 * @方法说明：删除黑白名单明细
	 * @时间：2017年4月21号
	 * @创建人：李震
	 */
	@RequiresPermissions("risk:riskBlackorwhite:edit")
	@RequestMapping(value = "deleteItem")
	@ResponseBody
	public String deleteItem( HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
			logger.info("黑白名单明细------>{删除}----->{开始}");
			String blackItemId = request.getParameter("blackItemId");
			if( StringUtil.isBlank(blackItemId)){
				return  "删除失败!编号为空";
			}
			RiskBlackorwhiteItemList temp = null;
			temp = new RiskBlackorwhiteItemList();
			temp = riskBlackorwhiteItemService.getRiskBlackorwhiteItemById(Integer.parseInt(blackItemId));
			
			RiskBlackorwhiteItemVo riskBlackorwhiteItem = null; 
			riskBlackorwhiteItem = new RiskBlackorwhiteItemVo();
			riskBlackorwhiteItem.setBlackId( temp.getBlackId()  );
			riskBlackorwhiteItem.setBlackItemId(Integer.parseInt(blackItemId));
			riskBlackorwhiteItem.setCreateAuthor(temp.getCreateAuthor());
			riskBlackorwhiteItem.setCreateTime( DateUtil.dateToString(temp.getCreateTime())   );
			riskBlackorwhiteItem.setStatus(temp.getStatus());
			riskBlackorwhiteItem.setUpdateAuthor(temp.getUpdateAuthor());
			riskBlackorwhiteItem.setUpdateTime( DateUtil.dateToString(temp.getUpdateTime() ) );
			riskBlackorwhiteItem.setBlackItemValue(temp.getBlackItemValue());

			String jsonStr = JSONObject.fromObject(riskBlackorwhiteItem,jsonConfig).toString();
			String returnStr = riskServiceClient.deleteRiskBlackorwhiteItem( jsonStr );
//			int num = riskBlackorwhiteItemService.deleteRiskBlackorwhiteItem(Integer.parseInt(blackItemId) );
			logger.info("黑白名单明细------>{删除}----->{结束}");
			String msg = "";
			if(  "1".equals(returnStr) ){
				msg = "删除成功!";
			}else{
				msg = "删除失败!";
			}
			return msg;
	}
	
	private Map<String,List<EnumBean>> getEnumMapForQuery() {
		 Map<String,List<EnumBean>> enumMap = null;
		 enumMap =  new HashMap<String,List<EnumBean>>();
		 List<EnumBean> riskBwCategory = Lists.newArrayList();
		 List<EnumBean> riskBwStatus = Lists.newArrayList();
		 List<EnumBean> riskBwType = Lists.newArrayList();
		for (RiskBwCategory en : RiskBwCategory.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			riskBwCategory.add(ct);
		}
		for (RiskBwStatus en : RiskBwStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			riskBwStatus.add(ct);
		}
		for (RiskBwType en : RiskBwType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			riskBwType.add(ct);
		}
		List<EnumBean> prodList = Lists.newArrayList();  //生效状态
		List<Product> productList = productCService.findList(new Product());
		if(null != productList && productList.size() > 0){
			for(Product product : productList){
				EnumBean ct = new EnumBean();
				ct.setValue(product.getCode());
				ct.setName(product.getName());
				prodList.add(ct);
			}
		}
		
		enumMap.put("riskBwCategory", riskBwCategory);
		enumMap.put("riskBwStatus", riskBwStatus);
		enumMap.put("riskBwType", riskBwType);
		enumMap.put("prodList", prodList);
		 return enumMap;
	}
	
	/**
	 * 
	 * @方法说明：检测上传的信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	private String checkCategoryValue(String category,String blackItemValue) {
	     if( !StringUtil.isBlank(category) ){
		    		if (  category.equals(RiskBwCategory.MERCHANT_ID.getValue())  && !RegExpression.regNumber(  blackItemValue  ) ) {
				    	return "商户编号必须是数字!";
				    }else if (  category.equals(RiskBwCategory.BANKCARD.getValue())  &&  !RegExpression.regBankCard(blackItemValue) ) {
				    	return "银行卡号格式不正确!";
				    }else if (  category.equals(RiskBwCategory.MOBILE.getValue())  &&  !Validator.isMobile(blackItemValue)  ) {
				    	return "手机号格式不正确!";
				    }/*else if (  category.equals(RiskBwCategory.IP.getValue())  &&  !Validator.isIP(blackItemValue) ) {
				    	return "IP格式不正确!";
				    }*/else if (  category.equals(RiskBwCategory.IDCARD.getValue())  && !Validator.isIDCard2(  blackItemValue  )  ) {
				    	return "身份证格式不正确!";
				    }else{
				    	return "suc";
				    }
	     }else{
	    	 return "元素值不能为空!";
	     }
	}

}
