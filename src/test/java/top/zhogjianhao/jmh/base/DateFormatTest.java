package top.zhogjianhao.jmh.base;

import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Date 格式化的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class DateFormatTest {

  public static void main(String[] args) {
    DateFormatTest test = new DateFormatTest();
    System.out.println(test.test1().equals(test.test2()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{DateFormatTest.class.getName()});
  }

  private static final Date NOW_DATE = new Date();
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  @Benchmark
  public String test1() {
    return FastDateFormat.getInstance(DATE_FORMAT).format(NOW_DATE);
  }

  @Benchmark
  public String test2() {
    return DateTimeFormatter.ofPattern(DATE_FORMAT).format(NOW_DATE.toInstant().atZone(ZoneId.systemDefault()));
  }
}

// Benchmark                             Mode     Cnt    Score    Error   Units
// DateFormatTest.test1                 thrpt       5    3.287 ±  0.091  ops/us
// DateFormatTest.test2                 thrpt       5    1.793 ±  0.114  ops/us
// DateFormatTest.test1                  avgt       5    0.300 ±  0.006   us/op
// DateFormatTest.test2                  avgt       5    0.543 ±  0.022   us/op
// DateFormatTest.test1                sample  125834    0.355 ±  0.012   us/op
// DateFormatTest.test1:test1·p0.00    sample            0.300            us/op
// DateFormatTest.test1:test1·p0.50    sample            0.300            us/op
// DateFormatTest.test1:test1·p0.90    sample            0.400            us/op
// DateFormatTest.test1:test1·p0.95    sample            0.400            us/op
// DateFormatTest.test1:test1·p0.99    sample            0.600            us/op
// DateFormatTest.test1:test1·p0.999   sample            3.800            us/op
// DateFormatTest.test1:test1·p0.9999  sample           51.205            us/op
// DateFormatTest.test1:test1·p1.00    sample          288.256            us/op
// DateFormatTest.test2                sample  140451    0.675 ±  0.022   us/op
// DateFormatTest.test2:test2·p0.00    sample            0.500            us/op
// DateFormatTest.test2:test2·p0.50    sample            0.600            us/op
// DateFormatTest.test2:test2·p0.90    sample            0.700            us/op
// DateFormatTest.test2:test2·p0.95    sample            0.800            us/op
// DateFormatTest.test2:test2·p0.99    sample            1.100            us/op
// DateFormatTest.test2:test2·p0.999   sample            3.800            us/op
// DateFormatTest.test2:test2·p0.9999  sample           47.625            us/op
// DateFormatTest.test2:test2·p1.00    sample          817.152            us/op
// DateFormatTest.test1                    ss       5   68.600 ± 56.211   us/op
// DateFormatTest.test2                    ss       5   65.240 ± 21.364   us/op
