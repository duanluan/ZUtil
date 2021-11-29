package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

@Slf4j
@DisplayName("正则工具类测试")
public class RegExUtilsTest {

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
