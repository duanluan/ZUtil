package top.csaf.jmh.contrast.str;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.lang.StrUtil;

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

  private static final String format = "{}爱{}。";
  private static final String arg1 = "我真的";
  private static final String arg2 = "你啊";


  @Benchmark
  public String formatByHutool() {
    return cn.hutool.core.util.StrUtil.format(format, arg1, arg2);
  }

  @Benchmark
  public String formatByZUtil() {
    return StrUtil.format(format, arg1, arg2);
  }
}

// Benchmark                                           Mode     Cnt     Score    Error   Units
// FormatTest.formatByHutool                          thrpt       5    17.941 ±  0.453  ops/us
// FormatTest.formatByZUtil                           thrpt       5    12.736 ±  0.393  ops/us
// FormatTest.formatByHutool                           avgt       5     0.056 ±  0.001   us/op
// FormatTest.formatByZUtil                            avgt       5     0.077 ±  0.002   us/op
// FormatTest.formatByHutool                         sample  163051     0.085 ±  0.002   us/op
// FormatTest.formatByHutool:formatByHutool·p0.00    sample               ≈ 0            us/op
// FormatTest.formatByHutool:formatByHutool·p0.50    sample             0.100            us/op
// FormatTest.formatByHutool:formatByHutool·p0.90    sample             0.100            us/op
// FormatTest.formatByHutool:formatByHutool·p0.95    sample             0.100            us/op
// FormatTest.formatByHutool:formatByHutool·p0.99    sample             0.200            us/op
// FormatTest.formatByHutool:formatByHutool·p0.999   sample             0.300            us/op
// FormatTest.formatByHutool:formatByHutool·p0.9999  sample             8.535            us/op
// FormatTest.formatByHutool:formatByHutool·p1.00    sample            72.448            us/op
// FormatTest.formatByZUtil                          sample  124769     0.122 ±  0.038   us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.00      sample               ≈ 0            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.50      sample             0.100            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.90      sample             0.100            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.95      sample             0.200            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.99      sample             0.200            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.999     sample             0.400            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.9999    sample            19.300            us/op
// FormatTest.formatByZUtil:formatByZUtil·p1.00      sample          1404.928            us/op
// FormatTest.formatByHutool                             ss       5    23.400 ± 25.256   us/op
// FormatTest.formatByZUtil                              ss       5    14.560 ±  9.778   us/op
