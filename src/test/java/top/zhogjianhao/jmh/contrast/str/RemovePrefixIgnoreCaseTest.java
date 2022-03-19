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
public class RemovePrefixIgnoreCaseTest {

  public static void main(String[] args) {
    // 结果是否相等
    RemovePrefixIgnoreCaseTest test = new RemovePrefixIgnoreCaseTest();
    System.out.println(test.removePrefixIgnoreCaseByHutool().equals(test.removePrefixIgnoreCaseByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{RemovePrefixIgnoreCaseTest.class.getName()});
  }

  private static final String str = "jpg.gpj";

  @Benchmark
  public String removePrefixIgnoreCaseByHutool() {
    return StrUtil.removePrefixIgnoreCase(str, "JPG");
  }

  @Benchmark
  public String removePrefixIgnoreCaseByZUtil() {
    return StringUtils.removeStartIgnoreCase(str, "JPG");
  }
}

// Benchmark                                                                                           Mode     Cnt    Score    Error   Units
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool                                          thrpt       5   24.646 ±  0.371  ops/us
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil                                           thrpt       5   24.510 ±  0.906  ops/us
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool                                           avgt       5    0.040 ±  0.001   us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil                                            avgt       5    0.040 ±  0.001   us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool                                         sample  118459    0.074 ±  0.004   us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool:removePrefixIgnoreCaseByHutool·p0.00    sample              ≈ 0            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool:removePrefixIgnoreCaseByHutool·p0.50    sample            0.100            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool:removePrefixIgnoreCaseByHutool·p0.90    sample            0.100            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool:removePrefixIgnoreCaseByHutool·p0.95    sample            0.100            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool:removePrefixIgnoreCaseByHutool·p0.99    sample            0.100            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool:removePrefixIgnoreCaseByHutool·p0.999   sample            0.500            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool:removePrefixIgnoreCaseByHutool·p0.9999  sample            9.439            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool:removePrefixIgnoreCaseByHutool·p1.00    sample          105.216            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil                                          sample  115745    0.081 ±  0.016   us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil:removePrefixIgnoreCaseByZUtil·p0.00      sample              ≈ 0            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil:removePrefixIgnoreCaseByZUtil·p0.50      sample            0.100            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil:removePrefixIgnoreCaseByZUtil·p0.90      sample            0.100            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil:removePrefixIgnoreCaseByZUtil·p0.95      sample            0.100            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil:removePrefixIgnoreCaseByZUtil·p0.99      sample            0.100            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil:removePrefixIgnoreCaseByZUtil·p0.999     sample            0.700            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil:removePrefixIgnoreCaseByZUtil·p0.9999    sample           14.573            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil:removePrefixIgnoreCaseByZUtil·p1.00      sample          553.984            us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByHutool                                             ss       5   17.980 ± 15.758   us/op
// RemovePrefixIgnoreCaseTest.removePrefixIgnoreCaseByZUtil                                              ss       5    5.540 ±  4.561   us/op
