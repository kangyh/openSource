package com.heepay.manage.modules.monitor.java.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/***
 * 
* 
* 描    述：
*
* 创 建 者： wangl
* 创建时间：  2017年1月20日上午10:07:21
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
public class MonitorMachine extends DataEntity<MonitorMachine>{
    /**
	 * @方法说明：
	 * @时间： 2017年1月20日上午10:07:24
	 * @创建人：wangl
	 */
	private static final long serialVersionUID = 1L;

	private Integer machineId;

    private String machineCode;

    private String machineName;

    private Integer isDb;

    private Integer machineGroupId;

    private String innerIp;

    private String outerIp;


    private String accessIp;

    private String accessType;

    private String userName;

    private String password;

    private Integer port;

    private String folder;

    private String suffix;

    private Integer status;

    private String systemOf;
    

    private Date createTime;
    
    private Date updateTime;
    
    private String createAuthor;
    
    private String updateAuthor;		

    //非映射字段
    private Date beginOperEndTime;
    
    private Date endOperEndTime;
    
    private String view;
    
    private String value;
    
    private String name;
    
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode == null ? null : machineCode.trim();
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName == null ? null : machineName.trim();
    }

    public Integer getIsDb() {
        return isDb;
    }

    public void setIsDb(Integer isDb) {
        this.isDb = isDb;
    }

    

    public Integer getMachineGroupId() {
		return machineGroupId;
	}

	public void setMachineGroupId(Integer machineGroupId) {
		this.machineGroupId = machineGroupId;
	}

	public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp == null ? null : innerIp.trim();
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp == null ? null : outerIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp == null ? null : accessIp.trim();
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType == null ? null : accessType.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder == null ? null : folder.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSystemOf() {
        return systemOf;
    }

    public void setSystemOf(String systemOf) {
        this.systemOf = systemOf == null ? null : systemOf.trim();
    }
}