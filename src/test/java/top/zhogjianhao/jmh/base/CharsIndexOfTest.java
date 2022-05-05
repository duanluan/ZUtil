package top.zhogjianhao.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.ArrayUtils;
import top.zhogjianhao.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * 从指定索引开始查找数组中指定的字符串的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class CharsIndexOfTest {

  public static void main(String[] args) {
    // 结果是否相等
    CharsIndexOfTest test = new CharsIndexOfTest();
    System.out.println(ObjectUtils.isAllEquals(true, false, test.test1(), test.test2()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{CharsIndexOfTest.class.getName()});
  }

  private static final char[] array = "aaaaaaaaaaaaaaaaaaaaaaaaaaaa{}bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb{}".toCharArray();
  private static final String str = "{}";
  private static final char[] valChars = str.toCharArray();

  @Benchmark
  public int test1() {
    int startIndex = 1;

    int charsLastElementIndex = array.length - 1;
    char lChar = valChars[0];
    char rChar = valChars[1];
    int index = -1;
    while (true) {
      int lCharIndex = ArrayUtils.indexOf(array, lChar, startIndex);
      // 如果找不到左边字符
      if (lCharIndex == -1) {
        break;
      } else if (lCharIndex == charsLastElementIndex) {
        break;
      } else {
        startIndex = lCharIndex + 1;
      }
      int rCharIndex = ArrayUtils.indexOf(array, rChar, lCharIndex + 1);
      // 如果找不到右边字符 则 -1
      if (rCharIndex == -1) {
        break;
      }
      // 如果右边字符下标为左边字符下标 +1，则返回左边字符下标
      if (rCharIndex == lCharIndex + 1) {
        index = lCharIndex;
        break;
      }
    }
    return index;
  }

  @Benchmark
  public int test2() {
    int startIndex = 1;

    for (int i = 0; i < valChars.length; i++) {
      int index = ArrayUtils.indexOf(array, valChars[i], startIndex);
      if (index == -1) {
        return -1;
      }
      if (i == valChars.length - 1) {
        return index - valChars.length + 1;
      }
      startIndex++;
    }
    return -1;
  }

  @Benchmark
  public int test3(){
    return new String(array).indexOf(str, 1);
  }
}

  // Benchmark                               Mode     Cnt   Score   Error   Units
  // CharsIndexOfTest.test1                 thrpt       5  78.649 ± 7.697  ops/us
  // CharsIndexOfTest.test2                 thrpt       5  46.264 ± 1.364  ops/us
  // CharsIndexOfTest.test3                 thrpt       5  48.020 ± 0.948  ops/us
  // CharsIndexOfTest.test1                  avgt       5   0.012 ± 0.001   us/op
  // CharsIndexOfTest.test2                  avgt       5   0.028 ± 0.017   us/op
  // CharsIndexOfTest.test3                  avgt       5   0.021 ± 0.002   us/op
  // CharsIndexOfTest.test1                sample  168614   0.049 ± 0.001   us/op
  // CharsIndexOfTest.test1:test1·p0.00    sample             ≈ 0           us/op
  // CharsIndexOfTest.test1:test1·p0.50    sample             ≈ 0           us/op
  // CharsIndexOfTest.test1:test1·p0.90    sample           0.100           us/op
  // CharsIndexOfTest.test1:test1·p0.95    sample           0.100           us/op
  // CharsIndexOfTest.test1:test1·p0.99    sample           0.100           us/op
  // CharsIndexOfTest.test1:test1·p0.999   sample           0.200           us/op
  // CharsIndexOfTest.test1:test1·p0.9999  sample           4.124           us/op
  // CharsIndexOfTest.test1:test1·p1.00    sample          48.000           us/op
  // CharsIndexOfTest.test2                sample  112702   0.058 ± 0.004   us/op
  // CharsIndexOfTest.test2:test2·p0.00    sample             ≈ 0           us/op
  // CharsIndexOfTest.test2:test2·p0.50    sample           0.100           us/op
  // CharsIndexOfTest.test2:test2·p0.90    sample           0.100           us/op
  // CharsIndexOfTest.test2:test2·p0.95    sample           0.100           us/op
  // CharsIndexOfTest.test2:test2·p0.99    sample           0.100           us/op
  // CharsIndexOfTest.test2:test2·p0.999   sample           0.200           us/op
  // CharsIndexOfTest.test2:test2·p0.9999  sample          16.084           us/op
  // CharsIndexOfTest.test2:test2·p1.00    sample          80.000           us/op
  // CharsIndexOfTest.test3                sample  105333   0.063 ± 0.004   us/op
  // CharsIndexOfTest.test3:test3·p0.00    sample             ≈ 0           us/op
  // CharsIndexOfTest.test3:test3·p0.50    sample           0.100           us/op
  // CharsIndexOfTest.test3:test3·p0.90    sample           0.100           us/op
  // CharsIndexOfTest.test3:test3·p0.95    sample           0.100           us/op
  // CharsIndexOfTest.test3:test3·p0.99    sample           0.100           us/op
  // CharsIndexOfTest.test3:test3·p0.999   sample           3.200           us/op
  // CharsIndexOfTest.test3:test3·p0.9999  sample          12.586           us/op
  // CharsIndexOfTest.test3:test3·p1.00    sample          84.608           us/op
  // CharsIndexOfTest.test1                    ss       5   2.440 ± 1.798   us/op
  // CharsIndexOfTest.test2                    ss       5   2.920 ± 0.633   us/op
  // CharsIndexOfTest.test3                    ss       5   3.200 ± 4.515   us/op
