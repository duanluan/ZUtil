package top.zhogjianhao.jmh.contrast.date;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.date.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class FormatTest {

  public static void main(String[] args) {
    // 结果是否相等
    FormatTest test = new FormatTest();
    System.out.println(test.formatByHutool().equals(test.formatByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{FormatTest.class.getName()});
  }

  private static final Date nowDate= new Date();

  @Benchmark
  public String formatByHutool() {
    return DateUtil.formatDateTime(nowDate);
  }

  @Benchmark
  public String formatByZUtil() {
    return DateUtils.format(nowDate);
  }
}

// Benchmark                                           Mode     Cnt    Score     Error   Units
// FormatTest.formatByHutool                          thrpt       5    3.811 ±   0.085  ops/us
// FormatTest.formatByZUtil                           thrpt       5    3.711 ±   0.214  ops/us
// FormatTest.formatByHutool                           avgt       5    0.265 ±   0.006   us/op
// FormatTest.formatByZUtil                            avgt       5    0.269 ±   0.012   us/op
// FormatTest.formatByHutool                         sample  144092    0.305 ±   0.009   us/op
// FormatTest.formatByHutool:formatByHutool·p0.00    sample            0.200             us/op
// FormatTest.formatByHutool:formatByHutool·p0.50    sample            0.300             us/op
// FormatTest.formatByHutool:formatByHutool·p0.90    sample            0.300             us/op
// FormatTest.formatByHutool:formatByHutool·p0.95    sample            0.300             us/op
// FormatTest.formatByHutool:formatByHutool·p0.99    sample            0.500             us/op
// FormatTest.formatByHutool:formatByHutool·p0.999   sample            3.291             us/op
// FormatTest.formatByHutool:formatByHutool·p0.9999  sample           17.369             us/op
// FormatTest.formatByHutool:formatByHutool·p1.00    sample          177.152             us/op
// FormatTest.formatByZUtil                          sample  139900    0.321 ±   0.019   us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.00      sample            0.200             us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.50      sample            0.300             us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.90      sample            0.300             us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.95      sample            0.400             us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.99      sample            0.700             us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.999     sample            4.696             us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.9999    sample           28.803             us/op
// FormatTest.formatByZUtil:formatByZUtil·p1.00      sample          756.736             us/op
// FormatTest.formatByHutool                             ss       5   91.380 ± 412.393   us/op
// FormatTest.formatByZUtil                              ss       5   38.180 ±  25.211   us/op
