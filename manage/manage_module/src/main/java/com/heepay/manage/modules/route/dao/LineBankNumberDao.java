/**
 *  
 */
package com.heepay.manage.modules.route.dao;

import java.util.List;

import com.heepay.manage.modules.route.entity.Areacode;
import org.apache.ibatis.annotations.Param;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.LineBankNumber;

/**
 * 联行号管理DAO接口
 * @author 牛文
 * @version V1.0
 */
@MyBatisDao
public interface LineBankNumberDao extends CrudDao<LineBankNumber> {
  
    public LineBankNumber selectByLineNumber(String associateLineNumber);

    public void status(LineBankNumber lineBankNumber);

    public List<LineBankNumber> findListBank(@Param(value="area_code") String id,@Param(value="bank_no")String bankNo);
  
    public List<LineBankNumber> findListByAreaname(@Param(value="city_name") String name,@Param(value="bank_no")String bankNo,@Param(value="open_bank_name")String open_bank_name);

    List<LineBankNumber> getNetLineNumber(@Param(value="bankNo")String bankNo, @Param(value="provinceName")String provinceName,
                                          @Param(value="cityName")String cityName, @Param(value="source")String source);

    List<Areacode> findAreacodeList(@Param(value="source")String source);

    List<LineBankNumber> findLineNumberList(@Param(value="bankNo")String bankNo,@Param(value="bankName")String bankName,
            @Param(value="provinceCode")String provinceCode, @Param(value="cityCode")String cityCode);

    /**
     * @方法说明：根据联行号查询省份
     * @时间： 2017-07-28 17:14
     * @创建人：wangl
     */
    LineBankNumber selectProvince(String associateLineNumber);
}