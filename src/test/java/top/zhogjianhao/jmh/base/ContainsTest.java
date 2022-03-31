package top.zhogjianhao.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * String.contains 和 pattern.matcher 来判断是否包含的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ContainsTest {

  public static void main(String[] args) {
    // 结果是否相等
    ContainsTest test = new ContainsTest();
    System.out.println(test.test1().equals(test.test2()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ContainsTest.class.getName()});
  }

  private static final String patternStr = "Yyuu";
  private static final Pattern pattern = Pattern.compile("Yyuu");

  @Benchmark
  public Boolean test1() {
    return patternStr.contains("y") && patternStr.contains("Y");
  }

  @Benchmark
  public Boolean test2() {
    return pattern.matcher("[yY]").find();
  }
}

// Benchmark                   Mode     Cnt     Score    Error   Units
// Test.test1                 thrpt       5   135.742 ±  2.772  ops/us
// Test.test2                 thrpt       5    32.660 ±  0.843  ops/us
// Test.test1                  avgt       5     0.007 ±  0.001   us/op
// Test.test2                  avgt       5     0.031 ±  0.002   us/op
// Test.test1                sample  128484     0.061 ±  0.047   us/op
// Test.test1:test1·p0.00    sample               ≈ 0            us/op
// Test.test1:test1·p0.50    sample               ≈ 0            us/op
// Test.test1:test1·p0.90    sample             0.100            us/op
// Test.test1:test1·p0.95    sample             0.100            us/op
// Test.test1:test1·p0.99    sample             0.100            us/op
// Test.test1:test1·p0.999   sample             0.200            us/op
// Test.test1:test1·p0.9999  sample            22.976            us/op
// Test.test1:test1·p1.00    sample          1812.480            us/op
// Test.test2                sample  151616     0.053 ±  0.002   us/op
// Test.test2:test2·p0.00    sample               ≈ 0            us/op
// Test.test2:test2·p0.50    sample             0.100            us/op
// Test.test2:test2·p0.90    sample             0.100            us/op
// Test.test2:test2·p0.95    sample             0.100            us/op
// Test.test2:test2·p0.99    sample             0.100            us/op
// Test.test2:test2·p0.999   sample             0.200            us/op
// Test.test2:test2·p0.9999  sample             5.783            us/op
// Test.test2:test2·p1.00    sample            86.528            us/op
// Test.test1                    ss       5    11.820 ± 10.330   us/op
// Test.test2                    ss       5     7.820 ± 10.054   us/op
