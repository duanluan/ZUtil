package top.zhogjianhao.jmh;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.charset.StandardCharsets;

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
