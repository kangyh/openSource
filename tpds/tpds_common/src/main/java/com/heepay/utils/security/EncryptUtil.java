package com.heepay.utils.security;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描 述：
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/8/14 9:37
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
public class EncryptUtil {

    private static final Logger log = LogManager.getLogger();

    /**
     * 报文中各别字段加密处理
     * @return
     */
    public static String fieldEncrypt(String str)
    {
        try
        {
            if(str==null)
            {
                return "";
            }
            String[] fields={"certNo","username","phoneNo","oldbankAccountNo","bankAccountNo","bankAccountTelNo"
                    ,"ownerName","ownerCertNo","ownerMobile","bankAccountNo"};
            StringBuffer sbFieldsPattern = new StringBuffer();
            //Pattern p=Pattern.compile("(\"showNum\":)(\".*?\")|(\"version\":)(\".*?\")");
            for (int i=0;i<fields.length;i++)
            {
                sbFieldsPattern.append("(\"");
                sbFieldsPattern.append(fields[i]);
                sbFieldsPattern.append("\":)");
                sbFieldsPattern.append("(\".*?\")");
                if (i+1<fields.length)
                {
                    sbFieldsPattern.append("|");
                }
            }
            Pattern p=Pattern.compile(sbFieldsPattern.toString());
            Matcher m = p.matcher(str);
            StringBuffer sb = new StringBuffer();
            while (m.find())
            {
                m.appendReplacement(sb,m.group(0).split(":")[0]+":\""+ Aes.encryptStr(m.group(0).split(":")[1], Constants.QuickPay.SYSTEM_KEY)+"\"");
            }
            m.appendTail(sb);
            return sb.toString();
        }catch(Exception e)
        {
            e.printStackTrace();
            log.error("报文字段加密处理失败"+e.getMessage());
        }
        return "";
    }
}
