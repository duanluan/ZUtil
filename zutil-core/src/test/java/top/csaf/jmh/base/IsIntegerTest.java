package top.csaf.jmh.base;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 是否为 Integer 的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class IsIntegerTest {

  public static void main(String[] args) {
    IsIntegerTest test = new IsIntegerTest();
    System.out.println(test.test1() == test.test2());
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{IsIntegerTest.class.getName()});
  }

  private static final String INTEGER_STR = "1";
  private static final Pattern IS_INTEGER_PATTERN = Pattern.compile("^[-+]?[\\d]*$");

  @Benchmark
  public boolean test1() {
    return IS_INTEGER_PATTERN.matcher(INTEGER_STR).find();
  }

  @Benchmark
  public boolean test2() {
    try {
      NumberUtils.createInteger(INTEGER_STR);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}

// Benchmark                      Mode     Cnt    Score    Error   Units
// IsIntegerTest.test1           thrpt       5   24.023 ±  0.428  ops/us
// IsIntegerTest.test2           thrpt       5  220.782 ±  2.927  ops/us
// IsIntegerTest.test1            avgt       5    0.041 ±  0.001   us/op
// IsIntegerTest.test2            avgt       5    0.005 ±  0.001   us/op
// IsIntegerTest.test1          sample  110890    0.067 ±  0.003   us/op
// IsIntegerTest.test1:p0.00    sample              ≈ 0            us/op
// IsIntegerTest.test1:p0.50    sample            0.100            us/op
// IsIntegerTest.test1:p0.90    sample            0.100            us/op
// IsIntegerTest.test1:p0.95    sample            0.100            us/op
// IsIntegerTest.test1:p0.99    sample            0.100            us/op
// IsIntegerTest.test1:p0.999   sample            0.300            us/op
// IsIntegerTest.test1:p0.9999  sample           11.932            us/op
// IsIntegerTest.test1:p1.00    sample           60.864            us/op
// IsIntegerTest.test2          sample  108958    0.036 ±  0.002   us/op
// IsIntegerTest.test2:p0.00    sample              ≈ 0            us/op
// IsIntegerTest.test2:p0.50    sample              ≈ 0            us/op
// IsIntegerTest.test2:p0.90    sample            0.100            us/op
// IsIntegerTest.test2:p0.95    sample            0.100            us/op
// IsIntegerTest.test2:p0.99    sample            0.100            us/op
// IsIntegerTest.test2:p0.999   sample            0.100            us/op
// IsIntegerTest.test2:p0.9999  sample            5.442            us/op
// IsIntegerTest.test2:p1.00    sample           40.640            us/op
// IsIntegerTest.test1              ss       5   15.440 ± 14.026   us/op
// IsIntegerTest.test2              ss       5    7.680 ±  4.360   us/op
