package top.zhogjianhao.jmh;

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
