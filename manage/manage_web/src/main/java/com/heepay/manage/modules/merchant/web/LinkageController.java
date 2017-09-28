package com.heepay.manage.modules.merchant.web;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.merchant.service.MerchantIndustryBaseCService;
import com.heepay.manage.modules.merchant.service.MerchantUnionpayAreaBaseCService;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;
import com.heepay.manage.modules.merchant.vo.MerchantUnionpayAreaBase;

/**          
* 
* 描    述： 商户信息基础数据联动
*
* 创 建 者： ly
* 创建时间： 2016年9月2日 下午5:07:39 
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
@RequestMapping(value="${adminPath}/merchant/linkage")
public class LinkageController {

  @Autowired
  private MerchantIndustryBaseCService merchantIndustryBaseCService;
  
  @Autowired
  private MerchantUnionpayAreaBaseCService merchantUnionpayAreaBaseCService;
  
  private ObjectMapper mapper = new ObjectMapper();
  
    
  /**     
  * @discription 获取商户行业类型
  * @author ly     
  * @created 2016年9月2日 下午5:07:51     
  * @return     
  */
  @RequestMapping(value="industry")
  @ResponseBody
  public String industry(){
    List<MerchantIndustryBase> list = merchantIndustryBaseCService.getIndustry();
    List<EnumBean> list1 = Lists.newArrayList();
    for(MerchantIndustryBase a : list){
      EnumBean b = new EnumBean();
      b.setName(a.getIndustryName());
      b.setValue(a.getIndustryId());
      list1.add(b);
    }
    String json = null;
    try {
      json = mapper.writeValueAsString(list1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }
  
    
  /**     
  * @discription 获取商户行业类型子集
  * @author ly     
  * @created 2016年9月2日 下午5:08:06     
  * @param id
  * @return     
  */
  @RequestMapping(value="industryChild")
  @ResponseBody
  public String industryChild(String id){
    List<MerchantIndustryBase> list = merchantIndustryBaseCService.industryChild(id);
    List<EnumBean> list1 = Lists.newArrayList();
    for(MerchantIndustryBase a : list){
      EnumBean b = new EnumBean();
      b.setName(a.getIndustryChildName());
      b.setValue(a.getIndustryChildId());
      list1.add(b);
    }
    String json = null;
    try {
      json = mapper.writeValueAsString(list1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }
  
    
  /**     
  * @discription 获取商户行业mcc
  * @author ly     
  * @created 2016年9月2日 下午5:08:26     
  * @param id
  * @return     
  */
  @RequestMapping(value="industryMcc")
  @ResponseBody
  public String industryMcc(String id){
    List<MerchantIndustryBase> list = merchantIndustryBaseCService.industryMcc(id);
    List<EnumBean> list1 = Lists.newArrayList();
    for(MerchantIndustryBase a : list){
      EnumBean b = new EnumBean();
      b.setName(a.getIndustryDescribe());
      b.setValue(a.getMcc());
      list1.add(b);
    }
    String json = null;
    try {
      json = mapper.writeValueAsString(list1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }
  
  
    
  /**     
  * @discription 获取银联区域码省
  * @author ly     
  * @created 2016年9月2日 下午5:08:36     
  * @return     
  */
  @RequestMapping(value="UnionpayP")
  @ResponseBody
  public String UnionpayP(){
    List<MerchantUnionpayAreaBase> list = merchantUnionpayAreaBaseCService.UnionpayP();
    List<EnumBean> list1 = Lists.newArrayList();
    for(MerchantUnionpayAreaBase a : list){
      EnumBean b = new EnumBean();
      b.setName(a.getProvinceName());
      b.setValue(a.getProvinceId());
      list1.add(b);
    }
    String json = null;
    try {
      json = mapper.writeValueAsString(list1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }
  
    
  /**     
  * @discription 获取银联区域码市
  * @author ly     
  * @created 2016年9月2日 下午5:09:00     
  * @param id
  * @return     
  */
  @RequestMapping(value="UnionpayCity")
  @ResponseBody
  public String UnionpayCity(String id){
    List<MerchantUnionpayAreaBase> list = merchantUnionpayAreaBaseCService.UnionpayCity(id);
    List<EnumBean> list1 = Lists.newArrayList();
    for(MerchantUnionpayAreaBase a : list){
      EnumBean b = new EnumBean();
      b.setName(a.getCityName());
      b.setValue(a.getCityId());
      list1.add(b);
    }
    String json = null;
    try {
      json = mapper.writeValueAsString(list1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }
}
