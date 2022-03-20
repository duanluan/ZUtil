package top.zhogjianhao.jmh.contrast.str;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.StringUtils;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class FormatTest {

  public static void main(String[] args) {
    // 结果是否相等
    FormatTest test = new FormatTest();
    System.out.println(test.formatByHutool().equals(test.formatByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{FormatTest.class.getName()});
  }

  private static final String format = "{}爱{}";
  private static final String arg1 = "我真的";
  private static final String arg2 = "你啊";


  @Benchmark
  public String formatByHutool() {
    return StrUtil.format(format, arg1, arg2);
  }

  @Benchmark
  public String formatByZUtil() {
    return StringUtils.format(format, arg1, arg2);
  }
}
