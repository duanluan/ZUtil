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
public class RemoveSuffixTest {

  public static void main(String[] args) {
    // 结果是否相等
    RemoveSuffixTest test = new RemoveSuffixTest();
    System.out.println(test.removeSuffixByHutool().equals(test.removeSuffixByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{RemoveSuffixTest.class.getName()});
  }

  private static final String str = "jpg.gpj";

  @Benchmark
  public String removeSuffixByHutool() {
    return StrUtil.removeSuffix(str, "gpj");
  }

  @Benchmark
  public String removeSuffixByZUtil() {
    return StringUtils.removeEnd(str, "gpj");
  }
}

// Benchmark                                                             Mode     Cnt    Score    Error   Units
// RemoveSuffixTest.removeSuffixByHutool                                thrpt       5  101.685 ±  3.005  ops/us
// RemoveSuffixTest.removeSuffixByZUtil                                 thrpt       5  102.357 ±  4.750  ops/us
// RemoveSuffixTest.removeSuffixByHutool                                 avgt       5    0.010 ±  0.001   us/op
// RemoveSuffixTest.removeSuffixByZUtil                                  avgt       5    0.010 ±  0.002   us/op
// RemoveSuffixTest.removeSuffixByHutool                               sample  107235    0.051 ±  0.004   us/op
// RemoveSuffixTest.removeSuffixByHutool:removeSuffixByHutool·p0.00    sample              ≈ 0            us/op
// RemoveSuffixTest.removeSuffixByHutool:removeSuffixByHutool·p0.50    sample              ≈ 0            us/op
// RemoveSuffixTest.removeSuffixByHutool:removeSuffixByHutool·p0.90    sample            0.100            us/op
// RemoveSuffixTest.removeSuffixByHutool:removeSuffixByHutool·p0.95    sample            0.100            us/op
// RemoveSuffixTest.removeSuffixByHutool:removeSuffixByHutool·p0.99    sample            0.100            us/op
// RemoveSuffixTest.removeSuffixByHutool:removeSuffixByHutool·p0.999   sample            0.500            us/op
// RemoveSuffixTest.removeSuffixByHutool:removeSuffixByHutool·p0.9999  sample           13.145            us/op
// RemoveSuffixTest.removeSuffixByHutool:removeSuffixByHutool·p1.00    sample          100.992            us/op
// RemoveSuffixTest.removeSuffixByZUtil                                sample  113188    0.046 ±  0.003   us/op
// RemoveSuffixTest.removeSuffixByZUtil:removeSuffixByZUtil·p0.00      sample              ≈ 0            us/op
// RemoveSuffixTest.removeSuffixByZUtil:removeSuffixByZUtil·p0.50      sample              ≈ 0            us/op
// RemoveSuffixTest.removeSuffixByZUtil:removeSuffixByZUtil·p0.90      sample            0.100            us/op
// RemoveSuffixTest.removeSuffixByZUtil:removeSuffixByZUtil·p0.95      sample            0.100            us/op
// RemoveSuffixTest.removeSuffixByZUtil:removeSuffixByZUtil·p0.99      sample            0.100            us/op
// RemoveSuffixTest.removeSuffixByZUtil:removeSuffixByZUtil·p0.999     sample            0.300            us/op
// RemoveSuffixTest.removeSuffixByZUtil:removeSuffixByZUtil·p0.9999    sample            7.903            us/op
// RemoveSuffixTest.removeSuffixByZUtil:removeSuffixByZUtil·p1.00      sample           74.496            us/op
// RemoveSuffixTest.removeSuffixByHutool                                   ss       5   17.200 ± 15.553   us/op
// RemoveSuffixTest.removeSuffixByZUtil                                    ss       5    6.640 ±  6.043   us/op
