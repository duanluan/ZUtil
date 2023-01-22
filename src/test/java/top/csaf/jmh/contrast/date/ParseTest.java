package top.csaf.jmh.contrast.date;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.date.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ParseTest {

  public static void main(String[] args) {
    // 结果是否相等
    ParseTest test = new ParseTest();
    System.out.println(test.parseByHutool().equals(test.parseByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ParseTest.class.getName()});
  }

  private static final String dateStr = "2022-03-06 02:23:30";

  @Benchmark
  public Date parseByHutool() {
    return DateUtil.parse(dateStr).toJdkDate();
  }

  @Benchmark
  public Date parseByZUtil() {
    return DateUtils.parseDate(dateStr);
  }
}

// Benchmark                                        Mode     Cnt     Score     Error   Units
// ParseTest.parseByHutool                         thrpt       5     0.308 ±   0.006  ops/us
// ParseTest.parseByZUtil                          thrpt       5     1.010 ±   0.029  ops/us
// ParseTest.parseByHutool                          avgt       5     3.401 ±   0.054   us/op
// ParseTest.parseByZUtil                           avgt       5     0.986 ±   0.024   us/op
// ParseTest.parseByHutool                        sample   98200     3.404 ±   0.117   us/op
// ParseTest.parseByHutool:parseByHutool·p0.00    sample             2.900             us/op
// ParseTest.parseByHutool:parseByHutool·p0.50    sample             3.100             us/op
// ParseTest.parseByHutool:parseByHutool·p0.90    sample             3.100             us/op
// ParseTest.parseByHutool:parseByHutool·p0.95    sample             3.400             us/op
// ParseTest.parseByHutool:parseByHutool·p0.99    sample             7.000             us/op
// ParseTest.parseByHutool:parseByHutool·p0.999   sample            36.928             us/op
// ParseTest.parseByHutool:parseByHutool·p0.9999  sample           337.752             us/op
// ParseTest.parseByHutool:parseByHutool·p1.00    sample          1478.656             us/op
// ParseTest.parseByZUtil                         sample  160430     1.045 ±   0.056   us/op
// ParseTest.parseByZUtil:parseByZUtil·p0.00      sample             0.900             us/op
// ParseTest.parseByZUtil:parseByZUtil·p0.50      sample             1.000             us/op
// ParseTest.parseByZUtil:parseByZUtil·p0.90      sample             1.000             us/op
// ParseTest.parseByZUtil:parseByZUtil·p0.95      sample             1.100             us/op
// ParseTest.parseByZUtil:parseByZUtil·p0.99      sample             1.500             us/op
// ParseTest.parseByZUtil:parseByZUtil·p0.999     sample             5.696             us/op
// ParseTest.parseByZUtil:parseByZUtil·p0.9999    sample            40.107             us/op
// ParseTest.parseByZUtil:parseByZUtil·p1.00      sample          1515.520             us/op
// ParseTest.parseByHutool                            ss       5   291.700 ± 223.270   us/op
// ParseTest.parseByZUtil                             ss       5   142.740 ±  42.165   us/op
