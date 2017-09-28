package com.heepay.prom.modules.prom.utils;

import com.heepay.codec.Base64;
import com.heepay.codec.CodecException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;

/**
 * 描 述：AES加密
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/9/14 13:53
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
public class AesUtils {

    /**
     * @return String  解密后的字符串
     */
    public static String Aes256Decode(byte[] bytes, byte[] key){
        String result = null;
        try{
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = cipher.doFinal(bytes);
            result = new String(decoded, "UTF-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, CodecException {
        String context = "9fKilEUOlGnYPxipRLdy1NiPTd6GBJ8/ut3HDeuwaPkgtKpJahZSawX7jClxY9jzhxYOjC+2r8IJUkmCxLYst4J8qXFMVGpRwg9pgD1uL3Q218wsFsVH0yLAUY71q2bpRPxEqVnnWl1IyNkpXL2+MkHgMLyF0R4y9T7ERyFT101uWy+FGQcwwyQMzrL8HTC9B4oEFphkJjJ1RrJ+cBWdiptsZUor99QVcqV2CE9Ho6iADADyXwTQBbSoUHgVd7q5PTtSgt9yFxms/dIUvRCgCj2xyO78dakYrsMwKfeGDWPe5boJscjIHnkw80MG+ahr4AfM5pudcyaDLm6CRFdhbnqKwJqZCwHt/46SjAQclBy8kTw94ZBuG7oBHiKxBQyiByLDv26jSN0n2WpEhfuWQBcU17Yb686enzLXqbkQFwYCvLkV/evFYy4iYsHh3h8tDl8i+y7ESaBuG4snToi/A9eYtuqJV8dIoZuC2XMbBWABClLn/Rs0TNEgAZLwgXFJCVwQI3Twki6KCG1IHFWB3ul7wo0c79xy0kB8GNh5y5k=";
        String key = "wx_auth_redirect_key_aSdsSdDowQr";
        String str = AesUtils.Aes256Decode(Base64.decode(context),key.getBytes());
        System.out.println(str);
    }
}
