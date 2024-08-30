package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.regex.RegExUtil;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("正则工具类测试")
class RegExUtilTest {

  @Test
  void replaceAllSpecialChar() {
    assertEquals("\\/\\\\\\(\\)\\[\\]\\{\\}\\.\\?\\+\\*\\|\\^\\$", RegExUtil.replaceAllSpecialChar("/\\()[]{}.?+*|^$"));
  }

  @Test
  void getMatcher() {
    assertEquals(Pattern.compile("", Pattern.MULTILINE).matcher("").toString(), RegExUtil.getMatcher("", "", Pattern.MULTILINE).toString());
    assertEquals(Pattern.compile("").matcher("").toString(), RegExUtil.getMatcher("", "").toString());
  }

  @Test
  void indexOf() {
    assertEquals(4, RegExUtil.indexOf("123\n456", "4", Pattern.MULTILINE));
    assertEquals(2, RegExUtil.indexOf("123", "3"));
  }

  @Test
  void match() {
    assertThrows(IllegalArgumentException.class, () -> RegExUtil.match("", "", -1, 1, 0));
    assertThrows(IllegalArgumentException.class, () -> RegExUtil.match("", "", 0, -1, 0));

    assertEquals("6", RegExUtil.match("123\n456", "\\d+(\\d)", 1, 1, 0));
  }

  @Test
  void matches() {
    assertEquals("6", RegExUtil.matches("123\n456", "\\d+(\\d)", 1, 0).get(1));
  }


  @Test
  void replace() {
    assertEquals("123|467", RegExUtil.replace("123|567", "(\\d)\\d+(\\d)", "4", 1, 1, 0));
    assertEquals("1223|4667", RegExUtil.replace("1223|5667", "(\\d)\\d+(\\d)", "4", 1, 1, 0));
  }

  @Test
  void replaceAll() {
    assertEquals("423|467", RegExUtil.replaceAll("123|567", "(\\d)\\d+(\\d)", "4", 1, 0));
    assertEquals("{\"b\":[\"123\"],\"ab\":123,\"b\":\"123\"}", RegExUtil.replaceAll("{\"a\":[\"123\"],\"az\":123,\"d\":\"123\"}", "\\\"(((?!\\\").)*)\\\":", "b", 2, 0));
  }
}
