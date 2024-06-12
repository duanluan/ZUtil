package top.csaf.jmh.contrast.date;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.date.DateUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class BeginEndTimeTest {

  public static void main(String[] args) {
    // 结果是否相等
    BeginEndTimeTest test = new BeginEndTimeTest();
    System.out.println(test.beginTimeByHutool().equals(test.beginTimeByZUtil()));
    System.out.println(test.endTimeByHutool().equals(test.endTimeByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{BeginEndTimeTest.class.getName()});
  }

  private static final LocalDateTime nowDateTime = LocalDateTime.now();
  private static final Date nowDate = DateUtil.toDate(nowDateTime);

  @Benchmark
  public Date beginTimeByHutool() {
    return cn.hutool.core.date.DateUtil.beginOfDay(nowDate).toJdkDate();
  }

  @Benchmark
  public Date beginTimeByZUtil() {
    return DateUtil.minDate(nowDateTime, ChronoField.MICRO_OF_DAY);
  }

  @Benchmark
  public Date endTimeByHutool() {
    return cn.hutool.core.date.DateUtil.endOfDay(nowDate).toJdkDate();
  }

  @Benchmark
  public Date endTimeByZUtil() {
    return DateUtil.maxDate(nowDateTime, ChronoField.MICRO_OF_DAY);
  }
}

// Benchmark                                                       Mode     Cnt     Score    Error   Units
// BeginEndTimeTest.beginTimeByHutool                             thrpt       5     3.721 ±  0.086  ops/us
// BeginEndTimeTest.beginTimeByZUtil                              thrpt       5     6.591 ±  0.400  ops/us
// BeginEndTimeTest.endTimeByHutool                               thrpt       5     3.351 ±  0.089  ops/us
// BeginEndTimeTest.endTimeByZUtil                                thrpt       5     6.696 ±  0.234  ops/us
// BeginEndTimeTest.beginTimeByHutool                              avgt       5     0.274 ±  0.009   us/op
// BeginEndTimeTest.beginTimeByZUtil                               avgt       5     0.150 ±  0.009   us/op
// BeginEndTimeTest.endTimeByHutool                                avgt       5     0.301 ±  0.036   us/op
// BeginEndTimeTest.endTimeByZUtil                                 avgt       5     0.151 ±  0.005   us/op
// BeginEndTimeTest.beginTimeByHutool                            sample  136425     0.338 ±  0.052   us/op
// BeginEndTimeTest.beginTimeByHutool:beginTimeByHutool·p0.00    sample             0.200            us/op
// BeginEndTimeTest.beginTimeByHutool:beginTimeByHutool·p0.50    sample             0.300            us/op
// BeginEndTimeTest.beginTimeByHutool:beginTimeByHutool·p0.90    sample             0.300            us/op
// BeginEndTimeTest.beginTimeByHutool:beginTimeByHutool·p0.95    sample             0.300            us/op
// BeginEndTimeTest.beginTimeByHutool:beginTimeByHutool·p0.99    sample             0.600            us/op
// BeginEndTimeTest.beginTimeByHutool:beginTimeByHutool·p0.999   sample             3.400            us/op
// BeginEndTimeTest.beginTimeByHutool:beginTimeByHutool·p0.9999  sample            24.835            us/op
// BeginEndTimeTest.beginTimeByHutool:beginTimeByHutool·p1.00    sample          1556.480            us/op
// BeginEndTimeTest.beginTimeByZUtil                             sample  125522     0.200 ±  0.034   us/op
// BeginEndTimeTest.beginTimeByZUtil:beginTimeByZUtil·p0.00      sample             0.100            us/op
// BeginEndTimeTest.beginTimeByZUtil:beginTimeByZUtil·p0.50      sample             0.200            us/op
// BeginEndTimeTest.beginTimeByZUtil:beginTimeByZUtil·p0.90      sample             0.200            us/op
// BeginEndTimeTest.beginTimeByZUtil:beginTimeByZUtil·p0.95      sample             0.200            us/op
// BeginEndTimeTest.beginTimeByZUtil:beginTimeByZUtil·p0.99      sample             0.400            us/op
// BeginEndTimeTest.beginTimeByZUtil:beginTimeByZUtil·p0.999     sample             2.200            us/op
// BeginEndTimeTest.beginTimeByZUtil:beginTimeByZUtil·p0.9999    sample            13.046            us/op
// BeginEndTimeTest.beginTimeByZUtil:beginTimeByZUtil·p1.00      sample          1005.568            us/op
// BeginEndTimeTest.endTimeByHutool                              sample  127644     0.363 ±  0.061   us/op
// BeginEndTimeTest.endTimeByHutool:endTimeByHutool·p0.00        sample             0.200            us/op
// BeginEndTimeTest.endTimeByHutool:endTimeByHutool·p0.50        sample             0.300            us/op
// BeginEndTimeTest.endTimeByHutool:endTimeByHutool·p0.90        sample             0.400            us/op
// BeginEndTimeTest.endTimeByHutool:endTimeByHutool·p0.95        sample             0.400            us/op
// BeginEndTimeTest.endTimeByHutool:endTimeByHutool·p0.99        sample             0.700            us/op
// BeginEndTimeTest.endTimeByHutool:endTimeByHutool·p0.999       sample             3.600            us/op
// BeginEndTimeTest.endTimeByHutool:endTimeByHutool·p0.9999      sample            28.651            us/op
// BeginEndTimeTest.endTimeByHutool:endTimeByHutool·p1.00        sample          2072.576            us/op
// BeginEndTimeTest.endTimeByZUtil                               sample  128657     0.181 ±  0.002   us/op
// BeginEndTimeTest.endTimeByZUtil:endTimeByZUtil·p0.00          sample             0.100            us/op
// BeginEndTimeTest.endTimeByZUtil:endTimeByZUtil·p0.50          sample             0.200            us/op
// BeginEndTimeTest.endTimeByZUtil:endTimeByZUtil·p0.90          sample             0.200            us/op
// BeginEndTimeTest.endTimeByZUtil:endTimeByZUtil·p0.95          sample             0.200            us/op
// BeginEndTimeTest.endTimeByZUtil:endTimeByZUtil·p0.99          sample             0.300            us/op
// BeginEndTimeTest.endTimeByZUtil:endTimeByZUtil·p0.999         sample             1.900            us/op
// BeginEndTimeTest.endTimeByZUtil:endTimeByZUtil·p0.9999        sample             8.575            us/op
// BeginEndTimeTest.endTimeByZUtil:endTimeByZUtil·p1.00          sample            36.160            us/op
// BeginEndTimeTest.beginTimeByHutool                                ss       5    55.820 ± 74.713   us/op
// BeginEndTimeTest.beginTimeByZUtil                                 ss       5    39.680 ± 20.583   us/op
// BeginEndTimeTest.endTimeByHutool                                  ss       5    64.840 ± 43.092   us/op
// BeginEndTimeTest.endTimeByZUtil                                   ss       5    51.700 ± 36.222   us/op
