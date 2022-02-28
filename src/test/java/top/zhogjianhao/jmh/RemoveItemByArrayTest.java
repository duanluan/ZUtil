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
