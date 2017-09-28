package com.heepay.manage.modules.route.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.heepay.manage.modules.route.entity.*;
import com.heepay.manage.modules.route.service.LineBankNumberService;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.modules.route.service.AreacodeService;
import com.heepay.manage.modules.route.service.HatProvinceService;
import com.heepay.manage.modules.route.service.UbankcodeService;

/**
 *
 * 描    述：省市联动选择Service
 *
 * 创 建 者： N.W
 * 创建时间： 2016年9月9日 下午3:22:39
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/route/city")
public class CityController {
    private JsonMapperUtil mapper = new JsonMapperUtil();
    @Autowired
    private AreacodeService areacodeService;
    @Autowired
    private UbankcodeService ubankcodeService;
    @Autowired
    private HatProvinceService hatProvinceService;
    @Autowired
    private LineBankNumberService lineBankNumberService;


    /**
     * 省市联动选择方法
     * @param id
     * @param bankNo
     * @param callback
     * @param role
     * @return
     */
    @RequestMapping(value="/select")
    @ResponseBody
    public String select(String id,String bankNo,String callback,String role){
        List<PCA> list = new ArrayList<PCA>();
        if("1".equals(role)){
            List<HatProvince> hatProvinces = hatProvinceService.findList();
            list = ProvinceToPCA(hatProvinces,list);
        }else if("2".equals(role)){
            List<Areacode> areacodes = areacodeService.findList(id);
            list = AreaToPCA(areacodes,list);
        }else{
            List<Ubankcode> ubankcodes = ubankcodeService.findList(id, bankNo);
            list = UbankToPCA(ubankcodes,list);
        }
        String json = mapper.toJson(list);
        return callback+"("+json+")";
    }

    /**
     * 开户行转换PCA实体对象
     * @param ubankcodes
     * @param list
     * @return
     */
    private List<PCA> UbankToPCA(List<Ubankcode> ubankcodes, List<PCA> list) {
        for(Ubankcode ubankcode : ubankcodes){
            PCA pca = new PCA();
            pca.setId(ubankcode.getOpenBankCode());
            pca.setName(ubankcode.getOpenBankName());
            list.add(pca);
        }
        return list;
    }

    /**
     * 地区转换实体对象
     * @param areacodes
     * @param list
     * @return
     */
    private List<PCA> AreaToPCA(List<Areacode> areacodes, List<PCA> list) {
        for(Areacode areacode : areacodes){
            PCA pca = new PCA();
            pca.setId(areacode.getAreaCode());
            pca.setName(areacode.getAreaName());
            list.add(pca);
        }
        return list;
    }

    /**
     * 省市转换实体对象PCA
     * @param hatProvinces
     * @param list
     * @return
     */
    private List<PCA> ProvinceToPCA(List<HatProvince> hatProvinces, List<PCA> list) {
        for(HatProvince hatProvince : hatProvinces){
            PCA pca = new PCA();
            pca.setId(hatProvince.getProvinceId());
            pca.setName(hatProvince.getProvince());
            list.add(pca);
        }
        return list;
    }
    
    @RequestMapping(value="/getOpenBank")
    @ResponseBody
    public String getOpenBank(String bankNo, String city) {
        List<Ubankcode> ubankcodes = ubankcodeService.findList(city, bankNo);
        List<PCA> list = new ArrayList<>();
        list = UbankToPCA(ubankcodes,list);
        System.out.println(bankNo + " city:" + city);
        return mapper.toJson(list);
    }

    @RequestMapping(value="/selectLineNumber")
    @ResponseBody
    public String selectLineNumber(String id,String bankNo,String callback,String role){
        List<PCA> list = new ArrayList<PCA>();
        if("1".equals(role)){
            List<HatProvince> hatProvinces = hatProvinceService.findList();
            list = ProvinceToPCA(hatProvinces,list);
        }else if("2".equals(role)){
            List<Areacode> areacodes = areacodeService.findList(id);
            list = AreaToPCA(areacodes,list);
        }else{
            List<LineBankNumber> lineBankNumbers = lineBankNumberService.selectLineNumberList(bankNo, "", id);
            return callback+"("+mapper.toJson(lineBankNumbers)+")";
        }
        String json = mapper.toJson(list);
        return callback+"("+json+")";
    }

    private List<PCA> LineToPCA(List<LineBankNumber> lineBankNumbers, List<PCA> list) {
        for(LineBankNumber lineBankNumber : lineBankNumbers){
            PCA pca = new PCA();
            pca.setId(lineBankNumber.getAssociateLineNumber());
            pca.setName(lineBankNumber.getOpenBankName());
            list.add(pca);
        }
        return list;
    }


}
