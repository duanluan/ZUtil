package top.zhogjianhao.jmh.contrast.date;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.date.DateUtils;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class NowDateStrTest {

  public static void main(String[] args) {
    // 结果是否相等
    NowDateStrTest test = new NowDateStrTest();
    System.out.println(test.nowDateTimStrByHutool().equals(test.nowDateTimStrByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{NowDateStrTest.class.getName()});
  }

  @Benchmark
  public String nowDateTimStrByHutool() {
    return DateUtil.now();
  }

  @Benchmark
  public String nowDateTimStrByZUtil() {
    return DateUtils.now();
  }
}

// Benchmark                                                            Mode     Cnt    Score     Error   Units
// NowDateStrTest.nowDateTimStrByHutool                                 thrpt       5    4.245 ±   0.203  ops/us
// NowDateStrTest.nowDateTimStrByZUtil                                  thrpt       5    4.386 ±   0.109  ops/us
// NowDateStrTest.nowDateTimStrByHutool                                  avgt       5    0.233 ±   0.002   us/op
// NowDateStrTest.nowDateTimStrByZUtil                                   avgt       5    0.224 ±   0.006   us/op
// NowDateStrTest.nowDateTimStrByHutool                                sample  162146    0.268 ±   0.003   us/op
// NowDateStrTest.nowDateTimStrByHutool:nowDateTimStrByHutool·p0.00    sample            0.200             us/op
// NowDateStrTest.nowDateTimStrByHutool:nowDateTimStrByHutool·p0.50    sample            0.300             us/op
// NowDateStrTest.nowDateTimStrByHutool:nowDateTimStrByHutool·p0.90    sample            0.300             us/op
// NowDateStrTest.nowDateTimStrByHutool:nowDateTimStrByHutool·p0.95    sample            0.300             us/op
// NowDateStrTest.nowDateTimStrByHutool:nowDateTimStrByHutool·p0.99    sample            0.500             us/op
// NowDateStrTest.nowDateTimStrByHutool:nowDateTimStrByHutool·p0.999   sample            3.385             us/op
// NowDateStrTest.nowDateTimStrByHutool:nowDateTimStrByHutool·p0.9999  sample           12.906             us/op
// NowDateStrTest.nowDateTimStrByHutool:nowDateTimStrByHutool·p1.00    sample          109.312             us/op
// NowDateStrTest.nowDateTimStrByZUtil                                 sample  175567    0.253 ±   0.006   us/op
// NowDateStrTest.nowDateTimStrByZUtil:nowDateTimStrByZUtil·p0.00      sample            0.200             us/op
// NowDateStrTest.nowDateTimStrByZUtil:nowDateTimStrByZUtil·p0.50      sample            0.200             us/op
// NowDateStrTest.nowDateTimStrByZUtil:nowDateTimStrByZUtil·p0.90      sample            0.300             us/op
// NowDateStrTest.nowDateTimStrByZUtil:nowDateTimStrByZUtil·p0.95      sample            0.300             us/op
// NowDateStrTest.nowDateTimStrByZUtil:nowDateTimStrByZUtil·p0.99      sample            0.500             us/op
// NowDateStrTest.nowDateTimStrByZUtil:nowDateTimStrByZUtil·p0.999     sample            1.200             us/op
// NowDateStrTest.nowDateTimStrByZUtil:nowDateTimStrByZUtil·p0.9999    sample            9.357             us/op
// NowDateStrTest.nowDateTimStrByZUtil:nowDateTimStrByZUtil·p1.00      sample          232.448             us/op
// NowDateStrTest.nowDateTimStrByHutool                                    ss       5   97.880 ± 430.609   us/op
// NowDateStrTest.nowDateTimStrByZUtil                                     ss       5   36.200 ±  17.173   us/op
