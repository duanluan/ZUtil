package top.csaf.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.idcard.IdCardUtil;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description IdCard工具类测试
 * @Author Rick
 * @Date 2024/6/14 10:52
 **/
@DisplayName("IdCard工具类测试")
public class IdCardUtilTest {


    @DisplayName("校验身份证号码最后一位校验码")
    @Test
    void validateCheckCode() {
        // 校验最后一位为X
        String number = "11010519491231002X";
        boolean Xresult = IdCardUtil.validateCheckCode(number);
        assertTrue(Xresult);
        // 校验最后一位为Y
        number = "11010519491231002Y";
        boolean Yresult = IdCardUtil.validateCheckCode(number);
        assertFalse(Yresult);
        // 校验长度超过18位的情况
        String longNumber = "11010519491231002";
        assertThrows(IllegalArgumentException.class, () -> {
            IdCardUtil.validateCheckCode(longNumber);
        });
        String nullNumber = null;
        // 校验null值
        assertThrows(NullPointerException.class, () -> {
            IdCardUtil.validateCheckCode(nullNumber);
        });
        // 校验空值
        // TODO 此处为18位的限制无法通过
        String blankNumber = "";
        assertThrows(NullPointerException.class, () -> {
            IdCardUtil.validateCheckCode(blankNumber);
        });
    }
}
