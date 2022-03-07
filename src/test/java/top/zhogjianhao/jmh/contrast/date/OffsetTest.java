package top.zhogjianhao.jmh.contrast.date;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.date.DateUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class OffsetTest {

  public static void main(String[] args) {
    // 结果是否相等
    OffsetTest test = new OffsetTest();
    System.out.println(test.offsetByHutool().equals(test.offsetByZUtil()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{OffsetTest.class.getName()});
  }

  private static final LocalDateTime nowDateTime = LocalDateTime.now();
  private static final Date nowDate= DateUtils.toDate(nowDateTime);

  @Benchmark
  public Date offsetByHutool() {
    return DateUtil.offset(nowDate, DateField.DAY_OF_MONTH,2).toJdkDate();
  }

  @Benchmark
  public Date offsetByZUtil() {
    return DateUtils.toDate(DateUtils.plusOrMinus(nowDateTime, 2, ChronoUnit.DAYS));
  }
}

// Benchmark                                           Mode     Cnt     Score    Error   Units
// OffsetTest.offsetByHutool                          thrpt       5     2.232 ±  0.141  ops/us
// OffsetTest.offsetByZUtil                           thrpt       5    13.255 ±  0.251  ops/us
// OffsetTest.offsetByHutool                           avgt       5     0.406 ±  0.017   us/op
// OffsetTest.offsetByZUtil                            avgt       5     0.073 ±  0.002   us/op
// OffsetTest.offsetByHutool                         sample  185721     0.474 ±  0.030   us/op
// OffsetTest.offsetByHutool:offsetByHutool·p0.00    sample             0.399            us/op
// OffsetTest.offsetByHutool:offsetByHutool·p0.50    sample             0.400            us/op
// OffsetTest.offsetByHutool:offsetByHutool·p0.90    sample             0.501            us/op
// OffsetTest.offsetByHutool:offsetByHutool·p0.95    sample             0.501            us/op
// OffsetTest.offsetByHutool:offsetByHutool·p0.99    sample             0.900            us/op
// OffsetTest.offsetByHutool:offsetByHutool·p0.999   sample             5.800            us/op
// OffsetTest.offsetByHutool:offsetByHutool·p0.9999  sample            62.326            us/op
// OffsetTest.offsetByHutool:offsetByHutool·p1.00    sample          1589.248            us/op
// OffsetTest.offsetByZUtil                          sample  129200     0.109 ±  0.028   us/op
// OffsetTest.offsetByZUtil:offsetByZUtil·p0.00      sample               ≈ 0            us/op
// OffsetTest.offsetByZUtil:offsetByZUtil·p0.50      sample             0.100            us/op
// OffsetTest.offsetByZUtil:offsetByZUtil·p0.90      sample             0.101            us/op
// OffsetTest.offsetByZUtil:offsetByZUtil·p0.95      sample             0.101            us/op
// OffsetTest.offsetByZUtil:offsetByZUtil·p0.99      sample             0.200            us/op
// OffsetTest.offsetByZUtil:offsetByZUtil·p0.999     sample             0.700            us/op
// OffsetTest.offsetByZUtil:offsetByZUtil·p0.9999    sample             7.032            us/op
// OffsetTest.offsetByZUtil:offsetByZUtil·p1.00      sample          1110.016            us/op
// OffsetTest.offsetByHutool                             ss       5    79.340 ± 30.052   us/op
// OffsetTest.offsetByZUtil                              ss       5    24.120 ± 14.540   us/op
