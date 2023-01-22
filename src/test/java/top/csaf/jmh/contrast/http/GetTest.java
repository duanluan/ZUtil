package top.csaf.jmh.contrast.http;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.http.HttpUtils;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.AverageTime)
public class GetTest {

  public static void main(String[] args) {
    // 结果是否相等
    GetTest test = new GetTest();
    System.out.println(test.getByHutool().equals(test.getByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{GetTest.class.getName()});
  }

  private static final String url = "http://localhost:3000/posts";

  @Benchmark
  public String getByHutool() {
    return HttpUtil.get(url);
  }

  @Benchmark
  public String getByZUtil() {
    return HttpUtils.get(url).toString();
  }
}

// Benchmark            Mode  Cnt      Score      Error  Units
// GetTest.getByHutool  avgt    5  29144.555 ± 2456.575  us/op
// GetTest.getByZUtil   avgt    5  29078.258 ± 3279.540  us/op
