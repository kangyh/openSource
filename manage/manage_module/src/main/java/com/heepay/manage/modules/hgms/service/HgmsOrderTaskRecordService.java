/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.enums.HgmsOrderTaskType;
import com.heepay.manage.common.enums.HgmsTaskStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsOrderTaskRecordDao;
import com.heepay.manage.modules.hgms.entity.HgmsOrderTaskRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * 描    述：汇聚财定时任务Service
 *
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 09:55:57
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
public class HgmsOrderTaskRecordService extends CrudService<HgmsOrderTaskRecordDao, HgmsOrderTaskRecord> {

    @Autowired
    private HgmsOrderTaskRecordDao hgmsOrderTaskRecordDao;

    public HgmsOrderTaskRecord get(String id) {
        return super.get(id);
    }

    public List<HgmsOrderTaskRecord> findList(HgmsOrderTaskRecord hgmsOrderTaskRecord) {
        return super.findList(hgmsOrderTaskRecord);
    }

    public Page<HgmsOrderTaskRecord> findPage(Page<HgmsOrderTaskRecord> page, HgmsOrderTaskRecord hgmsOrderTaskRecord) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, hgmsOrderTaskRecord);
    }

    @Transactional(readOnly = false)
    public void save(HgmsOrderTaskRecord hgmsOrderTaskRecord) {
        super.save(hgmsOrderTaskRecord, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsOrderTaskRecord hgmsOrderTaskRecord) {
        super.delete(hgmsOrderTaskRecord);
    }

    /**
     * //判断昨天的执行订单是否成功
     *
     * @param checkBeginDate
     * @param checkEndDate
     * @return
     */
    public boolean checkYesterDayExTaskStatus(Date checkBeginDate, Date checkEndDate) {
        HgmsOrderTaskRecord taskRecord = new HgmsOrderTaskRecord();
        //理论开始时间
        Calendar checkBegin = Calendar.getInstance();
        checkBegin.setTime(checkBeginDate);
        checkBegin.add(Calendar.DAY_OF_MONTH, -1);
        taskRecord.setCheckBeginDate(checkBegin.getTime());
        //理论结束时间
        Calendar endBegin = Calendar.getInstance();
        endBegin.setTime(checkEndDate);
        endBegin.add(Calendar.DAY_OF_MONTH, -1);
        taskRecord.setCheckEndDate(endBegin.getTime());
        //任务类型
        taskRecord.setTaskType(HgmsOrderTaskType.EXCUTE.getValue());
        HgmsOrderTaskRecord task = hgmsOrderTaskRecordDao.checkYesterDayExTaskStatus(taskRecord);
        return HgmsTaskStatus.SUCCESS.getValue().equals(task.getStatus());
    }

    public boolean checkYesterDayCrTaskStatus(Date checkBeginDate, Date checkEndDate) {
        HgmsOrderTaskRecord taskRecord = new HgmsOrderTaskRecord();
        taskRecord.setCheckBeginDate(checkBeginDate);
        taskRecord.setCheckEndDate(checkEndDate);
        taskRecord.setTaskType(HgmsOrderTaskType.CREATE.getValue());
        HgmsOrderTaskRecord task = hgmsOrderTaskRecordDao.checkYesterDayExTaskStatus(taskRecord);
        return HgmsTaskStatus.SUCCESS.getValue().equals(task.getStatus());
    }
}