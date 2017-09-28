package com.heepay.prom.common.utils;

/**
 * 
* 
* 描    述：ztree--json数据
*
* 创 建 者： 王亚洪  
* 创建时间： 2017年1月5日 下午4:39:47 
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
public class ZtreeNode {
  
  private int id;
  private int pId;
  private String name;
  private boolean nocheck;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getpId() {
    return pId;
  }

  public void setpId(int pId) {
    this.pId = pId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isNocheck() {
    return nocheck;
  }

  public void setNocheck(boolean nocheck) {
    this.nocheck = nocheck;
  }
  
}
