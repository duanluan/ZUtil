package top.zhogjianhao.jmh.contrast.date;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class IsLeapYearTest {

  public static void main(String[] args) {
    // 结果是否相等
    IsLeapYearTest test = new IsLeapYearTest();
    System.out.println(test.betweenByHutool() == test.betweenByZUtil());
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{IsLeapYearTest.class.getName()});
  }

  @Benchmark
  public boolean betweenByHutool() {
    return DateUtil.isLeapYear(2000);
  }

  @Benchmark
  public boolean betweenByZUtil() {
    return DateUtil.isLeapYear(2000);
  }
}

// Benchmark                                                 Mode     Cnt     Score    Error   Units
// IsLeapYearTest.betweenByHutool                           thrpt       5     9.851 ±  0.208  ops/us
// IsLeapYearTest.betweenByZUtil                            thrpt       5     9.899 ±  0.240  ops/us
// IsLeapYearTest.betweenByHutool                            avgt       5     0.109 ±  0.003   us/op
// IsLeapYearTest.betweenByZUtil                             avgt       5     0.104 ±  0.008   us/op
// IsLeapYearTest.betweenByHutool                          sample  185932     0.138 ±  0.007   us/op
// IsLeapYearTest.betweenByHutool:betweenByHutool·p0.00    sample             0.100            us/op
// IsLeapYearTest.betweenByHutool:betweenByHutool·p0.50    sample             0.100            us/op
// IsLeapYearTest.betweenByHutool:betweenByHutool·p0.90    sample             0.200            us/op
// IsLeapYearTest.betweenByHutool:betweenByHutool·p0.95    sample             0.200            us/op
// IsLeapYearTest.betweenByHutool:betweenByHutool·p0.99    sample             0.300            us/op
// IsLeapYearTest.betweenByHutool:betweenByHutool·p0.999   sample             0.607            us/op
// IsLeapYearTest.betweenByHutool:betweenByHutool·p0.9999  sample            21.805            us/op
// IsLeapYearTest.betweenByHutool:betweenByHutool·p1.00    sample           198.144            us/op
// IsLeapYearTest.betweenByZUtil                           sample  181289     0.145 ±  0.019   us/op
// IsLeapYearTest.betweenByZUtil:betweenByZUtil·p0.00      sample             0.100            us/op
// IsLeapYearTest.betweenByZUtil:betweenByZUtil·p0.50      sample             0.100            us/op
// IsLeapYearTest.betweenByZUtil:betweenByZUtil·p0.90      sample             0.200            us/op
// IsLeapYearTest.betweenByZUtil:betweenByZUtil·p0.95      sample             0.200            us/op
// IsLeapYearTest.betweenByZUtil:betweenByZUtil·p0.99      sample             0.300            us/op
// IsLeapYearTest.betweenByZUtil:betweenByZUtil·p0.999     sample             1.700            us/op
// IsLeapYearTest.betweenByZUtil:betweenByZUtil·p0.9999    sample            19.920            us/op
// IsLeapYearTest.betweenByZUtil:betweenByZUtil·p1.00      sample          1030.144            us/op
// IsLeapYearTest.betweenByHutool                              ss       5    24.320 ±  9.823   us/op
// IsLeapYearTest.betweenByZUtil                               ss       5    20.820 ± 32.308   us/op
