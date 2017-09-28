/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.contract.service;

import java.util.List;

import com.heepay.common.util.FastDFSUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.contract.entity.Contract;
import com.heepay.manage.modules.contract.dao.ContractDao;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * 描    述：合同管理Service
 *
 * 创 建 者： @author ly
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
public class ContractService extends CrudService<ContractDao, Contract> {

	public Contract get(String id) {
		return super.get(id);
	}
	
	public List<Contract> findList(Contract contract) {
		return super.findList(contract);
	}
	
	public Page<Contract> findPage(Page<Contract> page, Contract contract) {
		return super.findPage(page, contract);
	}
	
	@Transactional(readOnly = false)
	public void save(Contract contract,MultipartFile filePathFile) throws Exception {
		if(!filePathFile.isEmpty()) {
			//图片上传返回图片地址（不包含域名）
			String filePath = FastDFSUtils.uploadPic(filePathFile.getBytes(), filePathFile.getOriginalFilename(), filePathFile.getSize());
			contract.setFileName(filePathFile.getOriginalFilename());
			contract.setFilePath(filePath);
		}
		super.save(contract,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(Contract contract) {
		super.delete(contract);
	}
	
}