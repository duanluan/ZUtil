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
public class RemovePrefixTest {

  public static void main(String[] args) {
    // 结果是否相等
    RemovePrefixTest test = new RemovePrefixTest();
    System.out.println(test.removePrefixByHutool().equals(test.removePrefixByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{RemovePrefixTest.class.getName()});
  }

  private static final String str = "jpg.gpj";

  @Benchmark
  public String removePrefixByHutool() {
    return cn.hutool.core.util.StrUtil.removePrefix(str, "jpg");
  }

  @Benchmark
  public String removePrefixByZUtil() {
    return StrUtil.removeStart(str, "jpg");
  }
}

// Benchmark                                                             Mode     Cnt    Score    Error   Units
// RemovePrefixTest.removePrefixByHutool                                thrpt       5  101.642 ±  3.953  ops/us
// RemovePrefixTest.removePrefixByZUtil                                 thrpt       5  100.947 ±  1.817  ops/us
// RemovePrefixTest.removePrefixByHutool                                 avgt       5    0.010 ±  0.001   us/op
// RemovePrefixTest.removePrefixByZUtil                                  avgt       5    0.010 ±  0.001   us/op
// RemovePrefixTest.removePrefixByHutool                               sample  107247    0.052 ±  0.005   us/op
// RemovePrefixTest.removePrefixByHutool:removePrefixByHutool·p0.00    sample              ≈ 0            us/op
// RemovePrefixTest.removePrefixByHutool:removePrefixByHutool·p0.50    sample              ≈ 0            us/op
// RemovePrefixTest.removePrefixByHutool:removePrefixByHutool·p0.90    sample            0.100            us/op
// RemovePrefixTest.removePrefixByHutool:removePrefixByHutool·p0.95    sample            0.100            us/op
// RemovePrefixTest.removePrefixByHutool:removePrefixByHutool·p0.99    sample            0.100            us/op
// RemovePrefixTest.removePrefixByHutool:removePrefixByHutool·p0.999   sample            0.600            us/op
// RemovePrefixTest.removePrefixByHutool:removePrefixByHutool·p0.9999  sample           12.328            us/op
// RemovePrefixTest.removePrefixByHutool:removePrefixByHutool·p1.00    sample           73.984            us/op
// RemovePrefixTest.removePrefixByZUtil                                sample  112618    0.046 ±  0.003   us/op
// RemovePrefixTest.removePrefixByZUtil:removePrefixByZUtil·p0.00      sample              ≈ 0            us/op
// RemovePrefixTest.removePrefixByZUtil:removePrefixByZUtil·p0.50      sample              ≈ 0            us/op
// RemovePrefixTest.removePrefixByZUtil:removePrefixByZUtil·p0.90      sample            0.100            us/op
// RemovePrefixTest.removePrefixByZUtil:removePrefixByZUtil·p0.95      sample            0.100            us/op
// RemovePrefixTest.removePrefixByZUtil:removePrefixByZUtil·p0.99      sample            0.100            us/op
// RemovePrefixTest.removePrefixByZUtil:removePrefixByZUtil·p0.999     sample            0.300            us/op
// RemovePrefixTest.removePrefixByZUtil:removePrefixByZUtil·p0.9999    sample            7.468            us/op
// RemovePrefixTest.removePrefixByZUtil:removePrefixByZUtil·p1.00      sample           64.896            us/op
// RemovePrefixTest.removePrefixByHutool                                   ss       5   16.400 ±  8.159   us/op
// RemovePrefixTest.removePrefixByZUtil                                    ss       5    3.200 ±  5.795   us/op
