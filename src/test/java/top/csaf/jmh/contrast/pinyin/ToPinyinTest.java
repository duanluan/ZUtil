package top.csaf.jmh.contrast.pinyin;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.pinyin.PinyinUtil;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ToPinyinTest {

  private static final String STR = "好好学习，，为国为民";

  public static void main(String[] args) {
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
      cn.hutool.extra.pinyin.PinyinUtil.getPinyin(STR, " ");
    }
    long time2 = System.currentTimeMillis();
    System.out.println("hutool耗时：" + (time2 - time1));

    long time3 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
      PinyinUtil.getAll(STR, false, " ");
    }
    long time4 = System.currentTimeMillis();
    System.out.println("zUtil耗时：" + (time4 - time3));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToPinyinTest.class.getName()});
  }

  @Benchmark
  public String toPinyinByHutool() {
    return cn.hutool.extra.pinyin.PinyinUtil.getPinyin(STR, " ");
  }

  @Benchmark
  public String toPinyinByZUtil() {
    // PinyinFeature.setHasSeparatorByNotPinyinAround(true);
    return PinyinUtil.getAll(STR, false, " ");
  }
}

// Benchmark                                                 Mode     Cnt    Score    Error   Units
// ToPinyinTest.toPinyinByHutool                            thrpt       5    2.880 ±  0.160  ops/us
// ToPinyinTest.toPinyinByZUtil                             thrpt       5    4.577 ±  0.133  ops/us
// ToPinyinTest.toPinyinByHutool                             avgt       5    0.356 ±  0.012   us/op
// ToPinyinTest.toPinyinByZUtil                              avgt       5    0.216 ±  0.006   us/op
// ToPinyinTest.toPinyinByHutool                           sample  175058    0.435 ±  0.008   us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.00    sample            0.300            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.50    sample            0.400            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.90    sample            0.500            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.95    sample            0.500            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.99    sample            0.900            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.999   sample            1.600            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.9999  sample           40.900            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p1.00    sample          277.504            us/op
// ToPinyinTest.toPinyinByZUtil                            sample  162384    0.393 ±  0.008   us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.00      sample            0.200            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.50      sample            0.300            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.90      sample            0.500            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.95      sample            0.600            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.99      sample            1.000            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.999     sample            2.500            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.9999    sample           45.425            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p1.00      sample          170.496            us/op
// ToPinyinTest.toPinyinByHutool                               ss       5   30.880 ± 37.754   us/op
// ToPinyinTest.toPinyinByZUtil                                ss       5   23.060 ± 16.885   us/op
