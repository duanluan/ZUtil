package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.lang.NumberUtil;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("数字工具类测试")
class NumberUtilTest {

  @DisplayName("ltZero：小于 0，数字为 null 时返回 false")
  @Test
  void compareTo() {
    assertTrue(NumberUtil.ltZero(-1));
    assertTrue(NumberUtil.leZero(0));
    assertTrue(NumberUtil.gtZero(1));
    assertTrue(NumberUtil.geZero(0));
    assertTrue(NumberUtil.eqZero(0));
  }
}
