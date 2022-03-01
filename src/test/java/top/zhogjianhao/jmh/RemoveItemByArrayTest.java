package top.zhogjianhao.jmh;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * fori 手动往前移元素和 System.arraycopy 的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class RemoveItemByArrayTest {

  public static void main(String[] args) {
    // 结果是否相等
    RemoveItemByArrayTest test = new RemoveItemByArrayTest();
    System.out.println(Arrays.equals(test.move(), test.arraycopy()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{RemoveItemByArrayTest.class.getName()});
  }

  private static final Integer[] ints = new Integer[1000];

  static {
    for (int i = 0; i < ints.length; i++) {
      ints[i] = i + 1;
    }
  }

  @Benchmark
  public Integer[] move() {
    Integer[] ints1 = ints;
    for (int i = 3; i < ints1.length - 1; i++) {
      ints1[i] = ints1[i + 1];
    }
    return ints1;
  }

  @Benchmark
  public Integer[] arraycopy() {
    Integer[] ints1 = ints;
    System.arraycopy(ints1, 3 + 1, ints1, 3, ints1.length - 1 - 3);
    return ints1;
  }
}

// Benchmark                                            Mode     Cnt    Score   Error   Units
// RemoveItemByArrayTest.arraycopy                     thrpt       5    6.186 ± 0.097  ops/us
// RemoveItemByArrayTest.move                          thrpt       5    1.999 ± 0.058  ops/us
// RemoveItemByArrayTest.arraycopy                      avgt       5    0.162 ± 0.002   us/op
// RemoveItemByArrayTest.move                           avgt       5    0.497 ± 0.011   us/op
// RemoveItemByArrayTest.arraycopy                    sample  118324    0.201 ± 0.002   us/op
// RemoveItemByArrayTest.arraycopy:arraycopy·p0.00    sample            0.100           us/op
// RemoveItemByArrayTest.arraycopy:arraycopy·p0.50    sample            0.200           us/op
// RemoveItemByArrayTest.arraycopy:arraycopy·p0.90    sample            0.200           us/op
// RemoveItemByArrayTest.arraycopy:arraycopy·p0.95    sample            0.200           us/op
// RemoveItemByArrayTest.arraycopy:arraycopy·p0.99    sample            0.300           us/op
// RemoveItemByArrayTest.arraycopy:arraycopy·p0.999   sample            0.700           us/op
// RemoveItemByArrayTest.arraycopy:arraycopy·p0.9999  sample            6.600           us/op
// RemoveItemByArrayTest.arraycopy:arraycopy·p1.00    sample           40.448           us/op
// RemoveItemByArrayTest.move                         sample  157123    0.538 ± 0.004   us/op
// RemoveItemByArrayTest.move:move·p0.00              sample            0.500           us/op
// RemoveItemByArrayTest.move:move·p0.50              sample            0.500           us/op
// RemoveItemByArrayTest.move:move·p0.90              sample            0.600           us/op
// RemoveItemByArrayTest.move:move·p0.95              sample            0.600           us/op
// RemoveItemByArrayTest.move:move·p0.99              sample            1.000           us/op
// RemoveItemByArrayTest.move:move·p0.999             sample            2.088           us/op
// RemoveItemByArrayTest.move:move·p0.9999            sample           14.230           us/op
// RemoveItemByArrayTest.move:move·p1.00              sample          115.840           us/op
// RemoveItemByArrayTest.arraycopy                        ss       5    2.780 ± 2.268   us/op
// RemoveItemByArrayTest.move                             ss       5   22.400 ± 5.480   us/op
