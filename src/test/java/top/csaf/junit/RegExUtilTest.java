package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.regex.RegExUtil;
import top.csaf.regex.enums.FlagsEnum;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("正则工具类测试")
class RegExUtilTest {

  @Test
  void testCodeCoverage() {
    // region replaceAllSpecialChar
    assertThrows(NullPointerException.class, () -> RegExUtil.replaceAllSpecialChar(null));
    assertEquals("\\/\\\\\\(\\)\\[\\]\\{\\}\\.\\?\\+\\*\\|\\^\\$", RegExUtil.replaceAllSpecialChar("/\\()[]{}.?+*|^$"));
    // endregion


    // region getMatcher
    assertEquals(Pattern.compile("", 0).matcher("").toString(), RegExUtil.getMatcher("", "", 0).toString());
    assertEquals(Pattern.compile("", Pattern.MULTILINE).matcher("").toString(), RegExUtil.getMatcher("", "", FlagsEnum.MULTILINE).toString());
    assertEquals(Pattern.compile("").matcher("").toString(), RegExUtil.getMatcher("", "").toString());
    // endregion


    // region indexOf
    assertEquals(0, RegExUtil.indexOf("", "", 0));
    assertEquals(0, RegExUtil.indexOf("", "", FlagsEnum.MULTILINE));
    assertEquals(0, RegExUtil.indexOf("", ""));
    // endregion


    // region match
    assertThrows(IllegalArgumentException.class, () -> RegExUtil.match("", "", -1, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> RegExUtil.match("", "", 0, -1, 0));
    assertEquals("1", RegExUtil.match("11", "1", 1, 0, 0));
    // endregion
  }

  @DisplayName("替换所有匹配项指定捕获组的匹配值")
  @Test
  void testReplaceAll() {
    // 替换所有匹配项，第二个捕获组的匹配值
    System.out.println(
      RegExUtil.replaceAll(
        "{\"a\":[\"123\"],\"az\":123,\"d\":\"123\"}",
        // Match 2："az":
        // Group 1：az
        // Group 2：z
        "\\\"(((?!\\\").)*)\\\":",
        "XXX",
        2,
        Pattern.MULTILINE)
    );
  }
}
