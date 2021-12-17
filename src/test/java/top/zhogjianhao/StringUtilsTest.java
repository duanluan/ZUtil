package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@Slf4j
@DisplayName("字符串工具类测试")
public class StringUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("isAnyBlank：任意一个为空")
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

  @Test
  void nCopies() {
    long currentTimeMillis = System.currentTimeMillis();
    String.join("", Collections.nCopies(100000000, "abc"));
    println("Collections.nCopies：" + (System.currentTimeMillis() - currentTimeMillis));

    currentTimeMillis = System.currentTimeMillis();
    StringUtils.nCopies("abc", 100000000);
    println("StringUtils.nCopies：" + (System.currentTimeMillis() - currentTimeMillis));

    println(StringUtils.nCopies("0", 5));
  }
}
