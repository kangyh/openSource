package com.heepay.manage.modules.cbms.examine.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.CbmsOrderFormCStatus;
import com.heepay.manage.common.enums.orderStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.entity.CbmsCustomorderSum;
import com.heepay.manage.modules.cbms.entity.CbmsOrderSum;
import com.heepay.manage.modules.cbms.entity.CbmsOrderform;
import com.heepay.manage.modules.cbms.service.*;
import com.heepay.manage.modules.cbms.utils.LoadBeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.heepay.manage.common.utils.DateUtil.dateToString;


/**
 * 描    述：通关申报订单审核Controller
 * <p>
 * 创 建 者： @author 牛俊鹏
 * 创建时间：
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Controller
@RequestMapping(value = "${adminPath}/cbms/CbmsOrderexamineController")
public class CbmsOrderexamineController extends BaseController {
    @Autowired
    private CbmsOrderSumService cbmsOrderSumService;
    @Autowired
    private CbmsCustomorderSumService cbmsCustomorderSumService;
    @Autowired
    private CbmsOrderformService cbmsOrderformService;
    @Autowired
    private AccountRecordServiceImpl accountRecordServiceImpl;
    @Autowired
    private ApiNotifyServiceImpl apiNotifyServiceImpl;


    /**
     * @param id
     * @return
     * @discription 根据id查询数据库对象（页面发送请求第一时间执行此方法）
     * @author 牛俊鹏
     * @created 2017年2月6日
     */
    @ModelAttribute
    public CbmsOrderSum get(@RequestParam(required = false) String id) {
        logger.info("页面传入的id值为:" + id);
        CbmsOrderSum entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cbmsOrderSumService.get(id);
        }
        if (entity == null) {
            entity = new CbmsOrderSum();
        }
        return entity;
    }

    /**
     * @return
     * @discription 操作审核通过
     * @author 牛俊鹏
     * @created 2017年2月6日
     */
    @RequiresPermissions("cbms:CbmsOrderexamineController:edit")
    @RequestMapping(value = "examinesuc")
    public String examinesuc(CbmsOrderSum cbmsOrderSum, Model model, RedirectAttributes redirectAttributes) {
        if (cbmsOrderSum == null) {
            addMessage(redirectAttributes, "审核失败，汇总数据不存在");
            return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
        }
        logger.info("操作审核对象为:" + cbmsOrderSum.toString());
        if (!beanValidator(model, cbmsOrderSum)) { //数据验证
            return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
        }
        Date date = new Date();
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String declarationBatchHumber = "EX" + dateToString(date, "yyyyMMddhhmmss") + rannum;
        List<CbmsOrderform> updatelist = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        //查询同导入批次的订单
        List<CbmsOrderform> arrayList = cbmsOrderformService.selectimport(cbmsOrderSum.getImportBatchNumber());
        logger.info("查询同导入批次的订单为:" + arrayList.toString());
        if (arrayList.size() == 0) {
            addMessage(redirectAttributes, "审核失败，当前批次没有对应的订单");
            return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
        }
        for (CbmsOrderform cbmsOrderform : arrayList) {
           //筛选  待审核 和 不等于取消状态 和  不等于扣手续费失败的订单进行审核
            if (orderStatus.PENDINGAUDIT.getValue().equals(cbmsOrderform.getOrderStatus()) && !CbmsOrderFormCStatus.BACK.getValue().equals(cbmsOrderform.getCustomStatus()) && !"2".equals(cbmsOrderform.getTransStatus())) {
                map.put(cbmsOrderform.getCustomCode(), "");//筛选同批次订单的不同的海关
                cbmsOrderform.setSentCustomsNumber(declarationBatchHumber);
                cbmsOrderform.setOrderStatus("2");//2代表是待报关
                updatelist.add(cbmsOrderform);
            }
        }
        //更新订单表状态
        if (updatelist.size() > 0) {
            cbmsOrderformService.updateList(updatelist);
        } else {
            //代表此批次没有待审核的订单,更改导入订单汇总表的状态
            addMessage(redirectAttributes, "审核失败，当前批次中没有待审核的订单,请重新选择");
            return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
        }

        for (String key : map.keySet()) {
            //插入一条记录到 cbms_customorder_sum表  按照不同海关循环插入一条记录到申报汇总表中
            CbmsCustomorderSum cbmsCustomorderSum = LoadBeanUtils.gettfcbmsOrderSum(cbmsOrderSum, key, date, declarationBatchHumber, updatelist);
            cbmsCustomorderSumService.save(cbmsCustomorderSum);
        }
        //  当更新订单和插入记录完成之后再 修改ordersum表对应字段的状态
        cbmsOrderSum.setStatus("3");
        cbmsOrderSumService.update(cbmsOrderSum);
        addMessage(redirectAttributes, "审核通过：后台正在自动申报处理。");
        //重定向跳转list方法
        return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
    }

    /**
     * @return
     * @discription 操作审核拒绝
     * @author 牛俊鹏
     * @created 2017年2月6日
     */
    @RequiresPermissions("cbms:CbmsOrderexamineController:edit")
    @RequestMapping(value = "examinefal")
    public String examinefal(String reasonhidden, String orderhidden, RedirectAttributes redirectAttributes) {
        logger.info("操作审核拒绝,页面传入的reasonhidden值为==:" + reasonhidden + ";orderhidden值为" + orderhidden);
        CbmsOrderSum cbmsOrderSum = cbmsOrderSumService.get(orderhidden);
        if (cbmsOrderSum == null) {
            addMessage(redirectAttributes, "审核失败，此订单id没有对应的批次");
            return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
        }
        //查询此批次的所有订单
        List<CbmsOrderform> arrayList = cbmsOrderformService.selectimport(cbmsOrderSum.getImportBatchNumber());
        List<CbmsOrderform> updateList = new ArrayList<>();
        logger.info("查询此批次的所有订单值为==:" + arrayList.toString());
        StringBuffer sb = new StringBuffer();//退还手续费消息
        boolean failedstr = true;//循环退款成功的消息
        if (arrayList.size() == 0) {
            addMessage(redirectAttributes, "审核失败，当前批次没有对应的订单");
            return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
        }
        for (CbmsOrderform cbmsOrderform : arrayList) {
            //判断如果交易状态为4的话则做不退款状态改为取消
            if ((orderStatus.PENDINGAUDIT.getValue().equals(cbmsOrderform.getOrderStatus()) ||
                    orderStatus.UNCUSTOMSBROKER.getValue().equals(cbmsOrderform.getOrderStatus()))
                    && "4".equals(cbmsOrderform.getTransStatus())) {
                cbmsOrderform.setCustomStatus("6");//  报送状态 ：   取消
                cbmsOrderform.setOrderStatus("4");//  订单状态 ：   审核拒绝
                cbmsOrderform.setAuditFailReason(reasonhidden);
                updateList.add(cbmsOrderform);
            } else if ((orderStatus.PENDINGAUDIT.getValue().equals(cbmsOrderform.getOrderStatus()) ||
                    orderStatus.UNCUSTOMSBROKER.getValue().equals(cbmsOrderform.getOrderStatus()))
                    && "1".equals(cbmsOrderform.getTransStatus())) { //判断订单表中订单状态为待审核,才可以去退款
                if (accountRecordServiceImpl.refund(cbmsOrderform.getMerchantNo(), cbmsOrderform.getTransId())) { //1000代表退还手续费成功
                    cbmsOrderform.setCustomStatus("6");//  报送状态 ：   审核拒绝
                    cbmsOrderform.setOrderStatus("4");//  订单状态 ：   审核拒绝
                    cbmsOrderform.setTransStatus("3");//  已退款
                    cbmsOrderform.setAuditFailReason(reasonhidden);
                    updateList.add(cbmsOrderform);
                } else {
                    failedstr = false;
                    sb.append(cbmsOrderform.getOrderFormNo() + ",");
                }
            }
        }
        //修改此批次ordersum表中的状态
        //批量更新订单表数据
        if (updateList.size() > 0) {
            cbmsOrderformService.updateAuditFailList(updateList);
            logger.info("退款成功,批次号:{}", cbmsOrderSum.getImportBatchNumber());
        } else {
            addMessage(redirectAttributes, "审核失败，当前批次中没有可以审核的订单,请重新选择");
            return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
        }
        if (failedstr) {
            cbmsOrderSum.setStatus("4");//审核拒绝
            cbmsOrderSumService.update(cbmsOrderSum);
            logger.info("审核拒绝后,开始异步通知审核消息");
            //开始异步通知订单消息
            if ("2".equals(cbmsOrderSum.getDeclareType())) {
                apiNotifyMethod(cbmsOrderSum);
            }
            addMessage(redirectAttributes, "审核拒绝成功");
        } else {
            addMessage(redirectAttributes, "审核拒绝成功,部分订单退款失败,订单号分别为:", sb.toString());
        }
        //重定向跳转list方法
        return "redirect:" + Global.getAdminPath() + "/cbms/CbmsOrderexamineController/?repage";
    }

    /**
     * @return
     * @discription 通关订单审核列表展示
     * @author 牛俊鹏
     * @created 2017年2月6日
     */
    @RequiresPermissions("cbms:CbmsOrderexamineController:view")
    @RequestMapping(value = {"list", ""})
    public String list(CbmsOrderSum cbmsOrderSum,HttpServletRequest request, HttpServletResponse response, Model model) {
        //分页查询page
        logger.info("条件查询对象cbmsordersum为：" + cbmsOrderSum.toString());
        Page<CbmsOrderSum> page = cbmsOrderSumService.findPage(new Page<CbmsOrderSum>(request, response), cbmsOrderSum);
        logger.info("分页page对象为：" + page.toString());
        List<CbmsOrderSum> newlist = new ArrayList<>();
        for (CbmsOrderSum currency : page.getList()) {
            if (null == currency) {
                continue;
            }
            //如果 i=0则可以判定此批次下所有的订单已都申报
            //展示 待审核的一批次订单的数据
            if (!StringUtils.isEmpty(currency.getStatus())) {
                switch (currency.getStatus()) {
                    case "1":
                        currency.setComments("待审核"); break;//自动报关
                    case "2":
                        currency.setComments("待审核");break;//'不允许审核通过
                    case "5":
                        currency.setComments("待审核");break;//待审核
                    default:currency.setComments("");break;//待审核
                }
            }//判断如果手续费为null则手续费赋值为0
            if (currency.getFee() == null) {
                currency.setFee("0");
            }
            //转换申报类型为中文格式
            if ("2".equals(currency.getDeclareType())) {
                currency.setDeclareType("API接口");
            } else {
                currency.setDeclareType("文件上传");
            }
            newlist.add(currency);

        }
        page.setList(newlist);
        model.addAttribute("page", page);
        return "modules/cbms/orderexamine/cbmsOrderexamineList";
    }

    /**
     * @return
     * @discription 操作 ： 批量审核通过
     * @author 牛俊鹏
     * @created 2017年2月6日
     */
    @RequiresPermissions("cbms:CbmsOrderexamineController:edit")
    @RequestMapping(value = "examinesucces")
    public String examinesucces(String pids, String importBatchNumber, RedirectAttributes redirectAttributes) {
        logger.info("操作 ： 批量审核通过pids==" + pids);
        //判断是否选中批次
        if ("".equals(pids)) {
            addMessage(redirectAttributes, importBatchNumber + "&请至少选中一项");
            return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
        }
        String[] str = pids.split(",");//切割字符串
        Date date = new Date();
        Random random = new Random();
        List<CbmsOrderform> strList = new ArrayList<CbmsOrderform>();

        HashMap<String, String> map = new HashMap<>();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String declarationBatchHumber = "EX" + dateToString(date, "yyyyMMddhhmmss") + rannum;//申报批次号

        for (String s : str) {
            CbmsOrderform cbmsOrderform = new CbmsOrderform();
            cbmsOrderform.setOrderId(Long.valueOf(s));
            strList.add(cbmsOrderform);
        }
        //查询所有的订单id的所有订单数据
        List<CbmsOrderform> arrayList = cbmsOrderformService.selectIdList(strList);
        List<CbmsOrderform> updatelist = new ArrayList<>();
        logger.info("查询所选订单id的所有订单状态为待审核订单list:{}", arrayList.toString());
        if (arrayList.size() == 0) {
            addMessage(redirectAttributes, importBatchNumber + "&审核失败，所选订单没有对应的订单数据");
            return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
        }
        //查询导入订单汇总表中的对应批次号的数据
        CbmsOrderSum cbmsOrderSum = cbmsOrderSumService.ordersumselect(importBatchNumber);
        logger.info("通过批次号查出对应的汇总表数据:{}", cbmsOrderSum.toString());
        for (CbmsOrderform cbmsOrderform : arrayList) {
            if ("5".equals(cbmsOrderform.getCustomStatus())) {
                addMessage(redirectAttributes, importBatchNumber + "&审核失败：所选订单中存在取消状态的订单。");
                return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
            }
            if ("5".equals(cbmsOrderform.getOrderStatus())) {
                addMessage(redirectAttributes, importBatchNumber + "&审核失败：所选订单中存在不可审核通过的订单。");
                return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
            }
            if (orderStatus.PENDINGAUDIT.getValue().equals(cbmsOrderform.getOrderStatus()) && !"2".equals(cbmsOrderform.getTransStatus())) {//判断是待审核 不是手续费扣款失败的
                cbmsOrderform.setOrderStatus("2");//代表是待报关
                cbmsOrderform.setSentCustomsNumber(declarationBatchHumber);//将批次号赋值为每一个订单
                map.put(cbmsOrderform.getCustomCode(), "");//根据map 的去重将所有查到的订单的海关编码前两位筛选
                updatelist.add(cbmsOrderform);
            }
        }
        //更新所有的订单批次号
        logger.info("批量更新订单表的数据:{}", arrayList.toString());
        //更新订单表状态
        if (updatelist.size() > 0) {
            cbmsOrderformService.updateList(updatelist);
        } else {
            //代表此批次没有待审核的订单,更改导入订单汇总表的状态
            addMessage(redirectAttributes, importBatchNumber + "&审核失败，当前批次中没有待审核的订单,请重新选择");
            return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
        }
        //如果 为0则可以判定此批次下所有的订单已都申报
        if (cbmsOrderformService.notAuditedNum(importBatchNumber) == 0) {
            cbmsOrderSum.setStatus("6");
            cbmsOrderSumService.update(cbmsOrderSum);
        }
        for (String key : map.keySet()) {//循环map中的所有的key (key值代表所有订单的子海关的一级海关前两位)
            //插入一条记录到 cbms_customorder_sum表
            CbmsCustomorderSum cbmsCustomorderSum = LoadBeanUtils.gettfcbmsOrderSum(cbmsOrderSum, key, date, declarationBatchHumber, updatelist);
            cbmsCustomorderSumService.save(cbmsCustomorderSum);
        }
        addMessage(redirectAttributes, importBatchNumber + "&审核通过：后台正在自动申报处理。");
        //重定向跳转list方法
        return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";

    }

    /**
     * @return
     * @discription 操作 ： 批量审核拒绝
     * @author 牛俊鹏
     * @created 2017年1月
     */
    @RequiresPermissions("cbms:CbmsOrderexamineController:edit")
    @RequestMapping(value = "examinefalse")
    public String examinefalse(String pids, String reasonhidden, String importBatchNumber, RedirectAttributes redirectAttributes) {
        logger.info("操作 ： 批量审核失败页面传入的参数reasonhidden==" + reasonhidden + ";pids==" + pids);
        if ("".equals(pids)) {
            addMessage(redirectAttributes, importBatchNumber + "&请至少选中一项");
            return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
        }
        String[] str = pids.split(",");
        boolean failedstr = true;
        // 改变导入订单总表的审核状态
        StringBuffer sb = new StringBuffer();//记录有取消订单状态的批次号
        List<CbmsOrderform> strList = new ArrayList<CbmsOrderform>();
        for (String s : str) {
            CbmsOrderform cbmsOrderform = new CbmsOrderform();
            cbmsOrderform.setOrderId(Long.valueOf(s));
            strList.add(cbmsOrderform);
        }
        //查询所有的订单id的所有订单数据
        List<CbmsOrderform> arrayList = cbmsOrderformService.selectIdList(strList);
        List<CbmsOrderform> updateList = new ArrayList<>();
        logger.info("所选订单id的订单list:{}", arrayList.toString());
        if (arrayList.size() == 0) {
            addMessage(redirectAttributes, importBatchNumber + "&审核失败，所选订单没有对应的订单数据");
            return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
        }
        //查询导入订单汇总表中的对应批次号的数据
        CbmsOrderSum cbmsOrderSum = cbmsOrderSumService.ordersumselect(importBatchNumber);
        logger.info("通过批次号查出对应的汇总表数据:{}", cbmsOrderSum.toString());
        //第二步循环退款
        for (CbmsOrderform cbmsOrderform : arrayList) {
            //第一步校验是否选中的订单包含取消状态的订单
            if ("5".equals(cbmsOrderform.getCustomStatus())) {
                addMessage(redirectAttributes, importBatchNumber + "&操作失败：所选订单中存在取消状态的订单。");
                return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
            }
            if ((orderStatus.PENDINGAUDIT.getValue().equals(cbmsOrderform.getOrderStatus()) ||
                    orderStatus.UNCUSTOMSBROKER.getValue().equals(cbmsOrderform.getOrderStatus()))
                    && "4".equals(cbmsOrderform.getTransStatus())) {
                cbmsOrderform.setCustomStatus("6");//  报送状态 ：   取消
                cbmsOrderform.setOrderStatus("4");//  订单状态 ：   审核拒绝
                cbmsOrderform.setAuditFailReason(reasonhidden);
                updateList.add(cbmsOrderform);
            } else if ((orderStatus.PENDINGAUDIT.getValue().equals(cbmsOrderform.getOrderStatus()) ||
                    orderStatus.UNCUSTOMSBROKER.getValue().equals(cbmsOrderform.getOrderStatus()))
                    && "1".equals(cbmsOrderform.getTransStatus())) { //判断是否是待审核
                if (accountRecordServiceImpl.refund(cbmsOrderform.getMerchantNo(), cbmsOrderform.getTransId())) { //1000代表退还手续费成功
                    cbmsOrderform.setCustomStatus("6");//  报送状态 ：   审核拒绝
                    cbmsOrderform.setOrderStatus("4");//  订单状态 ：   审核拒绝
                    cbmsOrderform.setTransStatus("3");//  已退款
                    cbmsOrderform.setAuditFailReason(reasonhidden);
                    updateList.add(cbmsOrderform);
                } else {
                    failedstr = false;
                    sb.append(cbmsOrderform.getOrderFormNo() + ",");
                }
            }
        }
        if (updateList.size() > 0) {
            cbmsOrderformService.updateAuditFailList(updateList);
            logger.info("退款成功,批次号:{}", cbmsOrderSum.getImportBatchNumber());
        } else {
            //代表此批次没有待审核的订单
            addMessage(redirectAttributes, importBatchNumber + "&审核失败，当前批次中没有审核的订单,请重新选择");
            return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
        }
        if (failedstr) {
            //判断批量申报订单是否是此批次号下所有未审核的订单
            logger.info("判断此批次下是否还有为申报的订单");
            //如果 i=0则可以判定此批次下所有的订单已都申报
            if (cbmsOrderformService.notAuditedNum(importBatchNumber) == 0) {
                cbmsOrderSum.setStatus("6");
                cbmsOrderSumService.update(cbmsOrderSum);
            }
            logger.info("批量审核拒绝后,开始异步通知审核消息");
            //开始异步通知订单消息
            if ("2".equals(cbmsOrderSum.getDeclareType())) {
                apiNotifyMethod(cbmsOrderSum);
            }
            addMessage(redirectAttributes, importBatchNumber + "&审核拒绝成功");
            return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
        }
        addMessage(redirectAttributes, importBatchNumber + "&审核拒绝成功,部分订单退款失败,订单号分别为:{}", sb.toString());
        //重定向跳转list方法
        return "redirect:" + Global.getAdminPath() + "/cbms/cbmsOrderform/orderformlist?repage";
    }

    /**
     * 进行API异步通知操作
     *
     * @param cbmsOrderSum
     */
    private void apiNotifyMethod(CbmsOrderSum cbmsOrderSum) {
        List<CbmsOrderform> orderformList = cbmsOrderformService.selectimport(cbmsOrderSum.getImportBatchNumber());
        boolean strflag = false;//标记存在未申报的订单
        for (CbmsOrderform cbmsOrderform : orderformList) {
            if ("1".equals(cbmsOrderform.getCustomStatus()) || "2".equals(cbmsOrderform.getCustomStatus())) {
                strflag = true;
                break;
            }
        }
        //判断是否存在未申报的订单 true则结束此次循环
        if (strflag) {
            logger.info("此批次下存在状态为未报关或报送处理中的订单.批次号:{}", cbmsOrderSum.getImportBatchNumber());
            return;
        }
        //开始异步通知
        apiNotifyServiceImpl.customNotifyUtil(cbmsOrderSum, orderformList);
    }
}
