package com.heepay.manage.modules.reconciliation.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对字符串加密 加密算法使用SHA-1,SHA-256,默认使用SHA-256
 * @author wjp
 *
 */
/**
 *
 *
 * 描    述：对字符串加密 加密算法使用SHA-1,SHA-256,默认使用SHA-256
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月9日 下午5:32:05
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
public class StringEncrypt {

    private static StringEncrypt instance = null;

    private StringEncrypt() {

    }

    public static StringEncrypt getInstance() {
        if (instance == null)
            instance = new StringEncrypt();
        return instance;
    }

    /**
     * 对字符串加密,加密算法使用SHA-1,SHA-256,默认使用SHA-256
     * @param strSrc 要加密的字符串
     * @param encName 加密类型
     * @return
     */
    @SuppressWarnings("static-access")
    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = new BASE64().encode(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /*
     * 常青提供的
     * 原字符串06b41ddd8c6c42b3b5203d2511860eaf3b00b5aef58748e4a7db14e578d34cd397103c0160354fa39951199a04687d2b69ea169066774ae7b68e2abf98d2cb11b41f98f3be064e8b8d026d78a4286bce9fdd0cf9655f46298701d68fe69f97bcf75234d4d291426fa81b73f90a909c14a5b7ba60e0204fb4b8c17ecb1beb0073
     * |248329043920489032|1478528261513
     * 密文：KyP6BP61+sB7WZrHWNq5qtGyKPxnCj3tBCiN2WapFZo= 密钥：
     * 06b41ddd8c6c42b3b5203d2511860eaf3b00b5aef58748e4a7db14e578d34cd397103c0160354fa39951199a04687d2b69ea169066774ae7b68e2abf98d2cb11b41f98f3be064e8b8d026d78a4286bce9fdd0cf9655f46298701d68fe69f97bcf75234d4d291426fa81b73f90a909c14a5b7ba60e0204fb4b8c17ecb1beb0073
     */
    public static void main(String args[]) {
        String sha256 = StringEncrypt.Encrypt("汪业培", "SHA-256");
        System.out.println("sha256：" + sha256);// ISGMyneATSuhkiwz4BURBR==ISGMyneATSuhkiwz4BURBQ==
        //String ss = "06b41ddd8c6c42b3b5203d2511860eaf3b00b5aef58748e4a7db14e578d34cd397103c0160354fa39951199a04687d2b69ea169066774ae7b68e2abf98d2cb11b41f98f3be064e8b8d026d78a4286bce9fdd0cf9655f46298701d68fe69f97bcf75234d4d291426fa81b73f90a909c14a5b7ba60e0204fb4b8c17ecb1beb0073";
        String dd = "1478857686659|U01|150105198611194390|001||15801330003|jdbdkfsnflksdfkyyyrtreyyr|100011000006073500|朋朋";
        String md5 = StringEncrypt.Encrypt(dd, "SHA-256");
        System.out.println("md5：" + md5);

    }
}