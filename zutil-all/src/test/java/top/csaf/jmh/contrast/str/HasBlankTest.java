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
public class HasBlankTest {

  public static void main(String[] args) {
    // 结果是否相等
    HasBlankTest test = new HasBlankTest();
    System.out.println(test.hasBlankByHutool() == test.hasBlankByZUtil());
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{HasBlankTest.class.getName()});
  }

  private static final String[] strs = new String[]{"1", "2", "3", " "};

  @Benchmark
  public boolean hasBlankByHutool() {
    return cn.hutool.core.util.StrUtil.hasBlank(strs);
  }

  @Benchmark
  public boolean hasBlankByZUtil() {
    return StrUtil.isAnyBlank(strs);
  }
}

// Benchmark                                                 Mode     Cnt    Score    Error   Units
// HasBlankTest.hasBlankByHutool                            thrpt       5   77.481 ±  4.468  ops/us
// HasBlankTest.hasBlankByZUtil                             thrpt       5   86.630 ± 45.603  ops/us
// HasBlankTest.hasBlankByHutool                             avgt       5    0.013 ±  0.001   us/op
// HasBlankTest.hasBlankByZUtil                              avgt       5    0.010 ±  0.001   us/op
// HasBlankTest.hasBlankByHutool                           sample  149813    0.043 ±  0.004   us/op
// HasBlankTest.hasBlankByHutool:hasBlankByHutool·p0.00    sample              ≈ 0            us/op
// HasBlankTest.hasBlankByHutool:hasBlankByHutool·p0.50    sample              ≈ 0            us/op
// HasBlankTest.hasBlankByHutool:hasBlankByHutool·p0.90    sample            0.100            us/op
// HasBlankTest.hasBlankByHutool:hasBlankByHutool·p0.95    sample            0.100            us/op
// HasBlankTest.hasBlankByHutool:hasBlankByHutool·p0.99    sample            0.100            us/op
// HasBlankTest.hasBlankByHutool:hasBlankByHutool·p0.999   sample            0.100            us/op
// HasBlankTest.hasBlankByHutool:hasBlankByHutool·p0.9999  sample            2.500            us/op
// HasBlankTest.hasBlankByHutool:hasBlankByHutool·p1.00    sample          193.536            us/op
// HasBlankTest.hasBlankByZUtil                            sample  184672    0.043 ±  0.001   us/op
// HasBlankTest.hasBlankByZUtil:hasBlankByZUtil·p0.00      sample              ≈ 0            us/op
// HasBlankTest.hasBlankByZUtil:hasBlankByZUtil·p0.50      sample              ≈ 0            us/op
// HasBlankTest.hasBlankByZUtil:hasBlankByZUtil·p0.90      sample            0.100            us/op
// HasBlankTest.hasBlankByZUtil:hasBlankByZUtil·p0.95      sample            0.100            us/op
// HasBlankTest.hasBlankByZUtil:hasBlankByZUtil·p0.99      sample            0.100            us/op
// HasBlankTest.hasBlankByZUtil:hasBlankByZUtil·p0.999     sample            0.100            us/op
// HasBlankTest.hasBlankByZUtil:hasBlankByZUtil·p0.9999    sample            4.720            us/op
// HasBlankTest.hasBlankByZUtil:hasBlankByZUtil·p1.00      sample           57.088            us/op
// HasBlankTest.hasBlankByHutool                               ss       5    8.960 ± 10.252   us/op
// HasBlankTest.hasBlankByZUtil                                ss       5    5.880 ±  9.142   us/op
