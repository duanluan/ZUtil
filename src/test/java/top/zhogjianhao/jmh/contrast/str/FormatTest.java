package top.zhogjianhao.jmh.contrast.str;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.StringUtils;

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

  private static final String format = "{}爱{}";
  private static final String arg1 = "我真的";
  private static final String arg2 = "你啊";


  @Benchmark
  public String formatByHutool() {
    return StrUtil.format(format, arg1, arg2);
  }

  @Benchmark
  public String formatByZUtil() {
    return StringUtils.format(format, arg1, arg2);
  }
}

// Benchmark                                           Mode     Cnt    Score    Error   Units
// FormatTest.formatByHutool                          thrpt       5   18.008 ±  0.223  ops/us
// FormatTest.formatByZUtil                           thrpt       5   19.756 ±  0.481  ops/us
// FormatTest.formatByHutool                           avgt       5    0.055 ±  0.002   us/op
// FormatTest.formatByZUtil                            avgt       5    0.052 ±  0.002   us/op
// FormatTest.formatByHutool                         sample  168209    0.095 ±  0.018   us/op
// FormatTest.formatByHutool:formatByHutool·p0.00    sample              ≈ 0            us/op
// FormatTest.formatByHutool:formatByHutool·p0.50    sample            0.100            us/op
// FormatTest.formatByHutool:formatByHutool·p0.90    sample            0.100            us/op
// FormatTest.formatByHutool:formatByHutool·p0.95    sample            0.100            us/op
// FormatTest.formatByHutool:formatByHutool·p0.99    sample            0.200            us/op
// FormatTest.formatByHutool:formatByHutool·p0.999   sample            0.700            us/op
// FormatTest.formatByHutool:formatByHutool·p0.9999  sample           11.829            us/op
// FormatTest.formatByHutool:formatByHutool·p1.00    sample          812.032            us/op
// FormatTest.formatByZUtil                          sample  177572    0.087 ±  0.006   us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.00      sample              ≈ 0            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.50      sample            0.100            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.90      sample            0.100            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.95      sample            0.100            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.99      sample            0.200            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.999     sample            0.500            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.9999    sample           11.090            us/op
// FormatTest.formatByZUtil:formatByZUtil·p1.00      sample          247.296            us/op
// FormatTest.formatByHutool                             ss       5   27.680 ± 42.249   us/op
// FormatTest.formatByZUtil                              ss       5   12.720 ± 21.347   us/op
