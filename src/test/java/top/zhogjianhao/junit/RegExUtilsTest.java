package top.zhogjianhao.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.regex.RegExUtils;
import top.zhogjianhao.regex.enums.FlagsEnum;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("正则工具类测试")
public class RegExUtilsTest {

  @Test
  void testCodeCoverage() {
    // region replaceAllSpecialChar
    assertThrows(NullPointerException.class, () -> RegExUtils.replaceAllSpecialChar(null));
    assertEquals("\\/\\\\\\(\\)\\[\\]\\{\\}\\.\\?\\+\\*\\|\\^\\$", RegExUtils.replaceAllSpecialChar("/\\()[]{}.?+*|^$"));
    // endregion


    // region getMatcher
    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher(null, "", 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher("", null, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher(null, null, 0));
    assertEquals(Pattern.compile("", 0).matcher("").toString(), RegExUtils.getMatcher("", "", 0).toString());

    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher(null, "", FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher("", null, FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher(null, null, FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher(null, null, null));
    assertEquals(Pattern.compile("", Pattern.MULTILINE).matcher("").toString(), RegExUtils.getMatcher("", "", FlagsEnum.MULTILINE).toString());

    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher(null, ""));
    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher("", null));
    assertThrows(NullPointerException.class, () -> RegExUtils.getMatcher(null, null));
    assertEquals(Pattern.compile("").matcher("").toString(), RegExUtils.getMatcher("", "").toString());
    // endregion


    // region indexOf
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, "", 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf("", null, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, null, 0));
    assertEquals(0, RegExUtils.indexOf("", "", 0));

    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, "", FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf("", null, FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, null, FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, null, null));
    assertEquals(0, RegExUtils.indexOf("", "", FlagsEnum.MULTILINE));

    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, ""));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf("", (String) null));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, (String) null));
    assertEquals(0, RegExUtils.indexOf("", ""));

    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, ""));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf("", (Pattern) null));
    assertThrows(NullPointerException.class, () -> RegExUtils.indexOf(null, (Pattern) null));
    assertEquals(0, RegExUtils.indexOf("", Pattern.compile("")));
    // endregion


    // region match
    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, "", 0, 0, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match("", null, 0, 0, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, null, 0, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> RegExUtils.match("", "", -1, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> RegExUtils.match("", "", 0, -1, 0));
    assertEquals("1", RegExUtils.match("11", "1", 1, 0, 0));

    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, "", 0, 0, FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.match("", null, 0, 0, FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.match("", "", 0, 0, null));
    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, null, 0, 0, FlagsEnum.MULTILINE));
    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, null, 0, 0, null));

    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, "", 0, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match("", (String) null, 0, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, (String) null, 0, 0));

    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, Pattern.compile(""), 0, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match("", (Pattern) null, 0, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, (Pattern) null, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> RegExUtils.match("", Pattern.compile(""), -1, 0));
    assertThrows(IllegalArgumentException.class, () -> RegExUtils.match("", Pattern.compile(""), 0, -1));
    assertEquals("1", RegExUtils.match("11", Pattern.compile("1"), 1, 0));

    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, "", 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match("", (String) null, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, (String) null, 0));

    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, Pattern.compile(""), 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match("", (Pattern) null, 0));
    assertThrows(NullPointerException.class, () -> RegExUtils.match(null, (Pattern) null, 0));
    // endregion
  }

  @DisplayName("替换所有匹配项指定捕获组的匹配值")
  @Test
  void testReplaceAll() {
    // 替换所有匹配项，第二个捕获组的匹配值
    System.out.println(
      RegExUtils.replaceAll(
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
