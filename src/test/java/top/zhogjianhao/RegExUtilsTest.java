package top.zhogjianhao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.regex.Pattern;

@DisplayName("正则工具类测试")
public class RegExUtilsTest {

  public static void main(String[] args) {
    // List<String> list = matches("${1123asdfbjxzc23}\n" +
    //   "\n" +
    //   "${123}", "(?<=\\$\\{).*(?=\\})");
    // System.out.println();
    LocalDate a = null;
    if (a instanceof LocalDate || a ==null) {
      System.out.println(123);
    }
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
