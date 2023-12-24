package top.csaf.jmh.contrast.date;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.date.DateUtil;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class TodayStrTest {

  public static void main(String[] args) {
    // 结果是否相等
    TodayStrTest test = new TodayStrTest();
    System.out.println(test.todayStrByHutool().equals(test.todayStrByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{TodayStrTest.class.getName()});
  }

  @Benchmark
  public String todayStrByHutool() {
    return cn.hutool.core.date.DateUtil.today();
  }

  @Benchmark
  public String todayStrByZUtil() {
    return DateUtil.today();
  }
}

// Benchmark                                                 Mode     Cnt    Score    Error   Units
// TodayStrTest.todayStrByHutool                            thrpt       5    5.042 ±  0.141  ops/us
// TodayStrTest.todayStrByZUtil                             thrpt       5    5.540 ±  0.135  ops/us
// TodayStrTest.todayStrByHutool                             avgt       5    0.182 ±  0.003   us/op
// TodayStrTest.todayStrByZUtil                              avgt       5    0.186 ±  0.004   us/op
// TodayStrTest.todayStrByHutool                           sample  102966    0.221 ±  0.010   us/op
// TodayStrTest.todayStrByHutool:todayStrByHutool·p0.00    sample            0.100            us/op
// TodayStrTest.todayStrByHutool:todayStrByHutool·p0.50    sample            0.200            us/op
// TodayStrTest.todayStrByHutool:todayStrByHutool·p0.90    sample            0.200            us/op
// TodayStrTest.todayStrByHutool:todayStrByHutool·p0.95    sample            0.300            us/op
// TodayStrTest.todayStrByHutool:todayStrByHutool·p0.99    sample            0.400            us/op
// TodayStrTest.todayStrByHutool:todayStrByHutool·p0.999   sample            2.800            us/op
// TodayStrTest.todayStrByHutool:todayStrByHutool·p0.9999  sample           15.007            us/op
// TodayStrTest.todayStrByHutool:todayStrByHutool·p1.00    sample          297.472            us/op
// TodayStrTest.todayStrByZUtil                            sample  102902    0.221 ±  0.004   us/op
// TodayStrTest.todayStrByZUtil:todayStrByZUtil·p0.00      sample            0.100            us/op
// TodayStrTest.todayStrByZUtil:todayStrByZUtil·p0.50      sample            0.200            us/op
// TodayStrTest.todayStrByZUtil:todayStrByZUtil·p0.90      sample            0.300            us/op
// TodayStrTest.todayStrByZUtil:todayStrByZUtil·p0.95      sample            0.300            us/op
// TodayStrTest.todayStrByZUtil:todayStrByZUtil·p0.99      sample            0.400            us/op
// TodayStrTest.todayStrByZUtil:todayStrByZUtil·p0.999     sample            2.410            us/op
// TodayStrTest.todayStrByZUtil:todayStrByZUtil·p0.9999    sample           12.504            us/op
// TodayStrTest.todayStrByZUtil:todayStrByZUtil·p1.00      sample           67.584            us/op
// TodayStrTest.todayStrByHutool                               ss       5   44.960 ± 28.161   us/op
// TodayStrTest.todayStrByZUtil                                ss       5   38.280 ± 30.698   us/op
