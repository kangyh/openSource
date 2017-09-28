package com.heepay.manage.modules.merchant.web;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.sys.entity.HatAreas;
import com.heepay.manage.modules.sys.entity.HatCities;
import com.heepay.manage.modules.sys.entity.HatProvinces;
import com.heepay.manage.modules.sys.entity.PCA;
import com.heepay.manage.modules.sys.service.HatAreasService;
import com.heepay.manage.modules.sys.service.HatCitiesService;
import com.heepay.manage.modules.sys.service.HatProvincesService;

/**          
* 
* 描    述： 省市区三级联动
*
* 创 建 者： ly
* 创建时间： 2016年9月2日 下午5:07:22 
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
@RequestMapping(value = "${adminPath}/merchant/three")
public class ThreeController extends BaseController{
  private ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private HatAreasService hatAreaService;
  @Autowired
  private HatCitiesService hatCityService;
  @Autowired
  private HatProvincesService hatProvinceService;
  
    
  /**     
  * @discription 省市区三级联动
  * @author ly     
  * @created 2016年9月2日 下午5:10:03     
  * @param id
  * @param callback
  * @param role
  * @return     
  */
  @RequestMapping(value="/select")
  @ResponseBody
  public String select(String id,String callback,String role){
    List<PCA> list = new ArrayList<PCA>();
    if("1".equals(role)){
      List<HatProvinces> hatProvinces = hatProvinceService.selectList();
      list = ProvinceToPCA(hatProvinces,list);
    }else if("2".equals(role)){
      List<HatCities> hatCities = hatCityService.selectByFather(id);
      list = CityToPCA(hatCities,list);
    }else{
      List<HatAreas> hatAreas = hatAreaService.selectByFather(id);
      list = AreaToPCA(hatAreas,list);
    }
    String json = null;
    try {
      json = mapper.writeValueAsString(list);
    } catch (Exception e) {
      e.printStackTrace();
    }
    callback = callback+"("+json+")";
    return callback;
  }

    
  /**     
  * @discription 区县信息转换
  * @author ly     
  * @created 2016年9月2日 下午5:10:19     
  * @param hatAreas
  * @param list
  * @return     
  */
  private List<PCA> AreaToPCA(List<HatAreas> hatAreas, List<PCA> list) {
    for(HatAreas hatArea : hatAreas){
      PCA pca = new PCA();
      pca.setId(hatArea.getAreaid());
      pca.setName(hatArea.getArea());
      list.add(pca);
    }
    return list;
  }

    
  /**     
  * @discription 市信息转换
  * @author ly     
  * @created 2016年9月2日 下午5:10:38     
  * @param hatCities
  * @param list
  * @return     
  */
  private List<PCA> CityToPCA(List<HatCities> hatCities, List<PCA> list) {
    for(HatCities hatCity : hatCities){
      PCA pca = new PCA();
      pca.setId(hatCity.getCityid());
      pca.setName(hatCity.getCity());
      list.add(pca);
    }
    return list;
  }

    
  /**     
  * @discription 省信息转换
  * @author ly     
  * @created 2016年9月2日 下午5:10:36     
  * @param hatProvinces
  * @param list
  * @return     
  */
  private List<PCA> ProvinceToPCA(List<HatProvinces> hatProvinces, List<PCA> list) {
    for(HatProvinces hatProvince : hatProvinces){
      PCA pca = new PCA();
      pca.setId(hatProvince.getProvinceid());
      pca.setName(hatProvince.getProvince());
      list.add(pca);
    }
    return list;
  }

}
