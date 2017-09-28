package com.heepay.manage.rpc.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.modules.route.dao.AreacodeDao;
import com.heepay.manage.modules.route.dao.BankInfoDao;
import com.heepay.manage.modules.route.dao.LineBankNumberDao;
import com.heepay.manage.modules.route.entity.Areacode;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.LineBankNumber;
import com.heepay.manage.modules.route.service.LineBankNumberService;
import com.heepay.manage.modules.sys.dao.HatAreasDao;
import com.heepay.manage.modules.sys.dao.HatCitiesDao;
import com.heepay.manage.modules.sys.dao.HatProvincesDao;
import com.heepay.manage.modules.sys.entity.HatAreas;
import com.heepay.manage.modules.sys.entity.HatCities;
import com.heepay.manage.modules.sys.entity.HatProvinces;
import com.heepay.manage.modules.sys.entity.PCA;
import com.heepay.manage.rpc.service.ThreeLinkageService;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：三级联动service实现
 * <p/>
 * 创建者  B.HJ
 * 创建时间 2016-08-23-15:59
 * 创建描述：三级联动service实现
 */
@Component
@RpcService(name = "threeLevelServiceImpl", processor = ThreeLinkageService.Processor.class)
public class ThreeLevelServiceImpl implements ThreeLinkageService.Iface {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private LineBankNumberDao lineBankNumberDao;

    @Autowired
    private AreacodeDao areacodeDao;

    @Autowired
    private HatProvincesDao hatProvincesDao;
    
    @Autowired
    private BankInfoDao bankInfoDao;
    
    @Autowired
    private HatAreasDao hatAreasDao;

    @Autowired
    private HatCitiesDao hatCitiesDao;

    @Autowired
    private LineBankNumberService lineBankNumberService;


    /**     
      * @discription 三级联动
      * @author N.W       
      * @created 2016年9月26日 上午11:59:09     
      * @param id
      * @param callback
      * @param role
      * @return
      * @throws TException     
      */
    @Override
    public String select(String id, String callback, String role) throws TException {
        List<PCA> list = new ArrayList<>();
        if ("1".equals(role)) {
            List<HatProvinces> hatProvinces = hatProvincesDao.selectList();
            list = provinceToPCA(hatProvinces, list);
        } else if ("2".equals(role)) {
            List<HatCities> hatCities = hatCitiesDao.selectByFather(id);
            list = cityToPCA(hatCities, list);
        } else {
            List<HatAreas> hatAreas = hatAreasDao.selectByFather(id);
            list = areaToPCA(hatAreas, list);
        }
        String json = null;
        try {
            json = mapper.writeValueAsString(list);
        } catch (Exception e) {
            logger.error("转换json异常", e);
        }
        return callback + "(" + json + ")";
    }

    /**
     * 省市县三级联动，给晨sir的api使用
     * @param id        当前级别的id
     * @param role      1是省2是市3是县
     * @return          json
     * @throws TException
     */
    @Override
    public String selectApi(String id, String role) throws TException {
        List<PCA> list = new ArrayList<>();
        if ("1".equals(role)) {
            List<HatProvinces> hatProvinces = hatProvincesDao.selectList();
            list = provinceToPCA(hatProvinces, list);
        } else if ("2".equals(role)) {
            List<HatCities> hatCities = hatCitiesDao.selectByFather(id);
            list = cityToPCA(hatCities, list);
        } else {
            List<HatAreas> hatAreas = hatAreasDao.selectByFather(id);
            list = areaToPCA(hatAreas, list);
        }
        String json = null;
        try {
            json = mapper.writeValueAsString(list);
        } catch (Exception e) {
            logger.error("转换json异常", e);
        }
        return json;
    }

    /**

    @Override
    public String selectApi(String s, String s1) throws TException {
        return null;
    }

    /**
      * @discription bankID&CityId   三级联动
      * @author N.W       
      * @created 2016年9月26日 上午11:59:06     
      * @param id
      * @param callback
      * @param role
      * @param bankNo
      * @return
      * @throws TException     
      */
    public String selectBank(String id, String callback, String role,String bankNo) throws TException {
    	String json = null;
    	try {
    		List<PCA> list = new ArrayList<>();
    		List<LineBankNumber> ubankCodes = new ArrayList<LineBankNumber>();
			if ("1".equals(role)) {
				List<HatProvinces> hatProvinces = hatProvincesDao.selectList();
				list = provinceToPCA(hatProvinces, list);
				json = mapper.writeValueAsString(list);
			} else if ("2".equals(role)) {
				List<Areacode> areacodes = areacodeDao.findList(id);
				list = areaCodeToPCA(areacodes, list);
				json = mapper.writeValueAsString(list);
			} else {
				ubankCodes = lineBankNumberDao.findListBank(id, bankNo);
				json = mapper.writeValueAsString(ubankCodes);
			}
		} catch (Exception e) {
		}
    	
    	return callback + "(" + json + ")";
    }
    
