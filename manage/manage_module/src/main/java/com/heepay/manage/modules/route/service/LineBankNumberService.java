package com.heepay.manage.modules.route.service;

import com.heepay.codec.Md5;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.route.dao.BankInfoDao;
import com.heepay.manage.modules.route.dao.LineBankNumberDao;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.LineBankNumber;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.redis.JedisClusterUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**          
* 
* 描    述：联行号管理Service
*
* 创 建 者： 牛文
* 创建时间： 2016年9月29日 下午1:47:05 
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
    
@Service
@Transactional(readOnly = true)
public class LineBankNumberService extends CrudService<LineBankNumberDao, LineBankNumber> {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private LineBankNumberDao lineBankNumberDao;

    @Autowired
    private BankInfoDao bankInfoDao;

    /**
    * @discription 根据联行号查询数据库中的信息
    * @author 牛文
    * @created 2016年9月9日 下午4:08:31
    * @param associateLineNumber
    * @return
    */
    public LineBankNumber selectByLineNumber(String associateLineNumber){
        LineBankNumber lineBankNumbers = lineBankNumberDao.selectByLineNumber(associateLineNumber);
        return lineBankNumbers;
    }

    /**
     * @discription 联行号启用、禁用状态转换
     * @author 牛文
     * @created 2016年9月7日 下午6:34:14
     * @param lineBankNumber
     */
    @Transactional(readOnly = false)
    public void status(LineBankNumber lineBankNumber) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())){
            lineBankNumber.setUpdateBy(user);
        }
        lineBankNumberDao.status(lineBankNumber);
    }

    /**
     * @discription 获取联行号信息
     * @author ly
     * @created 2017年2月17日15:52:12
     */
    public List<LineBankNumber> selectLineNumberList(String bankNo, String provinceCode, String cityCode){
        List<Dict> dictList = DictUtils.getDictList(Constants.LINEBANK);
        String bankName = "";
        if(null != dictList && !dictList.isEmpty()){
            for(Dict dict : dictList){
                if(dict.getValue().equals(bankNo)){
                    BankInfo bankInfo = bankInfoDao.getBankNameByBankNo(bankNo);
                    if(null != bankInfo){
                        bankName = bankInfo.getBankName();
                    }
                    bankNo = dict.getLabel();
                }
            }
        }
        List<LineBankNumber> lineBankNumbers = lineBankNumberDao.findLineNumberList(bankNo, bankName, provinceCode, cityCode);
        return lineBankNumbers;
    }


   /**
    * @方法说明：根据联行号查询省份
    * @时间： 2017-07-28 17:14
    * @创建人：wangl
    */
    public String selectProvince(String associateLineNumber) {

        try {

            String name = Md5.encode(associateLineNumber);
            //缓存查询是否存在
            String value = JedisClusterUtil.getValue(name);
            if(StringUtils.isNotBlank(value)){
                return value;
            }
            //没有缓存直接查询数据库
            LineBankNumber lineBankNumbers = lineBankNumberDao.selectProvince(associateLineNumber);
            String jsonStr = new JsonMapperUtil().toJson(lineBankNumbers);

            //保持到缓存服务器
            JedisClusterUtil.setValue(name,jsonStr);
            return jsonStr;
        } catch (Exception e) {
            logger.error("根据联行号查询省份--->{异常}"+e);
            return "";
        }
    }
}