package com.heepay.manage.modules.route.web;

import com.heepay.manage.common.utils.CacheUtils;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.DownloadAccFile;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述：ecache的缓存清除
 * <p>
 * 创建者  ly
 * 创建时间 2017-03-01-11:16
 * 创建描述：
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Controller
@RequestMapping(value = "${adminPath}/route/cache")
public class CacheDeleteController extends BaseController {

    @RequestMapping(value="")
    public String list(Model model){
        return "modules/route/cacheDelete";
    }

    @RequestMapping(value="/delete")
    public String delete(String key, Model model){
        if(null != CacheUtils.get(key)){
            CacheUtils.remove(key);
        }
        model.addAttribute("key",key);
        return "modules/route/cacheDelete";
    }

    @RequestMapping(value="/deleteSession")
    public String deleteSession(String sesskey, Model model){
        if(null != UserUtils.getCache(sesskey)){
            UserUtils.removeCache(sesskey);
        }
        model.addAttribute("sesskey",sesskey);
        return "modules/route/cacheDelete";
    }
}
