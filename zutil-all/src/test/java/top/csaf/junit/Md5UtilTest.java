package top.csaf.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.crypto.Md5Util;
import top.csaf.io.FileUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MD5 工具类测试
 */
@DisplayName("MD5 工具类测试")
public class Md5UtilTest {


  @DisplayName("MD5 加密")
  @Test
  void to() throws IOException {
    // 字符串类型
    String input = "hello";
    // 大写
    assertEquals("5D41402ABC4B2A76B9719D911017C592", Md5Util.toUpperCase(input));
    // 大写短写（中间 16 位）
    assertEquals("BC4B2A76B9719D91", Md5Util.toUpperCaseShort(input));
    // 小写
    assertEquals("5d41402abc4b2a76b9719d911017c592", Md5Util.toLowerCase(input));
    // 小写短写（中间 16 位）
    assertEquals("bc4b2a76b9719d91", Md5Util.toLowerCaseShort(input));

    // byte[] 类型
    byte[] bytes = FileUtil.readFileToByteArray(new File("C:\\Windows\\notepad.exe"));
    // 大写
    assertEquals("FF3E29FDFAFA0E9030E2FCD71489D41D", Md5Util.toUpperCase(bytes));
    // 大写短写（中间 16 位）
    assertEquals("FAFA0E9030E2FCD7", Md5Util.toUpperCaseShort(bytes));
    // 小写
    assertEquals("ff3e29fdfafa0e9030e2fcd71489d41d", Md5Util.toLowerCase(bytes));
    // 小写短写（中间 16 位）
    assertEquals("fafa0e9030e2fcd7", Md5Util.toLowerCaseShort(bytes));

    // null
    assertEquals("", Md5Util.toUpperCase((String) null));
    assertEquals("", Md5Util.toUpperCase((byte[]) null));
    assertEquals("", Md5Util.toUpperCase(""));
  }
}
