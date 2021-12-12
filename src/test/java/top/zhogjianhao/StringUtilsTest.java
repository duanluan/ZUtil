package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("字符串工具类测试")
public class StringUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("isAnyBlank")
  @Test
  void isAnyBlank() {
    println(StringUtils.isAnyBlank("", ""));
    println(StringUtils.isAnyBlank("1", ""));
  }

  @DisplayName("toUnderscore：转换为下划线命名")
  @Test
  void toUnderscore() {
    println(StringUtils.toUnderscore("userName"));
    println(StringUtils.toUnderscore("user_nick$Name"));
  }
}