    /**     
    * @discription bankID & areaname 
    * @author N.W       
    * @created 2016年9月26日 上午11:58:54     
    * @param name
    * @param bankNo
    * @return
    * @throws TException List    
    */
    public String selectAreaName(String name,String bankNo,String open_bank_name) throws TException {
    	List<LineBankNumber> listByAreaname = lineBankNumberDao.findListByAreaname(name, bankNo, open_bank_name);
        String json = "";
        if (listByAreaname.size()!=0){
            json = JsonMapperUtil.nonEmptyMapper().toJson(listByAreaname);
        }
    	return json;
    }

    private List<PCA> areaToPCA(List<HatAreas> hatAreas, List<PCA> list) {
        for (HatAreas hatArea : hatAreas) {
            PCA pca = new PCA();
            pca.setId(hatArea.getAreaid());
            pca.setName(hatArea.getArea());
            list.add(pca);
        }
        return list;
    }

    private List<PCA> cityToPCA(List<HatCities> hatCities, List<PCA> list) {
        for (HatCities hatCity : hatCities) {
            PCA pca = new PCA();
            pca.setId(hatCity.getCityid());
            pca.setName(hatCity.getCity());
            list.add(pca);
        }
        return list;
    }

    /*private List<LineBankNumber> ubankCodeToPCA(List<LineBankNumber> ubankcodes, List<LineBankNumber> bankNumberList) {
        for (LineBankNumber ubankcode : ubankcodes) {
        	LineBankNumber lineBankNumber = new LineBankNumber();
        	lineBankNumber.setOpenBankCode(ubankcode.getOpenBankCode());
        	lineBankNumber.setOpenBankName(ubankcode.getOpenBankName());
        	lineBankNumber.setAssociateLineNumber(ubankcode.getAssociateLineNumber());
            bankNumberList.add(lineBankNumber);
        }
        return bankNumberList;
    }*/

    private List<PCA> areaCodeToPCA(List<Areacode> areacodes, List<PCA> list) {
        for (Areacode areacode : areacodes) {
            PCA pca = new PCA();
            pca.setId(areacode.getAreaCode());
            pca.setName(areacode.getAreaName());
            list.add(pca);
        }
        return list;
    }

    private List<PCA> provinceToPCA(List<HatProvinces> hatProvinces, List<PCA> list) {
        for (HatProvinces hatProvince : hatProvinces) {
            PCA pca = new PCA();
            pca.setId(hatProvince.getProvinceid());
            pca.setName(hatProvince.getProvince());
            list.add(pca);
        }
        return list;
    }

    /**
     * 获取银行列表
     * ly 2017年3月3日09:51:38
     */
	@Override
	public String getBankList(){
		String json = null;
		List<BankInfo> selectList = bankInfoDao.selectList();
		try {
			json = mapper.writeValueAsString(selectList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}


    /**
     * 获取联行号地区信息
     * ly 2017年3月3日09:52:24
     */
    @Override
    public String selectAreaList() throws TException {
        String json = null;
        List<Areacode> selectList = lineBankNumberDao.findAreacodeList("net");
        try {
            json = mapper.writeValueAsString(selectList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String selectLineNumberList(String bankNo, String provinceCode, String cityCode) throws TException {
        List<LineBankNumber> lineBankNumbers = lineBankNumberService.selectLineNumberList(bankNo, provinceCode, cityCode);
        String json = JsonMapperUtil.nonEmptyMapper().toJson(lineBankNumbers);
        return json;
    }

    /**
     * 根据省市 银行查询联行号
     * ly 2017年3月3日09:52:47
     */
    @Override
    public String selectLineNumber(String bankNo, String provinceName, String cityName) throws TException {
        List<LineBankNumber> list = lineBankNumberDao.getNetLineNumber(bankNo,provinceName,cityName,"net");
        if(null != list && !list.isEmpty()){
            return list.get(0).getAssociateLineNumber();
        }
        return "";
    }

}
