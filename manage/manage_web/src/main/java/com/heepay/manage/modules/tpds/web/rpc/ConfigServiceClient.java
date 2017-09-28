package com.heepay.manage.modules.tpds.web.rpc;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.tpds.model.DepositMsgVO;
import com.heepay.rpc.tpds.service.ConfigService;
import com.heepay.rpc.tpds.service.WithDrawService;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang 创建时间： 2017年2月17日 下午5:19:29 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Service
public class ConfigServiceClient extends BaseClientDistribute {

	private static final String SERVICENAME = "ConfigServiceImpl";

	private static final String NODENAME = "tpds_rpc";

	private static final Logger logger = LogManager.getLogger();

	@Resource(name = "tpdsAccountClient")
	private ThriftClientProxy clientProxy;

	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);

	}

	public ConfigService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new ConfigService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 
	 * 
	 * @方法说明：检查系统编码是否存在
	 * @author dongzhengjiang
	 * @param cmsg:系统编码
	 * @return 1：存在 0：不存在
	 * @时间：2017年2月20日 上午9:45:36
	 */
	public String checksysNo(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.checksysNo(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}

	/**
	 * 
	 * 
	 * @方法说明：添加商户台账记录
	 * @author dongzhengjiang
	 * @param 商户台账实体的json格式
	 * @return 1：成功 0：失败
	 * @时间：2017年2月20日 上午9:47:04
	 */
	public String addMerchantAccount(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.addMerchantAccount(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}

	/**
	 * 
	 * 
	 * @方法说明：修改商户台账记录
	 * @author dongzhengjiang
	 * @param 商户台账实体的json格式
	 * @return 1：成功 0：失败
	 * @时间：2017年2月20日 上午9:48:20
	 */
	public String editMerchantAccount(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.editMerchantAccount(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}

	public String getMerchantH5(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.getMerchantBySeqNo(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}

	public String addMerchantH5(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.addMerchantH5(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}
	
	
	/**
	 * 
	 * 
	 * @方法说明：添加商户产品密钥记录
	 * @author dongzhengjiang
	 * @param 商户台账实体的json格式
	 * @return 1：成功 0：失败
	 * @时间：2017年2月20日 上午9:47:04
	 */
	public String addMerchantProduct(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.addproductKey(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}

	/**
	 * 
	 * 
	 * @方法说明：修改商户产品密钥记录
	 * @author dongzhengjiang
	 * @param 商户台账实体的json格式
	 * @return 1：成功 0：失败
	 * @时间：2017年2月20日 上午9:48:20
	 */
	public String editMerchantProduct(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.editproductKey(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}
	
	//添加商户证书缓存
	public String addMerchantCer(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.addMerchantCer(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}
	
	//修改商户证书缓存
	public String editMerchantCer(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.editMerchantCer(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}
	
	//删除商户证书缓存
	public String deleteMerchantCer(String merchantNo){
		ConfigService.Client client = this.getClient();
		try {
			return client.delMerchantCer(merchantNo);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}
	}
	
	//添加银行证书缓存
	public String addBankcer(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.addBankCer(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}
	//修改银行证书缓冲
	public String editBankcert(String msg) {
		ConfigService.Client client = this.getClient();
		try {
			return client.editBankCer(msg);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}

	}
	
	//删除银行证书缓存
	public String deleteBankCer(String bankNo){
		ConfigService.Client client = this.getClient();
		try {
			return client.delBankCer(bankNo);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}
	}
}
