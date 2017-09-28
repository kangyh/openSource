/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.manage.common.enums.HgmsTaskStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.hgms.dao.HgmsSynDataTaskRecordDao;
import com.heepay.manage.modules.hgms.entity.HgmsSynDataTaskRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * 描    述：同步数据任务记录Service
 *
 * 创 建 者： @author 郭正新
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
@Service
@Transactional(readOnly = true)
public class HgmsSynDataTaskRecordService extends CrudService<HgmsSynDataTaskRecordDao, HgmsSynDataTaskRecord> {

    @Autowired
    private HgmsSynDataTaskRecordDao synDataTasskRecordDao;

    public HgmsSynDataTaskRecord get(String id) {
        return super.get(id);
    }

    public List<HgmsSynDataTaskRecord> findList(HgmsSynDataTaskRecord hgmsSynDataTaskRecord) {
        return super.findList(hgmsSynDataTaskRecord);
    }

    public Page<HgmsSynDataTaskRecord> findPage(Page<HgmsSynDataTaskRecord> page, HgmsSynDataTaskRecord hgmsSynDataTaskRecord) {
        page.setOrderBy("a.create_time desc");
        return super.findPage(page, hgmsSynDataTaskRecord);
    }

    @Transactional(readOnly = false)
    public void save(HgmsSynDataTaskRecord hgmsSynDataTaskRecord) {
        super.save(hgmsSynDataTaskRecord, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsSynDataTaskRecord hgmsSynDataTaskRecord) {
        super.delete(hgmsSynDataTaskRecord);
    }

    public boolean checkYesterDayTaskStatus(Date checkBeginDate, Date checkEndDate) {
        HgmsSynDataTaskRecord taskRecord = new HgmsSynDataTaskRecord();
        boolean taskStatus = true;
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
        List<HgmsSynDataTaskRecord> taskList = synDataTasskRecordDao.checkYesterDayTaskStatus(taskRecord);
        for (HgmsSynDataTaskRecord task : taskList) {
            taskStatus = HgmsTaskStatus.SUCCESS.getValue().equals(task.getStatus()) && taskStatus;
        }
        return taskStatus;
    }
}