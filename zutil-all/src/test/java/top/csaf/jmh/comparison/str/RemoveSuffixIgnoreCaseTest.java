package top.csaf.jmh.comparison.str;

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
public class RemoveSuffixIgnoreCaseTest {

  public static void main(String[] args) {
    // 结果是否相等
    RemoveSuffixIgnoreCaseTest test = new RemoveSuffixIgnoreCaseTest();
    System.out.println(test.removeSuffixIgnoreCaseByHutool().equals(test.removeSuffixIgnoreCaseByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{RemoveSuffixIgnoreCaseTest.class.getName()});
  }

  private static final String str = "jpg.gpj";

  @Benchmark
  public String removeSuffixIgnoreCaseByHutool() {
    return cn.hutool.core.util.StrUtil.removeSuffixIgnoreCase(str, "GPJ");
  }

  @Benchmark
  public String removeSuffixIgnoreCaseByZUtil() {
    return StrUtil.removeEndIgnoreCase(str, "GPJ");
  }
}

// Benchmark                                                                                           Mode     Cnt    Score    Error   Units
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool                                          thrpt       5   24.044 ±  0.522  ops/us
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil                                           thrpt       5   24.103 ±  3.715  ops/us
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool                                           avgt       5    0.042 ±  0.001   us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil                                            avgt       5    0.040 ±  0.002   us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool                                         sample  114670    0.077 ±  0.005   us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool:removeSuffixIgnoreCaseByHutool·p0.00    sample              ≈ 0            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool:removeSuffixIgnoreCaseByHutool·p0.50    sample            0.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool:removeSuffixIgnoreCaseByHutool·p0.90    sample            0.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool:removeSuffixIgnoreCaseByHutool·p0.95    sample            0.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool:removeSuffixIgnoreCaseByHutool·p0.99    sample            0.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool:removeSuffixIgnoreCaseByHutool·p0.999   sample            0.900            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool:removeSuffixIgnoreCaseByHutool·p0.9999  sample           13.653            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool:removeSuffixIgnoreCaseByHutool·p1.00    sample          161.280            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil                                          sample  113288    0.085 ±  0.025   us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil:removeSuffixIgnoreCaseByZUtil·p0.00      sample              ≈ 0            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil:removeSuffixIgnoreCaseByZUtil·p0.50      sample            0.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil:removeSuffixIgnoreCaseByZUtil·p0.90      sample            0.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil:removeSuffixIgnoreCaseByZUtil·p0.95      sample            0.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil:removeSuffixIgnoreCaseByZUtil·p0.99      sample            0.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil:removeSuffixIgnoreCaseByZUtil·p0.999     sample            1.100            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil:removeSuffixIgnoreCaseByZUtil·p0.9999    sample           13.337            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil:removeSuffixIgnoreCaseByZUtil·p1.00      sample          856.064            us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByHutool                                             ss       5   20.480 ± 21.392   us/op
// RemoveSuffixIgnoreCaseTest.removeSuffixIgnoreCaseByZUtil                                              ss       5    7.060 ±  5.381   us/op
