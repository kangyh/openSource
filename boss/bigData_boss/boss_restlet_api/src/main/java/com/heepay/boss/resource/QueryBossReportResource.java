package com.heepay.boss.resource;

import com.heepay.rpc.client.BossReportDateInfoServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 描 述：
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/1 14:53
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
public class QueryBossReportResource extends BaseServerResource{

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private BossReportDateInfoServiceClient bossReportDateInfoServiceClient;

    @Post
    public Representation queryBossReportInfo(Representation entity) throws IOException {
        log.info("boss_restlet_api:{}", "BOSS报表查询数据接口服务");
        JSONObject resultReqHeader = null, error = null;
        String respJson = null, routeName = null;
        try {
            JsonRepresentation jr = new JsonRepresentation(entity.getText());
            JSONObject result = jr.getJsonObject();
            routeName = getRequestAttributes().get("routeName").toString();
            log.info("BOSS报表查询数据接口服务调用的路径：method:{},参数:{}", routeName, entity);
            String params = result.toString();
            if(routeName.toUpperCase().equals("LIST")){
                respJson = bossReportDateInfoServiceClient.getbossReportDateInfoList(params);
            }else if(routeName.toUpperCase().equals("DETAILS")){
                respJson = bossReportDateInfoServiceClient.getbossReportDateInfoDetail(params);
            }else if(routeName.toUpperCase().equals("TIMETOTAL")){
                respJson = bossReportDateInfoServiceClient.getbossReportDateInfoTimeTotal(params);
            }else if(routeName.toUpperCase().equals("DAYTOTAL")){
                respJson = bossReportDateInfoServiceClient.getbossReportDateInfoDayTotal(params);
            }
        } catch (Exception e) {
            log.error("",e);
        }
        log.info("BOSS报表查询数据接口服务返回参数:{}", respJson);
        return new JsonRepresentation(respJson);
    }
}
