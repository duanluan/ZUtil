package top.csaf.jmh.base.str;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * 逗号分隔字符串获取第一个元素的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class CommaSeparatedStrGetFirstItemTest {

  public static void main(String[] args) {
    // 结果是否相等
    CommaSeparatedStrGetFirstItemTest test = new CommaSeparatedStrGetFirstItemTest();
    System.out.println(test.test1().equals(test.test2()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{CommaSeparatedStrGetFirstItemTest.class.getName()});
  }

  private static final String STR = "huo,hé,huó,huò,hè,hú";

  @Benchmark
  public String test1() {
    return STR.split(",")[0];
  }

  @Benchmark
  public String test2() {
    int commaIndex = STR.indexOf(",");
    if (commaIndex != -1) {
      return STR.substring(0, commaIndex);
    }
    return null;
  }
}

// Benchmark                                                Mode     Cnt    Score    Error   Units
// CommaSeparatedStrGetFirstItemTest.test1                 thrpt       5    8.688 ±  0.234  ops/us
// CommaSeparatedStrGetFirstItemTest.test2                 thrpt       5   90.797 ±  1.024  ops/us
// CommaSeparatedStrGetFirstItemTest.test1                  avgt       5    0.117 ±  0.003   us/op
// CommaSeparatedStrGetFirstItemTest.test2                  avgt       5    0.011 ±  0.001   us/op
// CommaSeparatedStrGetFirstItemTest.test1                sample  162928    0.151 ±  0.004   us/op
// CommaSeparatedStrGetFirstItemTest.test1:test1·p0.00    sample            0.099            us/op
// CommaSeparatedStrGetFirstItemTest.test1:test1·p0.50    sample            0.101            us/op
// CommaSeparatedStrGetFirstItemTest.test1:test1·p0.90    sample            0.201            us/op
// CommaSeparatedStrGetFirstItemTest.test1:test1·p0.95    sample            0.201            us/op
// CommaSeparatedStrGetFirstItemTest.test1:test1·p0.99    sample            0.301            us/op
// CommaSeparatedStrGetFirstItemTest.test1:test1·p0.999   sample            0.600            us/op
// CommaSeparatedStrGetFirstItemTest.test1:test1·p0.9999  sample           15.654            us/op
// CommaSeparatedStrGetFirstItemTest.test1:test1·p1.00    sample          115.200            us/op
// CommaSeparatedStrGetFirstItemTest.test2                sample  177424    0.047 ±  0.006   us/op
// CommaSeparatedStrGetFirstItemTest.test2:test2·p0.00    sample              ≈ 0            us/op
// CommaSeparatedStrGetFirstItemTest.test2:test2·p0.50    sample              ≈ 0            us/op
// CommaSeparatedStrGetFirstItemTest.test2:test2·p0.90    sample            0.100            us/op
// CommaSeparatedStrGetFirstItemTest.test2:test2·p0.95    sample            0.101            us/op
// CommaSeparatedStrGetFirstItemTest.test2:test2·p0.99    sample            0.101            us/op
// CommaSeparatedStrGetFirstItemTest.test2:test2·p0.999   sample            0.200            us/op
// CommaSeparatedStrGetFirstItemTest.test2:test2·p0.9999  sample            5.430            us/op
// CommaSeparatedStrGetFirstItemTest.test2:test2·p1.00    sample          174.592            us/op
// CommaSeparatedStrGetFirstItemTest.test1                    ss       5   14.160 ± 12.536   us/op
// CommaSeparatedStrGetFirstItemTest.test2                    ss       5    4.060 ±  4.024   us/op
