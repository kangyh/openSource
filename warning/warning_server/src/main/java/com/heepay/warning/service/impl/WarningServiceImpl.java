package com.heepay.warning.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.SmsUtils;
import com.heepay.common.util.StringUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.WeiXinType;
import com.heepay.risk_es_engine.ESearchSysLogService;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.warning.service.WarningService;
import com.heepay.warning.dao.InfoAddMapper;
import com.heepay.warning.dao.InfoGroupMapper;
import com.heepay.warning.dao.InfoMemberMapper;
import com.heepay.warning.dao.InfoMsgMapper;
import com.heepay.warning.entity.InfoAdd;
import com.heepay.warning.entity.InfoGroup;
import com.heepay.warning.entity.InfoMember;
import com.heepay.warning.entity.InfoMsg;
import com.heepay.warning.util.SendMail;
import com.heepay.warning.util.SendSms;
import com.heepay.warning.util.SendWeiXinUtil;
import com.heepay.warning.vo.MonitorMailModel;
import com.heepay.warning.vo.SendStatus;
import com.heepay.warning.vo.SendType;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang 创建时间： 2017年3月16日 上午11:27:32 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
@RpcService(name = "WarningServiceImpl", processor = WarningService.Processor.class)
public class WarningServiceImpl implements com.heepay.rpc.warning.service.WarningService.Iface {
	@Autowired
	private InfoMsgMapper infoMsgMapper;
	@Autowired
	private InfoMemberMapper infoMemberMapper;
	@Autowired
	private InfoGroupMapper infoGroupMapper;
	@Autowired
	private InfoAddMapper infoAddMapper;
	@Autowired
	private MonitorMailModel monitorMailModel;
	@Autowired
	private ESearchSysLogService eSearchSysLogService;
	private static final Logger logger = LogManager.getLogger();

	@Override
	public String prepareMsg() throws TException {
		List<InfoGroup> list = infoGroupMapper.selectAll();
		String subject = monitorMailModel.getSubject();
		if (list != null) {
			for (InfoGroup group : list) {
				InfoMsg msg = new InfoMsg();
				msg.setCreateTime(new Date());
				msg.setGroupId(group.getGroupId());
				msg.setIsAdd("N");
				msg.setType(group.getRemark());
				msg.setMsgHead(String.format(subject, group.getGroupName()));
				String error = this.getErrorInfo(group.getGroupCode(),group.getRemark());
				if (!StringUtil.isBlank(error)) {
					msg.setMsgBody(error);
					msg.setSendStatus(SendStatus.INIT.getValue());
					msg.setIsAdd(error.length()>300?"Y":"N");
					infoMsgMapper.insert(msg);
					if(error.length()>300)
					{
						InfoAdd add=new InfoAdd();
						add.setMsgId(msg.getMsgId());
						add.setFilePath(DateUtils.getDateStr(new Date(), "yyyyMMddHHssmm")+msg.getMsgId()+".txt");
						infoAddMapper.insert(add);
					}
				}
			}
		}
		return "1";
	}

