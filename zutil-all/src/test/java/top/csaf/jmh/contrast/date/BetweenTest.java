package top.csaf.jmh.contrast.date;

import cn.hutool.core.date.DateUnit;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.date.DateUtil;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class BetweenTest {

  public static void main(String[] args) {
    // 结果是否相等
    BetweenTest test = new BetweenTest();
    System.out.println(test.betweenByHutool() == test.betweenByZUtil());
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{BetweenTest.class.getName()});
  }

  private static final Date date1 = DateUtil.parseDate("2017-03-01 22:33:23");
  private static final Date date2 = DateUtil.parseDate("2017-04-01 23:33:23");

  @Benchmark
  public long betweenByHutool() {
    return cn.hutool.core.date.DateUtil.between(date1, date2, DateUnit.DAY);
  }

  @Benchmark
  public long betweenByZUtil() {
    return Math.abs(DateUtil.between(date1, date2, ChronoUnit.DAYS));
  }
}

// Benchmark                                              Mode     Cnt    Score    Error   Units
// BetweenTest.betweenByHutool                           thrpt       5  158.434 ± 34.343  ops/us
// BetweenTest.betweenByZUtil                            thrpt       5  226.887 ±  5.305  ops/us
// BetweenTest.betweenByHutool                            avgt       5    0.006 ±  0.001   us/op
// BetweenTest.betweenByZUtil                             avgt       5    0.004 ±  0.001   us/op
// BetweenTest.betweenByHutool                          sample  175151    0.038 ±  0.001   us/op
// BetweenTest.betweenByHutool:betweenByHutool·p0.00    sample              ≈ 0            us/op
// BetweenTest.betweenByHutool:betweenByHutool·p0.50    sample              ≈ 0            us/op
// BetweenTest.betweenByHutool:betweenByHutool·p0.90    sample            0.100            us/op
// BetweenTest.betweenByHutool:betweenByHutool·p0.95    sample            0.100            us/op
// BetweenTest.betweenByHutool:betweenByHutool·p0.99    sample            0.100            us/op
// BetweenTest.betweenByHutool:betweenByHutool·p0.999   sample            0.200            us/op
// BetweenTest.betweenByHutool:betweenByHutool·p0.9999  sample            3.048            us/op
// BetweenTest.betweenByHutool:betweenByHutool·p1.00    sample           23.872            us/op
// BetweenTest.betweenByZUtil                           sample  169094    0.039 ±  0.002   us/op
// BetweenTest.betweenByZUtil:betweenByZUtil·p0.00      sample              ≈ 0            us/op
// BetweenTest.betweenByZUtil:betweenByZUtil·p0.50      sample              ≈ 0            us/op
// BetweenTest.betweenByZUtil:betweenByZUtil·p0.90      sample            0.100            us/op
// BetweenTest.betweenByZUtil:betweenByZUtil·p0.95      sample            0.100            us/op
// BetweenTest.betweenByZUtil:betweenByZUtil·p0.99      sample            0.100            us/op
// BetweenTest.betweenByZUtil:betweenByZUtil·p0.999     sample            0.100            us/op
// BetweenTest.betweenByZUtil:betweenByZUtil·p0.9999    sample            4.724            us/op
// BetweenTest.betweenByZUtil:betweenByZUtil·p1.00      sample           93.568            us/op
// BetweenTest.betweenByHutool                              ss       5    6.980 ±  8.841   us/op
// BetweenTest.betweenByZUtil                               ss       5    4.320 ±  5.104   us/op
