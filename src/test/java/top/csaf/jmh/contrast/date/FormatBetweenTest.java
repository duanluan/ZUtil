package top.csaf.jmh.contrast.date;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.date.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class FormatBetweenTest {

  public static void main(String[] args) {
    // 结果是否相等
    FormatBetweenTest test = new FormatBetweenTest();
    System.out.println(test.fromBetweenByHutool().equals(test.fromBetweenByZUtil()) && test.fromBetweenByZUtil().equals(test.fromBetweenByZUtil1()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{FormatBetweenTest.class.getName()});
  }

  private static final Date date1 = DateUtils.parseDate("2016-03-01 22:33:23");
  private static final Date date2 = DateUtils.parseDate("2016-04-01 23:33:23");

  @Benchmark
  public String fromBetweenByHutool() {
    return DateUtil.formatBetween(date1, date2, BetweenFormatter.Level.SECOND);
  }

  @Benchmark
  public String fromBetweenByZUtil() {
    return DateUtils.formatBetween(date1, date2, "dd天H小时");
  }

  @Benchmark
  public String fromBetweenByZUtil1() {
    return DateUtils.formatBetween(date1, date2, true, null, "天", "小时", "分", "秒", "毫秒");
  }
}

// Benchmark                                                            Mode     Cnt     Score    Error   Units
// FormatBetweenTest.fromBetweenByHutool                               thrpt       5    17.735 ±  0.936  ops/us
// FormatBetweenTest.fromBetweenByZUtil                                thrpt       5     0.780 ±  0.021  ops/us
// FormatBetweenTest.fromBetweenByZUtil1                               thrpt       5    23.787 ±  2.033  ops/us
// FormatBetweenTest.fromBetweenByHutool                                avgt       5     0.056 ±  0.009   us/op
// FormatBetweenTest.fromBetweenByZUtil                                 avgt       5     1.291 ±  0.051   us/op
// FormatBetweenTest.fromBetweenByZUtil1                                avgt       5     0.043 ±  0.004   us/op
// FormatBetweenTest.fromBetweenByHutool                              sample  159179     0.089 ±  0.006   us/op
// FormatBetweenTest.fromBetweenByHutool:fromBetweenByHutool·p0.00    sample               ≈ 0            us/op
// FormatBetweenTest.fromBetweenByHutool:fromBetweenByHutool·p0.50    sample             0.100            us/op
// FormatBetweenTest.fromBetweenByHutool:fromBetweenByHutool·p0.90    sample             0.100            us/op
// FormatBetweenTest.fromBetweenByHutool:fromBetweenByHutool·p0.95    sample             0.100            us/op
// FormatBetweenTest.fromBetweenByHutool:fromBetweenByHutool·p0.99    sample             0.200            us/op
// FormatBetweenTest.fromBetweenByHutool:fromBetweenByHutool·p0.999   sample             0.700            us/op
// FormatBetweenTest.fromBetweenByHutool:fromBetweenByHutool·p0.9999  sample            13.888            us/op
// FormatBetweenTest.fromBetweenByHutool:fromBetweenByHutool·p1.00    sample           245.248            us/op
// FormatBetweenTest.fromBetweenByZUtil                               sample  118584     1.497 ±  0.073   us/op
// FormatBetweenTest.fromBetweenByZUtil:fromBetweenByZUtil·p0.00      sample             1.100            us/op
// FormatBetweenTest.fromBetweenByZUtil:fromBetweenByZUtil·p0.50      sample             1.400            us/op
// FormatBetweenTest.fromBetweenByZUtil:fromBetweenByZUtil·p0.90      sample             1.500            us/op
// FormatBetweenTest.fromBetweenByZUtil:fromBetweenByZUtil·p0.95      sample             1.700            us/op
// FormatBetweenTest.fromBetweenByZUtil:fromBetweenByZUtil·p0.99      sample             2.800            us/op
// FormatBetweenTest.fromBetweenByZUtil:fromBetweenByZUtil·p0.999     sample            13.888            us/op
// FormatBetweenTest.fromBetweenByZUtil:fromBetweenByZUtil·p0.9999    sample           182.144            us/op
// FormatBetweenTest.fromBetweenByZUtil:fromBetweenByZUtil·p1.00      sample          1585.152            us/op
// FormatBetweenTest.fromBetweenByZUtil1                              sample  140450     0.088 ±  0.003   us/op
// FormatBetweenTest.fromBetweenByZUtil1:fromBetweenByZUtil1·p0.00    sample               ≈ 0            us/op
// FormatBetweenTest.fromBetweenByZUtil1:fromBetweenByZUtil1·p0.50    sample             0.100            us/op
// FormatBetweenTest.fromBetweenByZUtil1:fromBetweenByZUtil1·p0.90    sample             0.100            us/op
// FormatBetweenTest.fromBetweenByZUtil1:fromBetweenByZUtil1·p0.95    sample             0.100            us/op
// FormatBetweenTest.fromBetweenByZUtil1:fromBetweenByZUtil1·p0.99    sample             0.200            us/op
// FormatBetweenTest.fromBetweenByZUtil1:fromBetweenByZUtil1·p0.999   sample             0.700            us/op
// FormatBetweenTest.fromBetweenByZUtil1:fromBetweenByZUtil1·p0.9999  sample            12.882            us/op
// FormatBetweenTest.fromBetweenByZUtil1:fromBetweenByZUtil1·p1.00    sample            68.992            us/op
// FormatBetweenTest.fromBetweenByHutool                                  ss       5    25.120 ± 72.558   us/op
// FormatBetweenTest.fromBetweenByZUtil                                   ss       5   150.900 ± 81.467   us/op
// FormatBetweenTest.fromBetweenByZUtil1                                  ss       5    12.780 ± 14.689   us/op
