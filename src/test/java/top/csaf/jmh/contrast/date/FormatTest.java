package top.csaf.jmh.contrast.date;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.date.DateUtil;

import java.util.Date;
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

  private static final Date nowDate= new Date();

  @Benchmark
  public String formatByHutool() {
    return cn.hutool.core.date.DateUtil.formatDateTime(nowDate);
  }

  @Benchmark
  public String formatByZUtil() {
    return DateUtil.format(nowDate);
  }
}

// Benchmark                                           Mode     Cnt     Score    Error   Units
// FormatTest.formatByHutool                          thrpt       5     3.841 ±  0.151  ops/us
// FormatTest.formatByZUtil                           thrpt       5     3.869 ±  0.276  ops/us
// FormatTest.formatByHutool                           avgt       5     0.267 ±  0.011   us/op
// FormatTest.formatByZUtil                            avgt       5     0.267 ±  0.013   us/op
// FormatTest.formatByHutool                         sample  135253     0.336 ±  0.037   us/op
// FormatTest.formatByHutool:formatByHutool·p0.00    sample             0.199            us/op
// FormatTest.formatByHutool:formatByHutool·p0.50    sample             0.300            us/op
// FormatTest.formatByHutool:formatByHutool·p0.90    sample             0.399            us/op
// FormatTest.formatByHutool:formatByHutool·p0.95    sample             0.400            us/op
// FormatTest.formatByHutool:formatByHutool·p0.99    sample             0.600            us/op
// FormatTest.formatByHutool:formatByHutool·p0.999   sample             7.496            us/op
// FormatTest.formatByHutool:formatByHutool·p0.9999  sample            18.025            us/op
// FormatTest.formatByHutool:formatByHutool·p1.00    sample          1515.520            us/op
// FormatTest.formatByZUtil                          sample  140264     0.314 ±  0.007   us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.00      sample             0.199            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.50      sample             0.300            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.90      sample             0.301            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.95      sample             0.400            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.99      sample             0.599            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.999     sample             5.800            us/op
// FormatTest.formatByZUtil:formatByZUtil·p0.9999    sample            32.094            us/op
// FormatTest.formatByZUtil:formatByZUtil·p1.00      sample           163.840            us/op
// FormatTest.formatByHutool                             ss       5    54.380 ± 33.128   us/op
// FormatTest.formatByZUtil                              ss       5    50.200 ± 67.260   us/op
