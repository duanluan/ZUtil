package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.lang.StrUtil;

import java.util.Collections;

@Slf4j
@DisplayName("字符串工具类测试")
class StrUtilTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("isAnyBlank：任意一个为空")
  @Test
  void isAnyBlank() {
    println(StrUtil.isAnyBlank("", ""));
    println(StrUtil.isAnyBlank("1", ""));
  }

  @DisplayName("toUnderscore：转换为下划线命名")
  @Test
  void toUnderscore() {
    println(StrUtil.toUnderscore("userName"));
    println(StrUtil.toUnderscore("user_nick$Name"));
  }

  @DisplayName("nCopies：字符串重复 n 遍")
  @Test
  void nCopies() {
    long currentTimeMillis = System.currentTimeMillis();
    String.join("", Collections.nCopies(100000000, "abc"));
    println("Collections.nCopies：" + (System.currentTimeMillis() - currentTimeMillis));

    currentTimeMillis = System.currentTimeMillis();
    StrUtil.nCopies("abc", 100000000);
    println("StringUtils.nCopies：" + (System.currentTimeMillis() - currentTimeMillis));

    println(StrUtil.nCopies("0", 5));
  }
}
