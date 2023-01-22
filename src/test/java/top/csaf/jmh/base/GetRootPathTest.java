package top.csaf.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * 获取类的根路径（不含包）的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class GetRootPathTest {

  public static void main(String[] args) {
    // 结果是否相等
    GetRootPathTest test = new GetRootPathTest();
    System.out.println(test.getResourcePath().equals(test.getLoaderResourcePath())
      && test.getLoaderResourcePath().equals(test.getLoaderSystemResourcePath())
      && test.getLoaderSystemResourcePath().equals(test.getThreadLoaderResourcePath()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{GetRootPathTest.class.getName()});
  }

  private static final Class<GetRootPathTest> CLASS = GetRootPathTest.class;

  @Benchmark
  public String getResourcePath() {
    return CLASS.getResource("/").getPath();
  }

  @Benchmark
  public String getLoaderResourcePath() {
    return CLASS.getClassLoader().getResource("").getPath();
  }

  @Benchmark
  public String getLoaderSystemResourcePath() {
    return ClassLoader.getSystemResource("").getPath();
  }

  @Benchmark
  public String getThreadLoaderResourcePath() {
    return Thread.currentThread().getContextClassLoader().getResource("").getPath();
  }
}

// Benchmark                                                                          Mode    Cnt     Score     Error   Units
// GetRootPathTest.getLoaderResourcePath                                             thrpt      5     0.038 ±   0.002  ops/us
// GetRootPathTest.getLoaderSystemResourcePath                                       thrpt      5     0.037 ±   0.002  ops/us
// GetRootPathTest.getResourcePath                                                   thrpt      5     0.038 ±   0.001  ops/us
// GetRootPathTest.getThreadLoaderResourcePath                                       thrpt      5     0.037 ±   0.001  ops/us
// GetRootPathTest.getLoaderResourcePath                                              avgt      5    26.358 ±   0.862   us/op
// GetRootPathTest.getLoaderSystemResourcePath                                        avgt      5    26.606 ±   0.614   us/op
// GetRootPathTest.getResourcePath                                                    avgt      5    26.328 ±   1.160   us/op
// GetRootPathTest.getThreadLoaderResourcePath                                        avgt      5    26.179 ±   0.645   us/op
// GetRootPathTest.getLoaderResourcePath                                            sample  95636    26.531 ±   0.094   us/op
// GetRootPathTest.getLoaderResourcePath:getLoaderResourcePath·p0.00                sample           24.288             us/op
// GetRootPathTest.getLoaderResourcePath:getLoaderResourcePath·p0.50                sample           25.280             us/op
// GetRootPathTest.getLoaderResourcePath:getLoaderResourcePath·p0.90                sample           28.096             us/op
// GetRootPathTest.getLoaderResourcePath:getLoaderResourcePath·p0.95                sample           31.680             us/op
// GetRootPathTest.getLoaderResourcePath:getLoaderResourcePath·p0.99                sample           54.592             us/op
// GetRootPathTest.getLoaderResourcePath:getLoaderResourcePath·p0.999               sample          111.383             us/op
// GetRootPathTest.getLoaderResourcePath:getLoaderResourcePath·p0.9999              sample          236.330             us/op
// GetRootPathTest.getLoaderResourcePath:getLoaderResourcePath·p1.00                sample         1949.696             us/op
// GetRootPathTest.getLoaderSystemResourcePath                                      sample  95657    26.401 ±   0.104   us/op
// GetRootPathTest.getLoaderSystemResourcePath:getLoaderSystemResourcePath·p0.00    sample           24.288             us/op
// GetRootPathTest.getLoaderSystemResourcePath:getLoaderSystemResourcePath·p0.50    sample           25.280             us/op
// GetRootPathTest.getLoaderSystemResourcePath:getLoaderSystemResourcePath·p0.90    sample           27.872             us/op
// GetRootPathTest.getLoaderSystemResourcePath:getLoaderSystemResourcePath·p0.95    sample           31.296             us/op
// GetRootPathTest.getLoaderSystemResourcePath:getLoaderSystemResourcePath·p0.99    sample           50.496             us/op
// GetRootPathTest.getLoaderSystemResourcePath:getLoaderSystemResourcePath·p0.999   sample           80.300             us/op
// GetRootPathTest.getLoaderSystemResourcePath:getLoaderSystemResourcePath·p0.9999  sample          179.954             us/op
// GetRootPathTest.getLoaderSystemResourcePath:getLoaderSystemResourcePath·p1.00    sample         2588.672             us/op
// GetRootPathTest.getResourcePath                                                  sample  93624    27.152 ±   0.132   us/op
// GetRootPathTest.getResourcePath:getResourcePath·p0.00                            sample           24.096             us/op
// GetRootPathTest.getResourcePath:getResourcePath·p0.50                            sample           25.184             us/op
// GetRootPathTest.getResourcePath:getResourcePath·p0.90                            sample           28.576             us/op
// GetRootPathTest.getResourcePath:getResourcePath·p0.95                            sample           33.280             us/op
// GetRootPathTest.getResourcePath:getResourcePath·p0.99                            sample           62.144             us/op
// GetRootPathTest.getResourcePath:getResourcePath·p0.999                           sample          167.776             us/op
// GetRootPathTest.getResourcePath:getResourcePath·p0.9999                          sample          254.829             us/op
// GetRootPathTest.getResourcePath:getResourcePath·p1.00                            sample         2240.512             us/op
// GetRootPathTest.getThreadLoaderResourcePath                                      sample  96057    26.383 ±   0.115   us/op
// GetRootPathTest.getThreadLoaderResourcePath:getThreadLoaderResourcePath·p0.00    sample           23.872             us/op
// GetRootPathTest.getThreadLoaderResourcePath:getThreadLoaderResourcePath·p0.50    sample           25.088             us/op
// GetRootPathTest.getThreadLoaderResourcePath:getThreadLoaderResourcePath·p0.90    sample           28.000             us/op
// GetRootPathTest.getThreadLoaderResourcePath:getThreadLoaderResourcePath·p0.95    sample           32.000             us/op
// GetRootPathTest.getThreadLoaderResourcePath:getThreadLoaderResourcePath·p0.99    sample           52.352             us/op
// GetRootPathTest.getThreadLoaderResourcePath:getThreadLoaderResourcePath·p0.999   sample          116.601             us/op
// GetRootPathTest.getThreadLoaderResourcePath:getThreadLoaderResourcePath·p0.9999  sample          216.253             us/op
// GetRootPathTest.getThreadLoaderResourcePath:getThreadLoaderResourcePath·p1.00    sample         2732.032             us/op
// GetRootPathTest.getLoaderResourcePath                                                ss      5   197.520 ±  95.928   us/op
// GetRootPathTest.getLoaderSystemResourcePath                                          ss      5   236.160 ±  93.531   us/op
// GetRootPathTest.getResourcePath                                                      ss      5   201.940 ±  66.294   us/op
// GetRootPathTest.getThreadLoaderResourcePath                                          ss      5   219.680 ± 213.569   us/op
