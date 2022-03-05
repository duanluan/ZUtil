package top.zhogjianhao.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.charset.StandardCharsets;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * 字节数组转字符数组性能测试
 * <p>
 * 注意：ByteBuffer 会补 0
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ToCharsTest {

  public static void main(String[] args) {
    // 结果是否相等
    ToCharsTest test = new ToCharsTest();
    System.out.println(test.string()[0] == test.charBufferWrap()[0] && test.charBufferWrap()[0] == test.charBufferAllocate()[0]);
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToCharsTest.class.getName()});
  }

  byte[] bytes = {-28, -72, -83, -27, -101, -67};

  @Benchmark
  public char[] string() {
    return new String(bytes, StandardCharsets.UTF_8).toCharArray();
  }

  @Benchmark
  public char[] charBufferWrap() {
    return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes)).array();
  }

  @Benchmark
  public char[] charBufferAllocate() {
    ByteBuffer bb = ByteBuffer.allocate(bytes.length).put(bytes);
    bb.flip();
    return StandardCharsets.UTF_8.decode(bb).array();
  }
}

// Benchmark                                                    Mode     Cnt     Score    Error   Units
// ToCharsTest.charBufferAllocate                              thrpt       5    17.768 ±  1.110  ops/us
// ToCharsTest.charBufferWrap                                  thrpt       5    21.095 ±  0.273  ops/us
// ToCharsTest.string                                          thrpt       5    26.156 ±  4.919  ops/us
// ToCharsTest.charBufferAllocate                               avgt       5     0.055 ±  0.003   us/op
// ToCharsTest.charBufferWrap                                   avgt       5     0.042 ±  0.001   us/op
// ToCharsTest.string                                           avgt       5     0.038 ±  0.005   us/op
// ToCharsTest.charBufferAllocate                             sample  143812     0.094 ±  0.027   us/op
// ToCharsTest.charBufferAllocate:charBufferAllocate·p0.00    sample               ≈ 0            us/op
// ToCharsTest.charBufferAllocate:charBufferAllocate·p0.50    sample             0.100            us/op
// ToCharsTest.charBufferAllocate:charBufferAllocate·p0.90    sample             0.100            us/op
// ToCharsTest.charBufferAllocate:charBufferAllocate·p0.95    sample             0.100            us/op
// ToCharsTest.charBufferAllocate:charBufferAllocate·p0.99    sample             0.200            us/op
// ToCharsTest.charBufferAllocate:charBufferAllocate·p0.999   sample             0.800            us/op
// ToCharsTest.charBufferAllocate:charBufferAllocate·p0.9999  sample            11.566            us/op
// ToCharsTest.charBufferAllocate:charBufferAllocate·p1.00    sample          1167.360            us/op
// ToCharsTest.charBufferWrap                                 sample  107152     0.068 ±  0.004   us/op
// ToCharsTest.charBufferWrap:charBufferWrap·p0.00            sample               ≈ 0            us/op
// ToCharsTest.charBufferWrap:charBufferWrap·p0.50            sample             0.100            us/op
// ToCharsTest.charBufferWrap:charBufferWrap·p0.90            sample             0.100            us/op
// ToCharsTest.charBufferWrap:charBufferWrap·p0.95            sample             0.100            us/op
// ToCharsTest.charBufferWrap:charBufferWrap·p0.99            sample             0.100            us/op
// ToCharsTest.charBufferWrap:charBufferWrap·p0.999           sample             0.800            us/op
// ToCharsTest.charBufferWrap:charBufferWrap·p0.9999          sample            10.679            us/op
// ToCharsTest.charBufferWrap:charBufferWrap·p1.00            sample           109.056            us/op
// ToCharsTest.string                                         sample  123424     0.072 ±  0.003   us/op
// ToCharsTest.string:string·p0.00                            sample               ≈ 0            us/op
// ToCharsTest.string:string·p0.50                            sample             0.100            us/op
// ToCharsTest.string:string·p0.90                            sample             0.100            us/op
// ToCharsTest.string:string·p0.95                            sample             0.100            us/op
// ToCharsTest.string:string·p0.99                            sample             0.100            us/op
// ToCharsTest.string:string·p0.999                           sample             0.600            us/op
// ToCharsTest.string:string·p0.9999                          sample            11.189            us/op
// ToCharsTest.string:string·p1.00                            sample            49.472            us/op
// ToCharsTest.charBufferAllocate                                 ss       5    19.740 ± 28.292   us/op
// ToCharsTest.charBufferWrap                                     ss       5    14.240 ± 13.385   us/op
// ToCharsTest.string                                             ss       5    13.160 ±  8.630   us/op
