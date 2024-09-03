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
public class SubTest {

  public static void main(String[] args) {
    // 结果是否相等
    SubTest test = new SubTest();
    System.out.println(test.subByHutool().equals(test.subByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{SubTest.class.getName()});
  }

  private static final String str = "abcdefgh";

  @Benchmark
  public String subByHutool() {
    return cn.hutool.core.util.StrUtil.sub(str, 2, -3);
  }

  @Benchmark
  public String subByZUtil() {
    return StrUtil.substring(str, 2, -3);
  }
}

// Benchmark                                  Mode     Cnt    Score    Error   Units
// SubTest.subByHutool                       thrpt       5  110.426 ±  8.503  ops/us
// SubTest.subByZUtil                        thrpt       5  113.246 ±  2.390  ops/us
// SubTest.subByHutool                        avgt       5    0.009 ±  0.001   us/op
// SubTest.subByZUtil                         avgt       5    0.009 ±  0.001   us/op
// SubTest.subByHutool                      sample  125460    0.040 ±  0.001   us/op
// SubTest.subByHutool:subByHutool·p0.00    sample              ≈ 0            us/op
// SubTest.subByHutool:subByHutool·p0.50    sample              ≈ 0            us/op
// SubTest.subByHutool:subByHutool·p0.90    sample            0.100            us/op
// SubTest.subByHutool:subByHutool·p0.95    sample            0.100            us/op
// SubTest.subByHutool:subByHutool·p0.99    sample            0.100            us/op
// SubTest.subByHutool:subByHutool·p0.999   sample            0.200            us/op
// SubTest.subByHutool:subByHutool·p0.9999  sample            5.896            us/op
// SubTest.subByHutool:subByHutool·p1.00    sample           28.896            us/op
// SubTest.subByZUtil                       sample  124705    0.041 ±  0.002   us/op
// SubTest.subByZUtil:subByZUtil·p0.00      sample              ≈ 0            us/op
// SubTest.subByZUtil:subByZUtil·p0.50      sample              ≈ 0            us/op
// SubTest.subByZUtil:subByZUtil·p0.90      sample            0.100            us/op
// SubTest.subByZUtil:subByZUtil·p0.95      sample            0.100            us/op
// SubTest.subByZUtil:subByZUtil·p0.99      sample            0.100            us/op
// SubTest.subByZUtil:subByZUtil·p0.999     sample            0.200            us/op
// SubTest.subByZUtil:subByZUtil·p0.9999    sample            6.696            us/op
// SubTest.subByZUtil:subByZUtil·p1.00      sample           33.472            us/op
// SubTest.subByHutool                          ss       5   15.020 ±  4.204   us/op
// SubTest.subByZUtil                           ss       5    3.260 ±  3.229   us/op
