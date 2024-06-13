package top.csaf.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.crypto.Md5Util;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description MD5工具类测试
 * @Author Rick
 * @Date 2024/6/13 9:49
 **/
@DisplayName("MD5工具类测试")
public class Md5UtilTest {


    @DisplayName("MD5 加密")
    @Test
    void to() {
        // 大写转换
        String input = "hello";
        String expected = "5D41402ABC4B2A76B9719D911017C592";
        String upperActual = Md5Util.to(input, true);
        assertEquals(expected, upperActual, "The MD5 hash of 'hello' in uppercase should match the expected value.");
        // 小写转换
        expected = "5d41402abc4b2a76b9719d911017c592";
        String lowerActual = Md5Util.to(input, false);
        assertEquals(expected, lowerActual, "The MD5 hash of 'hello' in lowercase should match the expected value.");
        // 空字符转换
        input = "";
        String expectedUpperCase = "D41D8CD98F00B204E9800998ECF8427E";
        String expectedLowerCase = "d41d8cd98f00b204e9800998ecf8427e";
        String actualUpperCase = Md5Util.to(input, true);
        assertEquals(expectedUpperCase, actualUpperCase, "The MD5 hash of an empty string in uppercase should match the expected value.");
        String actualLowerCase = Md5Util.to(input, false);
        assertEquals(expectedLowerCase, actualLowerCase, "The MD5 hash of an empty string in lowercase should match the expected value.");
        // null值抛出异常
        assertThrows(NullPointerException.class, () -> Md5Util.to(null, true), "Passing null to the to() method should throw a NullPointerException.");
        assertThrows(NullPointerException.class, () -> Md5Util.to(null, false), "Passing null to the to() method should throw a NullPointerException.");
    }
}
