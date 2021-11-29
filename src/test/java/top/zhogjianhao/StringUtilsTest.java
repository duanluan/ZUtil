package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("字符串工具类测试")
public class StringUtilsTest {

  @DisplayName("toUnderscore")
  @Test
  void toUnderscore() {
    System.out.println(StringUtils.toUnderscore("userName"));
    System.out.println(StringUtils.toUnderscore("user_nick$Name"));
  }
}
