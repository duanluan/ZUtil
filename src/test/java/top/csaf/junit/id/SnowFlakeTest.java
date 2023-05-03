package top.csaf.junit.id;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.id.SnowFlake;

@DisplayName("雪花算法测试")
class SnowFlakeTest {

  @DisplayName("简单实现")
  @Test
  void testSimple() {
    SnowFlake snowFlake = new SnowFlake(SnowFlake.builder());
    snowFlake = SnowFlake.builder().dataCenterBit(4).machineBit(5).build();
    snowFlake = new SnowFlake(3, 5);
    // for (int i = 0; i < 1000000; i++) {
      System.out.println(snowFlake.next());;
    // }
  }
}
