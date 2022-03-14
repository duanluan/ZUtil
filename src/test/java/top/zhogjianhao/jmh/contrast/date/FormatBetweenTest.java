package top.zhogjianhao.jmh.contrast.date;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.date.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class FormatBetweenTest {

  public static void main(String[] args) {
    // 结果是否相等
    FormatBetweenTest test = new FormatBetweenTest();
    System.out.println(test.fromBetweenByHutool().equals(test.fromBetweenByZUtil()) && test.fromBetweenByZUtil().equals(test.fromBetweenByZUtil1()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{FormatBetweenTest.class.getName()});
  }

  private static final Date date1 = DateUtils.parseDate("2016-03-01 22:33:23");
  private static final Date date2 = DateUtils.parseDate("2016-04-01 23:33:23");

  @Benchmark
  public String fromBetweenByHutool() {
    return DateUtil.formatBetween(date1, date2, BetweenFormatter.Level.SECOND);
  }

  @Benchmark
  public String fromBetweenByZUtil() {
    return DateUtils.formatBetween(date1, date2, "dd天H小时");
  }

  @Benchmark
  public String fromBetweenByZUtil1() {
    return DateUtils.formatBetween(date1, date2, true, null, "天", "小时", "分", "秒", "毫秒");
  }
}
