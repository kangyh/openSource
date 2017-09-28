package manage_module;

import com.heepay.codec.Sha;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.SmsUtils;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.modules.cbms.utils.MathUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gzx on 2017/1/10.
 */
public class EncodingPasswordTest {

    private static PropertiesLoader loader = new PropertiesLoader("fastDFS.properties");
    @Test
    public void test01() {
        String code = MathUtils.getRandomString(8);
        String encode = Sha.encode(code);

        System.out.print("加密前的密码：" +code + "--------------" + "加后的密码：" +encode);
    }

    @Test
    public void test02() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        System.out.print(simpleDateFormat.format(new Date()));
    }

    @Test
    public void test3() {

        String str = "http://192.168.162.71/group1/M00/00/10/wKiiR1hIwD-AeLp0AACr3jwebCc850.png";
//        String substring = str.substring(22);
        String property = loader.getProperty("fastdfs.image.host");
        String replace = str.replace(property, "");
        System.out.print(replace);
    }
    @Test
    public void test4() {
        String value = RouteStatus.AUDING.getValue();
        System.out.print(value);
    }
}
