package top.zhogjianhao.id;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("雪花算法测试")
public class SnowFlakeTest {

  @DisplayName("简单实现")
  @Test
  void testSimple() {
    SnowFlake snowFlake = new SnowFlake(SnowFlake.builder());
    snowFlake = SnowFlake.builder().dataCenterBit(4).machineBit(5).build();
    System.out.println(snowFlake.next());
    snowFlake = new SnowFlake(3, 5);
    snowFlake.next();
  }
}
