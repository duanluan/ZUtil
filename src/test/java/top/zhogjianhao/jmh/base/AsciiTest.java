package top.zhogjianhao.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * ASCII 字符，使用常量和直接使用的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class AsciiTest {

  public static void main(String[] args) {
    // 结果是否相等
    AsciiTest test = new AsciiTest();
    System.out.println(test.test1().equals(test.test2()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{AsciiTest.class.getName()});
  }

  private static final String AND = "&";

  @Benchmark
  public String test1() {
    return AND;
  }

  @Benchmark
  public String test2() {
    return "&";
  }
}

// Benchmark                   Mode     Cnt    Score    Error   Units
// Test.test1                 thrpt       5  141.861 ± 10.083  ops/us
// Test.test2                 thrpt       5  145.842 ±  1.790  ops/us
// Test.test1                  avgt       5    0.007 ±  0.001   us/op
// Test.test2                  avgt       5    0.007 ±  0.001   us/op
// Test.test1                sample  115215    0.040 ±  0.002   us/op
// Test.test1:test1·p0.00    sample              ≈ 0            us/op
// Test.test1:test1·p0.50    sample              ≈ 0            us/op
// Test.test1:test1·p0.90    sample            0.100            us/op
// Test.test1:test1·p0.95    sample            0.100            us/op
// Test.test1:test1·p0.99    sample            0.100            us/op
// Test.test1:test1·p0.999   sample            0.100            us/op
// Test.test1:test1·p0.9999  sample            3.287            us/op
// Test.test1:test1·p1.00    sample           74.496            us/op
// Test.test2                sample  114435    0.040 ±  0.001   us/op
// Test.test2:test2·p0.00    sample              ≈ 0            us/op
// Test.test2:test2·p0.50    sample              ≈ 0            us/op
// Test.test2:test2·p0.90    sample            0.100            us/op
// Test.test2:test2·p0.95    sample            0.100            us/op
// Test.test2:test2·p0.99    sample            0.100            us/op
// Test.test2:test2·p0.999   sample            0.100            us/op
// Test.test2:test2·p0.9999  sample            2.956            us/op
// Test.test2:test2·p1.00    sample            7.096            us/op
// Test.test1                    ss       5    1.560 ±  2.102   us/op
// Test.test2                    ss       5    1.480 ±  1.425   us/op
