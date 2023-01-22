package top.csaf.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.charset.StandardCharsets;

import java.nio.CharBuffer;
import java.util.concurrent.TimeUnit;

/**
 * 字节数组转字符数组性能测试
 * <p>
 * 注意：CharBuffer 会补 0
 * <p>
 * JMH 快速上手：https://juejin.cn/post/6844904169476718605<br>
 * JMH + JUnit5：https://stackoverflow.com/questions/30485856/how-to-run-jmh-from-inside-junit-tests
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ToBytesTest {

  public static void main(String[] args) {
    // 结果是否相等
    ToBytesTest test = new ToBytesTest();
    System.out.println(test.string()[0] == test.charBufferWrap()[0] && test.charBufferWrap()[0] == test.charBufferAllocate()[0]);
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToBytesTest.class.getName()});
  }

  char[] chars = new char[]{'中', '国'};

  @Benchmark
  public byte[] string() {
    return new String(chars).getBytes(StandardCharsets.UTF_8);
  }

  @Benchmark
  public byte[] charBufferWrap() {
    return StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars)).array();
  }

  @Benchmark
  public byte[] charBufferAllocate() {
    CharBuffer cb = CharBuffer.allocate(chars.length).put(chars);
    cb.flip();
    return StandardCharsets.UTF_8.encode(cb).array();
  }
}

// Benchmark                                                    Mode     Cnt    Score    Error   Units
// ToBytesTest.charBufferAllocate                              thrpt       5   10.256 ±  0.266  ops/us
// ToBytesTest.charBufferWrap                                  thrpt       5   11.447 ±  1.610  ops/us
// ToBytesTest.string                                          thrpt       5   26.405 ±  0.710  ops/us
// ToBytesTest.charBufferAllocate                               avgt       5    0.095 ±  0.003   us/op
// ToBytesTest.charBufferWrap                                   avgt       5    0.086 ±  0.002   us/op
// ToBytesTest.string                                           avgt       5    0.038 ±  0.001   us/op
// ToBytesTest.charBufferAllocate                             sample  120409    0.129 ±  0.026   us/op
// ToBytesTest.charBufferAllocate:charBufferAllocate·p0.00    sample            0.100            us/op
// ToBytesTest.charBufferAllocate:charBufferAllocate·p0.50    sample            0.100            us/op
// ToBytesTest.charBufferAllocate:charBufferAllocate·p0.90    sample            0.200            us/op
// ToBytesTest.charBufferAllocate:charBufferAllocate·p0.95    sample            0.200            us/op
// ToBytesTest.charBufferAllocate:charBufferAllocate·p0.99    sample            0.200            us/op
// ToBytesTest.charBufferAllocate:charBufferAllocate·p0.999   sample            1.459            us/op
// ToBytesTest.charBufferAllocate:charBufferAllocate·p0.9999  sample           10.135            us/op
// ToBytesTest.charBufferAllocate:charBufferAllocate·p1.00    sample          945.152            us/op
// ToBytesTest.charBufferWrap                                 sample  108019    0.118 ±  0.006   us/op
// ToBytesTest.charBufferWrap:charBufferWrap·p0.00            sample              ≈ 0            us/op
// ToBytesTest.charBufferWrap:charBufferWrap·p0.50            sample            0.100            us/op
// ToBytesTest.charBufferWrap:charBufferWrap·p0.90            sample            0.100            us/op
// ToBytesTest.charBufferWrap:charBufferWrap·p0.95            sample            0.200            us/op
// ToBytesTest.charBufferWrap:charBufferWrap·p0.99            sample            0.200            us/op
// ToBytesTest.charBufferWrap:charBufferWrap·p0.999           sample            3.100            us/op
// ToBytesTest.charBufferWrap:charBufferWrap·p0.9999          sample           30.099            us/op
// ToBytesTest.charBufferWrap:charBufferWrap·p1.00            sample           97.664            us/op
// ToBytesTest.string                                         sample  116972    0.076 ±  0.009   us/op
// ToBytesTest.string:string·p0.00                            sample              ≈ 0            us/op
// ToBytesTest.string:string·p0.50                            sample            0.100            us/op
// ToBytesTest.string:string·p0.90                            sample            0.100            us/op
// ToBytesTest.string:string·p0.95                            sample            0.100            us/op
// ToBytesTest.string:string·p0.99                            sample            0.100            us/op
// ToBytesTest.string:string·p0.999                           sample            0.800            us/op
// ToBytesTest.string:string·p0.9999                          sample           18.180            us/op
// ToBytesTest.string:string·p1.00                            sample          200.448            us/op
// ToBytesTest.charBufferAllocate                                 ss       5   45.340 ± 56.662   us/op
// ToBytesTest.charBufferWrap                                     ss       5   31.060 ± 29.005   us/op
// ToBytesTest.string                                             ss       5   20.980 ± 32.452   us/op