	private String getErrorInfo(String groupId,String sendType) {
		StringBuilder sb = new StringBuilder();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list = eSearchSysLogService.getSysErrorLogByTimeSpan("");
		} catch (Exception ex) {
			logger.error("调用es出现错误:{}", ex.getMessage());
		}
		if (list != null) {
			for (Map<String, String> map : list) {
				if (map.get("_type") != null && map.get("_type").toString().equals(groupId+"-"+DateUtils.getDateStr(new Date(), "yyyy-MM").replace("-", "."))) {
					sb.append(map.get("message"));
					if(sendType.equals(SendType.EMAIL.getValue())||sendType.equals(SendType.ALL.getValue()))
						sb.append("<br>");
				}
			}
		}
		return sb.toString();
	}

	@Override
	public String sendMsg() throws TException {
		List<InfoMsg> list = infoMsgMapper.getList();

		if (list != null) {
			for (InfoMsg msg : list) {
				msg.setMsgBody(infoMsgMapper.getBodyByMsgId(msg.getMsgId()).getMsgBody());
				List<InfoMember> memberList = infoMemberMapper.selectByGroupId(msg.getGroupId());
				if (memberList != null&&memberList.size()>0) {
					int failCount = 0;
					String weixinResult = "";
					if ((msg.getType().equals(SendType.WECHAT.getValue())
							|| msg.getType().equals(SendType.ALL.getValue()))
							&& !StringUtil.isBlank(memberList.get(0).getWechat())) {
						logger.info("给{}发送微信开始", memberList.get(0).getWechat());
						weixinResult = SendWeiXinUtil.getInstance().sendWeiXin(memberList.get(0).getWechat(),
								msg.getMsgBody());
						logger.info("发送微信结束", weixinResult);
					}
					for (InfoMember member : memberList) {
						if ((msg.getType().equals(SendType.EMAIL.getValue())
								|| msg.getType().equals(SendType.ALL.getValue()))
								&& !StringUtil.isBlank(member.getEmail())) {
							try {
								logger.info("给{}发送邮件开始", member.getEmail());
								if(msg.getIsAdd().equals("N"))
								{
								boolean b=SendMail.getInstance().sendMail(member.getEmail(), msg.getMsgHead(), msg.getMsgBody(),
										null,null);
								if(!b)
								failCount++;
								}
								else
								{   
									InfoAdd add=infoAddMapper.getByMsgId(msg.getMsgId());
									if(add!=null)
									{
										String err=msg.getMsgBody().replace("<br>", System.getProperty("line.separator"));
									boolean b=SendMail.getInstance().sendMail(member.getEmail(), msg.getMsgHead(), "监控报警信息，请查收附件!",
											err.getBytes(),add.getFilePath(),null);
									if(!b)
									failCount++;
									}
								}
							} catch (Exception ex) {
								failCount++;
								logger.info("给{}发送邮件失败{}", member.getEmail(), ex.getMessage());
							}
							logger.info("给{}发送邮件结束", member.getEmail());

						}
						if ((msg.getType().equals(SendType.SMS.getValue())
								|| msg.getType().equals(SendType.ALL.getValue()))
								&& !StringUtil.isBlank(member.getMobile())) {
							logger.info("给{}发送短信开始", member.getMobile());
							String result = "";
							if (msg.getMsgBody().length() < 200)
								result = SendSms.sendSMS(member.getMobile(), msg.getMsgBody(), "46");
							else {
								int index = msg.getMsgBody().length() / 200;
								int m = msg.getMsgBody().length() % 200;
								for (int i = 0; i < index; i++) {
									result = SendSms.sendSMS(member.getMobile(),
											msg.getMsgBody().substring(i * 200, 200 + i * 200), "46");
									try {
										Thread.sleep(5000L);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								if (m > 0) {
									result = SendSms.sendSMS(member.getMobile(),
											msg.getMsgBody().substring(index * 200), "46");
								}
							}
							logger.info("发送短信结束{}", result);
						}

					}
					if (memberList.size() > 0) {
						if (msg.getType().equals(SendType.EMAIL.getValue()) && failCount == memberList.size()) {
							msg.setSendTime(new Date());
							msg.setSendStatus(SendStatus.FAIL.getValue());
						} else if (msg.getType().equals(SendType.WECHAT.getValue())
								&& weixinResult.equals(WeiXinType.labelOf(WeiXinType.FAILED.getValue()))) {
							msg.setSendTime(new Date());
							msg.setSendStatus(SendStatus.FAIL.getValue());
						} else {
							msg.setSendTime(new Date());
							msg.setSendStatus(SendStatus.OK.getValue());
						}
						infoMsgMapper.update(msg);
					}
				}
			}
		}
		return "1";
	}

	@Override
	public String sendWaringMsg(String msgEntity) throws TException {
		logger.info("发送消息json:{}",msgEntity);	
		String tomails="";
		InfoMsg msg = JsonMapperUtil.buildNonDefaultMapper().fromJson(msgEntity, InfoMsg.class);
		if(StringUtil.isBlank(msg.getIsAdd()))
			msg.setIsAdd("N");
		if(msg.getMsgId()==null||msg.getMsgId()<=0)
		{   
			msg.setCreateTime(new Date());
			infoMsgMapper.insert(msg);
		}
		List<InfoMember> memberList = infoMemberMapper.selectByGroupId(msg.getGroupId());
		if (memberList != null&&memberList.size()>0) {
			int failCount = 0;
			String weixinResult = "";
			if ((msg.getType().equals(SendType.WECHAT.getValue()) || msg.getType().equals(SendType.ALL.getValue()))
					&& !StringUtil.isBlank(memberList.get(0).getWechat())) {
				logger.info("给{}发送微信开始", memberList.get(0).getWechat());
				weixinResult = SendWeiXinUtil.getInstance().sendWeiXin(memberList.get(0).getWechat(), msg.getMsgBody());
				logger.info("发送微信结束", weixinResult);
			}
			
			for (InfoMember member : memberList) {
				tomails+=member.getEmail()+",";
				
				if ((msg.getType().equals(SendType.SMS.getValue()) || msg.getType().equals(SendType.ALL.getValue()))
						&& !StringUtil.isBlank(member.getMobile())) {
					logger.info("给{}发送短信开始", member.getMobile());
					String result = "";
					if (msg.getMsgBody().length() < 200)
						result = SendSms.sendSMS(member.getMobile(), msg.getMsgBody(), "46");
					else {
						int index = msg.getMsgBody().length() / 200;
						int m = msg.getMsgBody().length() % 200;
						for (int i = 0; i < index; i++) {
							result = SendSms.sendSMS(member.getMobile(),
									msg.getMsgBody().substring(i * 200, 200 + i * 200), "46");
						}
						if (m > 0) {
							result = SendSms.sendSMS(member.getMobile(), msg.getMsgBody().substring(index * 200), "46");
						}
					}
					logger.info("发送短信结束{}", result);
				}

			}
			if(!StringUtil.isBlank(tomails))
				tomails=tomails.substring(0, tomails.length()-1);
			if ((msg.getType().equals(SendType.EMAIL.getValue()) || msg.getType().equals(SendType.ALL.getValue()))
					) {
				try {
					logger.info("给{}发送邮件开始", tomails);
					if(StringUtil.isBlank(msg.getIsAdd())||msg.getIsAdd().equals("N"))
					{ 
						
					boolean b=SendMail.getInstance().sendMail(tomails, msg.getMsgHead(), msg.getMsgBody(),
							null,msg.getSender());
					if(!b)
					failCount++;
					}
					else
					{   
						InfoAdd add=infoAddMapper.getByMsgId(msg.getMsgId());
						if(add!=null)
						{
							String err=msg.getMsgBody().replace("<br>", System.getProperty("line.separator"));
						boolean b=SendMail.getInstance().sendMail(tomails, msg.getMsgHead(), "监控报警信息，请查收附件!",
								err.getBytes(),add.getFilePath(),msg.getSender());
						if(!b)
						failCount++;
						}
					}	
				} catch (Exception ex) {
					failCount++;
					logger.info("给{}发送邮件失败{}", tomails, ex.getStackTrace());
				}
				logger.info("给{}发送邮件结束", tomails);

			}
			if (msg.getType().equals(SendType.EMAIL.getValue()) && failCount == memberList.size()) {
				msg.setSendTime(new Date());
				msg.setSendStatus(SendStatus.FAIL.getValue());
			} else if (msg.getType().equals(SendType.WECHAT.getValue())
					&& weixinResult.equals(WeiXinType.labelOf(WeiXinType.FAILED.getValue()))) {
				msg.setSendTime(new Date());
				msg.setSendStatus(SendStatus.FAIL.getValue());
			} else {
				msg.setSendTime(new Date());
				msg.setSendStatus(SendStatus.OK.getValue());
			}
			if(msg.getMsgId()!=null&&msg.getMsgId()>0)
			infoMsgMapper.update(msg);
								
		}
		return "1";
	}

	@Override
	public String getInfoGroup(String groupCode) throws TException {
		InfoGroup entity=infoGroupMapper.getByGroupCode(groupCode);
		if(entity==null)
			return null;
		else
			return JsonMapperUtil.buildNonDefaultMapper().toJson(entity);
	}

}
